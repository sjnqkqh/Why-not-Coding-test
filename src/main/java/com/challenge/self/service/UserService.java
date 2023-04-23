package com.challenge.self.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.self.config.CommonException;
import com.challenge.self.dto.user.req.LoginReqDto;
import com.challenge.self.dto.user.req.PasswordResetReqDto;
import com.challenge.self.dto.user.req.UserCreateReqDto;
import com.challenge.self.dto.user.resp.UserInfoRespDto;
import com.challenge.self.entity.User;
import com.challenge.self.repository.UserRepository;
import com.challenge.self.util.code.ApiExceptionCode;
import com.challenge.self.util.BcryptUtil;
import com.challenge.self.util.EncryptUtil;
import com.challenge.self.util.JwtTokenProvideUtil;
import com.challenge.self.util.RegexUtil;
import com.challenge.self.util.StringUtil;
import com.challenge.self.util.code.YnCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final EntityManager entityManager;

	/**
	 * 회원 생성 DTO 유효성 검사
	 *
	 * @param reqDto 회원 생성 DTO
	 */
	@Transactional(readOnly = true)
	public void validateCreateUser(UserCreateReqDto reqDto) {

		// 로그인 ID 유효성 검사
		if (!RegexUtil.checkLoginIdPattern(reqDto.getLoginId())) {
			throw new CommonException("Login ID validation fail.", ApiExceptionCode.REQUEST_VALIDATION_EXCEPTION);
		}

		if (isDuplicateLoginId(reqDto.getLoginId())) {
			throw new CommonException("Login ID duplicate.", ApiExceptionCode.REQUEST_VALIDATION_EXCEPTION);
		}

		// 비밀번호 유효성 검사
		if (!RegexUtil.checkPasswordPattern(reqDto.getPassword())) {
			throw new CommonException("Password validation fail.", ApiExceptionCode.REQUEST_VALIDATION_EXCEPTION);
		}

		// 휴대전화 번호 패턴 유효성 검사
		if (!RegexUtil.checkPhoneNumberPattern(reqDto.getPhone())) {
			throw new CommonException("Phone Number validation fail.", ApiExceptionCode.REQUEST_VALIDATION_EXCEPTION);
		}
	}

	/**
	 * 로그인 ID 중복 검사
	 */
	@Transactional(readOnly = true)
	public boolean isDuplicateLoginId(String loginId) {
		return userRepository.existsByLoginIdAndDeleteYn(loginId, YnCode.Y);
	}

	/**
	 * 회원 ID로 회원 정보 조회
	 *
	 * @param id 유저 ID
	 * @return 회원 객체
	 */
	@Transactional(readOnly = true)
	public User searchUser(Long id) {
		return userRepository.findById(id)
			.orElseThrow(
				() -> new CommonException("User ID does not exist", ApiExceptionCode.REQUEST_VALIDATION_EXCEPTION));
	}

	/**
	 * 회원 ID로 회원 정보 조회 (UserInfoDto 반환)
	 *
	 * @param id 유저 ID
	 * @return 회원 객체
	 */
	@Transactional(readOnly = true)
	public UserInfoRespDto searchUserInfo(Long id) throws Exception {
		return new UserInfoRespDto(searchUser(id));
	}

	/**
	 * 로그인 ID와 이메일로 회원정보 조회 (비밀번호 조회 시, 사용)
	 *
	 * @param loginId 로그인 ID
	 * @param email 이메일
	 * @return 회원정보
	 */
	@Transactional(readOnly = true)
	public User searchUserByLoginIdAndEmail(String loginId, String email) {
		return userRepository.findFirstByLoginIdAndEmailAndDeleteYnOrderByCreatedAtDesc(loginId, email, YnCode.N)
			.orElseThrow(
				() -> new CommonException(ApiExceptionCode.NOT_EXIST_USER_INFORMATION_ERROR)
			);
	}

	/**
	 * AccessToken으로 회원 정보 조회
	 *
	 * @param accessToken 헤더에 포함된 액세스 토큰 값
	 * @return 회원 객체
	 */
	@Transactional(readOnly = true)
	public User searchUser(String accessToken) {
		return userRepository.findFirstByAccessTokenAndDeleteYnOrderByCreatedAtDesc(accessToken, YnCode.N)
			.orElseThrow(() -> new CommonException(ApiExceptionCode.TOKEN_NOT_EXIST_ERROR));
	}

	/**
	 * 회원 생성
	 *
	 * @param reqDto 회원 생성 DTO
	 */
	@Transactional
	public void createUser(UserCreateReqDto reqDto) throws Exception {
		String encPhone = EncryptUtil.encryptAES256(reqDto.getPhone());
		String hashedPassword = BcryptUtil.bcryptEncode(reqDto.getPassword());
		userRepository.save(new User(reqDto, encPhone, hashedPassword));
	}


	/**
	 * 로그인
	 *
	 * @param reqDto ID, PW
	 * @return 로그인 성공 여부
	 */
	@Transactional(readOnly = true)
	public Long login(LoginReqDto reqDto) {
		Optional<User> userOptional = userRepository.findFirstByLoginIdAndDeleteYnOrderByCreatedAtDesc(
			reqDto.getLoginId(), YnCode.N);
		if (userOptional.isEmpty()) {
			log.info("[UserService.login] Login Fail. ID:" + reqDto.getLoginId());
			throw new CommonException(ApiExceptionCode.LOGIN_FAIL_ERROR);
		}

		User user = userOptional.get();
		if (!BcryptUtil.match(reqDto.getPassword(), user.getEncryptedPassword())) {
			log.info("[UserService.login] Login Fail. Password is Wrong");
			throw new CommonException(ApiExceptionCode.LOGIN_FAIL_ERROR);
		}

		return user.getUserId();
	}

	/**
	 * 로그인 이후 Access Token 발급
	 *
	 * @param userId 회원 ID
	 * @return Access Token
	 */
	@Transactional
	public String giveLoginAuthToken(Long userId) {
		Long expiredAt = System.currentTimeMillis() + (1000 * 60 * 60 * 3); // 토큰 만료 시각
		LocalDateTime expiredLocal = LocalDateTime.now().plusHours(3); // 토큰 만료 시각
		String accessToken = JwtTokenProvideUtil.doGenerateToken(StringUtil.getUUID(), expiredAt); // 해당 회원 Access Token
		searchUser(userId).setAccessToken(accessToken, expiredLocal); // 회원 정보에 토큰 정보 삽입

		return accessToken;
	}

	/**
	 * 비밀번호 변경
	 *
	 * @param reqDto 휴대전화 인증 및 아이디, 이메일, 신규 비밀번호 정보
	 */
	@Transactional
	public void resetPassword(PasswordResetReqDto reqDto) {
		User user = searchUserByLoginIdAndEmail(reqDto.getLoginId(), reqDto.getEmail());
		user.updatePassword(BcryptUtil.bcryptEncode(reqDto.getPassword()));
	}


	/**
	 * 벌크 회원 생성 (Test Setting)
	 *
	 * @param reqDtoList 회원 생성 DTO List
	 */
	@Transactional
	public void createUserBulk(ArrayList<UserCreateReqDto> reqDtoList) throws Exception {
		ArrayList<User> userList = new ArrayList<>();
		for (UserCreateReqDto dto : reqDtoList) {
			String encPhone = EncryptUtil.encryptAES256(dto.getPhone());
			String hashedPassword = BcryptUtil.bcryptEncode(dto.getPassword());
			userList.add(new User(dto, encPhone, hashedPassword));
		}

		for (int i = 0; i < userList.size(); i++) {
			entityManager.persist(userList.get(i));
			if (i % 100 == 0) { // 매 100건마다 flush & clear
				entityManager.flush();
				entityManager.clear();
			}
		}

		entityManager.flush(); // 마지막에 flush & clear
		entityManager.clear();

	}

	@Transactional(readOnly = true)
	public ArrayList<User> getRandomUser(int pageSize) {
		return userRepository.findUsersByDeleteYn(YnCode.N, Pageable.ofSize(pageSize));
	}
}

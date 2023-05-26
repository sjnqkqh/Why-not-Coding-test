package com.why_not_cote.controller;

import com.why_not_cote.dto.CommonRespDto;
import com.why_not_cote.dto.user.req.LoginReqDto;
import com.why_not_cote.dto.user.req.PasswordResetReqDto;
import com.why_not_cote.dto.user.req.UserCreateReqDto;
import com.why_not_cote.dto.user.resp.LoginIdAvailableRespDto;
import com.why_not_cote.dto.user.resp.LoginRespDto;
import com.why_not_cote.dto.user.resp.UserInfoRespDto;
import com.why_not_cote.service.AuthService;
import com.why_not_cote.service.UserService;
import com.why_not_cote.util.code.AuthTypeCode;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    /**
     * 회원가입
     *
     * @param reqDto 회원가입 데이터
     * @return 회원가입 완료 여부
     */
    @PostMapping("/api/user")
    public CommonRespDto createUser(@RequestBody @Valid UserCreateReqDto reqDto) throws Exception {
        // 회원가입 입력 데이터 유효성 검사
        userService.validateCreateUser(reqDto);

        // 휴대폰 인증 여부 확인
        Long authId = authService.validatePhoneAuth(reqDto.getPhone(), reqDto.getTelecomCode(), AuthTypeCode.SIGN_IN, reqDto.getAuthentication());

        // 회원 생성
        userService.createUser(reqDto);

        // 휴대폰 인증 데이터 제거
        authService.deletePhoneAuthHistory(authId);

        return new CommonRespDto(true);
    }

    /**
     * 로그인 ID 중복 검사
     *
     * @param loginId 로그인 ID
     * @return 로그인 ID 사용 가능 여부
     */
    @GetMapping("/api/user/check-duplicate")
    public LoginIdAvailableRespDto checkLoginIdDuplicate(@RequestParam(name = "loginId") @Valid String loginId) {
        return new LoginIdAvailableRespDto(userService.isDuplicateLoginId(loginId));
    }

    /**
     * 로그인
     *
     * @param reqDto 로그인 정보
     * @return 로그인 결과과
     */
    @PostMapping("/api/user/login")
    public LoginRespDto login(@RequestBody @Valid LoginReqDto reqDto) {
        Long userId = userService.login(reqDto);

        return new LoginRespDto(userService.giveLoginAuthToken(userId));
    }

    /**
     * 회원 정보 조회
     *
     * @return 회원 정보
     */
    @GetMapping("/api/user/info")
    public UserInfoRespDto searchUserInformation(@RequestAttribute(name = "id") Long userId) throws Exception {
        return userService.searchUserInfo(userId);
    }

    /**
     * 비밀번호 변경
     *
     * @return 비밀번호 변경 성공 여부
     */
    @PatchMapping("/api/user/password")
    public CommonRespDto resetPassword(@RequestBody PasswordResetReqDto reqDto) throws Exception {
        Long authId = authService.validatePhoneAuth(reqDto.getPhone(), reqDto.getTelecomCode(), AuthTypeCode.PASSWORD_RESET, reqDto.getAuthentication());

        userService.resetPassword(reqDto);

        authService.deletePhoneAuthHistory(authId);

        return new CommonRespDto(true);
    }
}

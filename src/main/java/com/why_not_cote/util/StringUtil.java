package com.why_not_cote.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	private static final SecureRandom RANDOM = new SecureRandom();

	/**
	 * 인증번호 생성 함수 (입력한 휴대전화 번호의 뒷자리 6자리)
	 *
	 * @param str 휴대전화 번호
	 * @return 인증번호 6자리
	 */
	public static String getAuthValue(String str) {
		return str.substring(str.length() - 6);
	}

	/**
	 * 랜덤 UUID 생성 함수
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 랜덤 글자 조합 함수
	 */
	public static String generateRandomNameString(String subFix) {
		List<String> prefixList = new ArrayList<>(List.of("사랑", "행복", "꿈나무", "쑥쑥"));
		return StringUtils.join(prefixList.get(RANDOM.nextInt(prefixList.size())), subFix);
	}

}

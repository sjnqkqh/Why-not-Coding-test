package com.why_not_cote.config;

import com.why_not_cote.entity.user.User;
import com.why_not_cote.service.UserService;
import com.why_not_cote.util.code.ApiExceptionCode;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        String accessToken = StringUtils.replaceOnce(request.getHeader(HttpHeaders.AUTHORIZATION), "bearer ", "");
        User user = userService.searchUser(accessToken);

        if (StringUtils.isBlank(accessToken)) {
            throw new CommonException(ApiExceptionCode.HEADER_FIELD_EXCEPTION);
        }
        log.info("[AuthenticationInterceptor.preHandle] Start URI={} accessToken={}", request.getRequestURI(), accessToken);

        if (LocalDateTime.now().isAfter(user.getAccessExpiredAt())) { // Access Token 만료 여부 확인
            log.error("[AuthenticationInterceptor.preHandle] Expire access token. expiredAt={}", user.getAccessExpiredAt());
            throw new CommonException(ApiExceptionCode.TOKEN_EXPIRED_EXCEPTION);
        }

        request.setAttribute("id", user.getUserId()); // 회원 ID 정보 Attribute로 전달

        return true;
    }

}

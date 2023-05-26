package com.why_not_cote.dto.user.resp;

import lombok.Getter;

@Getter
public class LoginRespDto {

    private final String accessToken;


    public LoginRespDto( String accessToken) {
        this.accessToken = accessToken;
    }
}

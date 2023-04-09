package com.challenge.self.dto.auth.req;

import com.challenge.self.util.AuthTypeCode;
import com.challenge.self.util.TelecomCode;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInPhoneAuthReqDto {

    private final AuthTypeCode authTypeCode = AuthTypeCode.SIGN_IN;

    @NotNull
    private TelecomCode telecomCode;

    @NotBlank
    private String phone;


    public SignInPhoneAuthReqDto(TelecomCode telecomCode, String phone) {
        this.telecomCode = telecomCode;
        this.phone = phone;
    }
}

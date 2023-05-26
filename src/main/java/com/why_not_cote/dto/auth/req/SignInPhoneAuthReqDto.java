package com.why_not_cote.dto.auth.req;

import com.why_not_cote.util.code.AuthTypeCode;
import com.why_not_cote.util.code.TelecomCode;
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

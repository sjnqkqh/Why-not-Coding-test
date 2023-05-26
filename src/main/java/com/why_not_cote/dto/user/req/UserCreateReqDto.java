package com.why_not_cote.dto.user.req;

import com.why_not_cote.util.code.TelecomCode;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateReqDto {

    @NotBlank
    private String loginId;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Size(min = 1, max = 20)
    private String nickname;

    @NotBlank
    private String phone;

    @NotNull
    private TelecomCode telecomCode;

    @NotBlank
    private String authentication;

}

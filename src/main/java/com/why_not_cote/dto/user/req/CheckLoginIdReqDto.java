package com.why_not_cote.dto.user.req;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckLoginIdReqDto {

    @NotBlank
    String loginId;
}

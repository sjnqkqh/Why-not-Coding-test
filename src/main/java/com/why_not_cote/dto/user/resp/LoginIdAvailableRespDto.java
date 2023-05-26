package com.why_not_cote.dto.user.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class LoginIdAvailableRespDto {

    private Boolean isDuplicateId;
}

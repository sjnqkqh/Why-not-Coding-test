package com.challenge.self.dto.enviroment.req;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BulkReqDto {

	int bulkCount;
}

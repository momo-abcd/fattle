package com.sixman.fattle.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

	private long userCode;
	private String tokenType;
	private String accessToken;
	private String refreshToken;

}

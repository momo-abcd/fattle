package com.sixman.fattle.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {

	private long userCode;
	private String tokenType;
	private String accessToken;
	private String refreshToken;

}

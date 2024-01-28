package com.sixman.fattle.dto.response;

import com.sixman.fattle.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

	private long userCode;
	private String tokenType;
	private String accessToken;
	private String refreshToken;

}

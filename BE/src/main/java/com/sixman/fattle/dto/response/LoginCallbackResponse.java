package com.sixman.fattle.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCallbackResponse {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String id_token;
    private long expires_in;
    private String scope;
    private long refresh_token_expires_in;

    private String id;
}

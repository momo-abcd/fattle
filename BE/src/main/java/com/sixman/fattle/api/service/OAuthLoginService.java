package com.sixman.fattle.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

@Service
public class UserService {

    @Value("${rest-api-key}")
    private String REST_API_KEY;

    @Value("${redirect-uri}")
    private String REDIRECT_URI;

    @Value("${authorize-uri}")
    private String AUTHORIZE_URI;

    @Value("${token-uri}")
    public String TOKEN_URI;

    @Value("${client-secret}")
    private String CLIENT_SECRET;

    @Value("${kakao-api-host}")
    private String KAKAO_API_HOST;

    public RedirectView goKakaoOAuth() {
        return goKakaoOAuth("");
    }

    public RedirectView goKakaoOAuth(String scope) {
        String uri = AUTHORIZE_URI + "?redirect_uri=" + REDIRECT_URI
                + "&response_type=code&client_id=" + REST_API_KEY;
        if (!scope.isEmpty()) uri += "&scope=" + scope;

        return new RedirectView(uri);
    }

    public RedirectView loginCallback(String code) {
        String param = "grant_type=authorization_code&client_id=" + REST_API_KEY
                + "?redirect_uri=" + REDIRECT_URI
                + "&client_secret=" + CLIENT_SECRET + "&code=" + code;

    }
}

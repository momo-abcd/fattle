package com.sixman.fattle.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sixman.fattle.dto.response.LoginCallbackResponse;
import com.sixman.fattle.repository.UserRepository;
import com.sixman.fattle.utils.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final HttpCallService httpCallService;

    private final UserRepository userRepository;

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

    public LoginCallbackResponse loginCallback(String code) {
        RestTemplate template = new RestTemplate();

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = getKakaoTokenRequest(code);

        ResponseEntity<String> accessTokenResponse = template.exchange(TOKEN_URI, HttpMethod.POST, kakaoTokenRequest, String.class);

        ObjectMapper mapper = new ObjectMapper();

        LoginCallbackResponse response = null;

        try {
            response = mapper.readValue(accessTokenResponse.getBody(), LoginCallbackResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }

    private HttpEntity<MultiValueMap<String, String>> getKakaoTokenRequest(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", REST_API_KEY);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);
        params.add("client_secret", CLIENT_SECRET);

        return new HttpEntity<>(params, headers);
    }

    public String getProfile(String token) {
        String uri = KAKAO_API_HOST + "/v2/user/me";
        return httpCallService.callWithToken(Const.GET, uri, token);
    }
}

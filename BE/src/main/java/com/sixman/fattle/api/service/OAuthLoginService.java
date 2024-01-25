package com.sixman.fattle.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sixman.fattle.dto.ProfileDto;
import com.sixman.fattle.dto.response.LoginCallbackResponse;
import com.sixman.fattle.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

@Service
public class OAuthLoginService {

    @Autowired
    public HttpCallService httpCallService;

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

        System.out.println("uri" + uri);

        if (!scope.isEmpty()) uri += "&scope=" + scope;

        return new RedirectView(uri);
    }

    public ResponseEntity<LoginCallbackResponse> loginCallback(String code) {
        String param = "grant_type=authorization_code&client_id=" + REST_API_KEY
                + "?redirect_uri=" + REDIRECT_URI
                + "&client_secret=" + CLIENT_SECRET + "&code=" + code;
        String rtn = httpCallService.call(Const.POST, TOKEN_URI, Const.EMPTY, param);

        ObjectMapper mapper = new ObjectMapper();

        LoginCallbackResponse response = null;

        try {
            response = mapper.readValue(rtn, LoginCallbackResponse.class);
            String profile = getProfile(response.getAccess_token());
            ProfileDto profileDto = mapper.readValue(profile, ProfileDto.class);
            response.setId(profileDto.getId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(response);
    }

    public String getProfile(String token) {
        String uri = KAKAO_API_HOST + "/v2/user/me";
        return httpCallService.callWithToken(Const.GET, uri, token);
    }
}

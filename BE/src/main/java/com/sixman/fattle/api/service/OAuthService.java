package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.KakaoProfile;
import com.sixman.fattle.dto.response.LoginResponse;
import com.sixman.fattle.dto.response.OAuthTokenResponse;
import com.sixman.fattle.entity.User;
import com.sixman.fattle.utils.JwtTokenProvider;
import com.sixman.fattle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private static final String BEARER_TYPE = "Bearer";

    private final InMemoryClientRegistrationRepository inMemoryRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    public LoginResponse login(String providerName, String code) {
        ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);

        OAuthTokenResponse response = getToken(code, provider);

        User user = getUserProfile(providerName, response, provider);

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserCode()));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        return LoginResponse.builder()
                .userCode(user.getUserCode())
                .tokenType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private OAuthTokenResponse getToken(String code, ClientRegistration provider) {
        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OAuthTokenResponse.class)
                .block();
    }

    private MultiValueMap<String, String> tokenRequest(String code, ClientRegistration provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUri());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("client_id", provider.getClientId());
        return formData;
    }

    private User getUserProfile(String providerName, OAuthTokenResponse response, ClientRegistration provider) {
        Map<String, Object> userAttributes = getUserAttributes(provider, response);
        KakaoProfile profile = null;

        if (providerName.equals("kakao")) {
            profile = new KakaoProfile(userAttributes);
        }

        long providerId = profile.getProviderId();

        User user = userRepository.findByUserCode(providerId);

        if (user == null) {
            user = new User();
            user.setUserCode(providerId);
            userRepository.save(user);
        }

        return user;
    }

    private Map<String, Object> getUserAttributes(ClientRegistration provider, OAuthTokenResponse response) {
        return WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header -> header.setBearerAuth(response.getAccess_token()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

}

package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.dto.KakaoProfile;
import com.sixman.fattle.dto.response.LoginResponse;
import com.sixman.fattle.dto.dto.OAuthToken;
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
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private static final String BEARER_TYPE = "Bearer";

    private final InMemoryClientRegistrationRepository inMemoryRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    public RedirectView getCode(String providerName) {
        ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);

        String uri = provider.getProviderDetails().getAuthorizationUri()
                + "?response_type=code&redirect_uri="
                + provider.getRedirectUri()
                + "&client_id="
                + provider.getClientId();
        return new RedirectView(uri);
    }

    public LoginResponse login(String providerName, String code) {
        ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);

        OAuthToken token = getToken(code, provider);

        User user = getUserProfile(token, provider);

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserCode()));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        return LoginResponse.builder()
                .userCode(user.getUserCode())
                .tokenType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private OAuthToken getToken(String code, ClientRegistration provider) {
        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OAuthToken.class)
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

    private User getUserProfile(OAuthToken token, ClientRegistration provider) {
        Map<String, Object> userAttributes = getUserAttributes(provider, token);
        KakaoProfile profile = new KakaoProfile(userAttributes);

        long providerId = profile.getProviderId();

        return signIn(providerId);
    }

    private User signIn(long userCode) {
        User user = userRepository.findByUserCode(userCode);

        if (user == null) {
            user = new User();
            user.setUserCode(userCode);
            userRepository.save(user);
        }

        return user;
    }

    private Map<String, Object> getUserAttributes(ClientRegistration provider, OAuthToken token) {
        return WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header -> header.setBearerAuth(token.getAccess_token()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

}

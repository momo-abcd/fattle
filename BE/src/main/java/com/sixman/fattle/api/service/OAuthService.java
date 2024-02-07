package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.dto.KakaoProfile;
import com.sixman.fattle.dto.response.TokenResponse;
import com.sixman.fattle.dto.dto.OAuthToken;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private static final String BEARER_TYPE = "Bearer";

    private final InMemoryClientRegistrationRepository inMemoryRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public URI getCode(String providerName) throws URISyntaxException {
        ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);

        String uri = provider.getProviderDetails().getAuthorizationUri()
                + "?response_type=code&redirect_uri="
                + provider.getRedirectUri()
                + "&client_id="
                + provider.getClientId();
        return new URI(uri);
    }

    public TokenResponse login(String providerName, String code) {
        ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);

        OAuthToken token = getToken(code, provider);

        long userCode = getUserProfile(token, provider);

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(userCode));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        return TokenResponse.builder()
                .userCode(userCode)
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

    private long getUserProfile(OAuthToken token, ClientRegistration provider) {
        Map<String, Object> userAttributes = getUserAttributes(provider, token);
        KakaoProfile profile = new KakaoProfile(userAttributes);

       return profile.getProviderId();
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

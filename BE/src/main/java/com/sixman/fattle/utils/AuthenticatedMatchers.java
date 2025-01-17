package com.sixman.fattle.utils;

public class AuthenticatedMatchers {

    private AuthenticatedMatchers() {}

    public static final String[] matcherArray = {
            "/",
            "/oauth/code/**",
            "/oauth/login/**",
            "/api-docs",
            "/api-docs/json",
            "/v3/**",
            "/v3/api-docs/**",
            "/swagger-ui/index.html",
            "/swagger-ui/**",
            "/api-docs/**",
            "/swagger-ui.html"
    };

}

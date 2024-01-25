package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.OAuthLoginService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/accounts/auth")
@Slf4j
@Api(tags = "소셜로그인 API")
public class AuthController {

    @Autowired
    private OAuthLoginService oAuthLoginService;

    @GetMapping("/login")
    public RedirectView goKakaoOAuth() {
        return oAuthLoginService.goKakaoOAuth();
    }

    @GetMapping("/login-callback")
    public ResponseEntity<?> loginCallback(@RequestParam("code") String code) {
        return oAuthLoginService.loginCallback(code);
    }

}

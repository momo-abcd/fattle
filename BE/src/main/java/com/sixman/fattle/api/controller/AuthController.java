package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private OAuthLoginService OAuthLoginService;

    public AuthController(OAuthLoginService OAuthLoginService) {
        this.OAuthLoginService = OAuthLoginService;
    }

    @GetMapping("/login/kakao")
    public RedirectView goKakaoOAuth() {
        return OAuthLoginService.goKakaoOAuth();
    }

    @GetMapping("/login-callback")
    public RedirectView loginCallback(@RequestParam("code") String code) {
        return OAuthLoginService.loginCallback(code);
    }

}

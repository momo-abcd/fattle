package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.OAuthLoginService;
import com.sixman.fattle.dto.response.LoginCallbackResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "소셜로그인 API")
public class AuthController {

    private final OAuthLoginService oAuthLoginService;

    @GetMapping("/login")
    public RedirectView goKakaoOAuth() {
        return oAuthLoginService.goKakaoOAuth();
    }

    @GetMapping("/login-callback")
    public ResponseEntity<?> loginCallback(@RequestParam("code") String code) {
        LoginCallbackResponse response = oAuthLoginService.loginCallback(code);

        return ResponseEntity.ok(response);
    }

}

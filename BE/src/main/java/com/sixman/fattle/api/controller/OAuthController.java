package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.OAuthService;
import com.sixman.fattle.dto.response.LoginCallbackResponse;
import com.sixman.fattle.entity.User;
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
@CrossOrigin("*")
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/login")
    public RedirectView goKakaoOAuth() {
        return oAuthService.goKakaoOAuth();
    }

    @GetMapping("/login-callback")
    public ResponseEntity<?> loginCallback(@RequestParam("code") String code) {
        LoginCallbackResponse response = oAuthService.loginCallback(code);

        User user = oAuthService.getUser(response.getAccess_token());

        return ResponseEntity.ok(user);
    }

}

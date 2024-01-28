package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.OAuthService;
import com.sixman.fattle.dto.response.LoginResponse;
import com.sixman.fattle.dto.response.OAuthTokenResponse;
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

    @GetMapping("/login/{provider}")
    public ResponseEntity<?> login(@PathVariable String provider,
                                           @RequestParam("code") String code) {
        LoginResponse response = oAuthService.login(provider, code);

        return ResponseEntity.ok(response);
    }

}

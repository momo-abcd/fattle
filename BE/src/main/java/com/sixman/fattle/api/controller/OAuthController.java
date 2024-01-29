package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.OAuthService;
import com.sixman.fattle.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "OAuth 컨트롤러", description = "로그인 기능을 위한 API")
public class OAuthController {

    private final OAuthService oAuthService;

    @Operation(summary = "로그인", description = "카카오 로그인 code를 받아 FATTLE 로그인 실행")
    @GetMapping("/login/{provider}")
    public ResponseEntity<?> login(@PathVariable String provider,
                                           @RequestParam("code") String code) {
        LoginResponse response = oAuthService.login(provider, code);

        return ResponseEntity.ok(response);
    }

}

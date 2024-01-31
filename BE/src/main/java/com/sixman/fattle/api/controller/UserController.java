package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User 컨트롤러", description = "유저 관리 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인",
            description = "유저 코드를 통해 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "204", description = "회원가입 필요")
    })
    @GetMapping("/login/{userCode}")
    public ResponseEntity<?> login(@PathVariable long userCode) {
        return ResponseEntity.status(userService.login(userCode)).build();
    }

}

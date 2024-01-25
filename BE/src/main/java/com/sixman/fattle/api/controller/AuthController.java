package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Slf4j
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login/kakao")
    public RedirectView goKakaoOAuth() {
        return userService.goKakaoOAuth();
    }

    @GetMapping("/login-callback")
    public RedirectView loginCallback(@RequestParam("code") String code) {
        return userService.loginCallback(code);
    }

}

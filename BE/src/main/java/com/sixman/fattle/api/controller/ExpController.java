package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.ExpService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exp")
@RequiredArgsConstructor
@Tag(name = "Exp 컨트롤러", description = "경험치 관리를 위한 API")
public class ExpController {

    private final ExpService expService;

//    @GetMapping("/history/{userCode}")
//    public ResponseEntity<?> expHistory(@PathVariable long userCode) {
//
//    }

}

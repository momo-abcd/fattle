package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.BattlePointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/battle/point")
@RequiredArgsConstructor
@Tag(name = "Battle Point 컨트롤러", description = "배틀 중 포인트를 부여하는 API")
public class BattlePointController {

    private final BattlePointService battlePointService;

}

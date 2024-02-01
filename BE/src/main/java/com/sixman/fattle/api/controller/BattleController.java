package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.BattleService;
import com.sixman.fattle.dto.request.BattleCreateRequest;
import com.sixman.fattle.dto.request.RegistPlayerRequest;
import com.sixman.fattle.dto.request.BattleSettingRequest;
import com.sixman.fattle.dto.request.RegistTriggerRequest;
import com.sixman.fattle.dto.response.BattleCreateResponse;
import com.sixman.fattle.dto.response.BattleListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/battle")
@RequiredArgsConstructor
@Tag(name = "Battle 컨트롤러", description = "배틀 서비스를 위한 API")
public class BattleController {

    private final BattleService battleService;

    @Operation(summary = "배틀 코드 생성",
            description = "배틀 코드를 생성하여 사용자에게 제공")
    @PostMapping("/create")
    public ResponseEntity<?> createBattle(@RequestBody BattleCreateRequest request) {
        BattleCreateResponse response = battleService.createBattle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "배틀 리스트",
            description = "사용자가 참여한 배틀 리스트")
    @GetMapping("/list/{userCode}")
    public ResponseEntity<?> getBattleList(@PathVariable long userCode) {
        BattleListResponse response = battleService.getBattleList(userCode);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/regist/player")
    public ResponseEntity<?> registPlayer(@RequestBody RegistPlayerRequest request) {
        battleService.registPlayer(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/regist/trigger")
    public ResponseEntity<?> registTrigger(@RequestBody RegistTriggerRequest request) {
        battleService.registTrigger(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/setting")
    public ResponseEntity<?> battleSetting(@RequestBody BattleSettingRequest request) {
        battleService.battleSetting(request);
        return ResponseEntity.ok().build();
    }

}

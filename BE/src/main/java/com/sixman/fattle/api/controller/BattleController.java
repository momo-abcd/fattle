package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.BattleService;
import com.sixman.fattle.dto.request.*;
import com.sixman.fattle.dto.response.BattleCreateResponse;
import com.sixman.fattle.dto.response.BattleInfoResponse;
import com.sixman.fattle.dto.response.BattleListResponse;
import com.sixman.fattle.dto.response.RemainPointResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponse(responseCode = "201", description = "배틀 생성")
    @PostMapping("/create")
    public ResponseEntity<BattleCreateResponse> createBattle(@RequestBody BattleCreateRequest request) {
        BattleCreateResponse response = battleService.createBattle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "배틀 리스트",
            description = "사용자가 참여한 배틀 리스트")
    @ApiResponse(responseCode = "200", description = "배틀 리스트 응답")
    @GetMapping("/list/{userCode}")
    public ResponseEntity<BattleListResponse> getBattleList(@PathVariable long userCode) {
        BattleListResponse response = battleService.getBattleList(userCode);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내기자 등록",
            description = "배틀 내기자 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내기자 등록 성공"),
            @ApiResponse(responseCode = "400", description = "내기자 등록 실패")
    })
    @PostMapping("/regist/player")
    public ResponseEntity<HttpStatus> registPlayer(@RequestBody RegistPlayerRequest request) {
        return ResponseEntity.status(battleService.registPlayer(request)).build();
    }

    @Operation(summary = "자극자 등록",
            description = "배틀 자극자 등록")
    @ApiResponse(responseCode = "200", description = "자극자 등록 성공")
    @PostMapping("/regist/trigger")
    public ResponseEntity<?> registTrigger(@RequestBody RegistTriggerRequest request) {
        battleService.registTrigger(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "배틀 정보 수정",
            description = "배틀 정보 수정")
    @ApiResponse(responseCode = "200", description = "배틀 정보 수정 성공")
    @PatchMapping("/setting")
    public ResponseEntity<?> battleSetting(@RequestBody BattleSettingRequest request) {
        battleService.battleSetting(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "배틀 시작",
            description = "배틀 시작")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "배틀 시작 성공"),
            @ApiResponse(responseCode = "400", description = "배틀 시작 실패")
    })
    @GetMapping("/start/{battleCode}")
    public ResponseEntity<?> battleStart(@PathVariable String battleCode) {
        return ResponseEntity.status(battleService.battleStart(battleCode)).build();
    }

    @Operation(summary = "배틀 종료",
            description = "배틀 종료")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "배틀 종료 성공"),
            @ApiResponse(responseCode = "400", description = "배틀 종료 실패")
    })
    @GetMapping("/finish/{battleCode}")
    public ResponseEntity<?> battleFinish(@PathVariable String battleCode) {
        return ResponseEntity.status(battleService.battleFinish(battleCode)).build();
    }

    @Operation(summary = "최종 몸무게 설정",
            description = "배틀 종료 전 최종 몸무게 설정")
    @ApiResponse(responseCode = "200", description = "몸무게 설정 성공")
    @PatchMapping("/weight")
    public ResponseEntity<?> setPlayerWeight(@RequestBody PlayerWeightRequest request) {
        battleService.setPlayerWeight(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "배틀 정보",
            description = "배틀 정보 상세 보기")
    @ApiResponse(responseCode = "200", description = "배틀 정보 상세 보기 성공")
    @GetMapping("/info/{battleCode}")
    public ResponseEntity<BattleInfoResponse> getBattleInfo(@PathVariable String battleCode) {
        BattleInfoResponse response = battleService.getBattleInfo(battleCode);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "자극자 남은 라이브 점수",
            description = "자극자 남은 라이브 점수 조회")
    @ApiResponse(responseCode = "200", description = "자극자 남은 라이브 점수 조회 성공")
    @GetMapping("/point/{battleCode}/{userCode}")
    public ResponseEntity<RemainPointResponse> getRemainPoint(@PathVariable String battleCode,
                                                              @PathVariable long userCode) {
        RemainPointResponse response = battleService.getRemainPoint(battleCode, userCode);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "자극자 점수 부여",
            description = "배틀 중 자극자 점수 부여")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "점수 부여 성공"),
            @ApiResponse(responseCode = "400", description = "점수 부여 실패")
    })
    @PatchMapping("/point")
    public ResponseEntity<?> setBattlePoint(@RequestBody BattlePointRequest request) {
        HttpStatus status = battleService.setBattlePoint(request);
        return ResponseEntity.status(status).build();
    }

    @DeleteMapping("/{battleCode}")
    public ResponseEntity<?> deleteBattle(@PathVariable String battleCode) {
        HttpStatus status = battleService.deleteBattle(battleCode);
        return ResponseEntity.status(status).build();
    }

    @DeleteMapping("/player/{battleCode}/{userCode}")
    public ResponseEntity<?> deletePlayer(@PathVariable String battleCode,
                                          @PathVariable long userCode) {
        HttpStatus status = battleService.deletePlayer(battleCode, userCode);
        return ResponseEntity.status(status).build();
    }

    @DeleteMapping("/trigger/{battleCode}/{userCode}")
    public ResponseEntity<?> deleteTrigger(@PathVariable String battleCode,
                                          @PathVariable long userCode) {
        HttpStatus status = battleService.deleteTrigger(battleCode, userCode);
        return ResponseEntity.status(status).build();
    }

}

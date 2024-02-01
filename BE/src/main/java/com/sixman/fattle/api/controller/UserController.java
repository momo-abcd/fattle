package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.UserService;
import com.sixman.fattle.dto.DailyQuestDto;
import com.sixman.fattle.dto.request.SignUpRequest;
import com.sixman.fattle.dto.response.UserInfoResponse;
import com.sixman.fattle.entity.DailyQuest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @Operation(summary = "회원가입",
            description = "유저 정보를 받아 회원가입")
    @ApiResponse(responseCode = "201", description = "회원가입 성공")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "닉네임 중복 확인",
            description = "기존에 존재하는 닉네임인지 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 사용 가능"),
            @ApiResponse(responseCode = "204", description = "닉네임 중복")
    })
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname) {
        return ResponseEntity.status(userService.checkNickname(nickname)).build();
    }

    @Operation(summary = "유저 정보",
            description = "유저 코드를 받아 유저 정보를 응답")
    @ApiResponse(responseCode = "200", description = "유저 정보 응답")
    @GetMapping("/userinfo/{userCode}")
    public ResponseEntity<UserInfoResponse> userInfo(@PathVariable long userCode) {
        UserInfoResponse response = userService.userInfo(userCode);
        DailyQuest lastDailyQuest = userService.getDailyQuest(userCode);
        response.setDayCheck(lastDailyQuest.isDayCheck());
        response.setExerciseCount(lastDailyQuest.getExerciseCount());
        response.setFoodCount(lastDailyQuest.getFoodCount());
        response.setFinish(lastDailyQuest.isFinish());
        return ResponseEntity.ok(response);
    }


    private DailyQuestDto convertToDto(DailyQuest dailyQuest) {
        return DailyQuestDto.builder()
                // DailyQuest의 필드들을 DailyQuestDto에 맞게 매핑
                .recordDate(dailyQuest.getRecordDate())
                .dayCheck(dailyQuest.isDayCheck())
                .exerciseCount(dailyQuest.getExerciseCount())
                .foodCount(dailyQuest.getFoodCount())
                .Finish(dailyQuest.isFinish())
                // 나머지 필드들도 필요에 따라 추가
                .build();
    }
}

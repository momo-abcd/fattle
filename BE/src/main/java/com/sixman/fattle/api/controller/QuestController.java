package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.DailyQuestService;
import com.sixman.fattle.dto.dto.DailyQuestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quest")
@RequiredArgsConstructor
@Tag(name = "Quest 컨트롤러", description = "퀘스트 관리 API")
public class QuestController {

    @Autowired
    private final DailyQuestService dailyQuestService;

    @Operation(summary = "퀘스트 정보",
            description = "유저 코드를 받아 메인 페이지에 퀘스트 정보를 응답")
    @ApiResponse(responseCode = "200", description = "유저 정보 응답")
    @GetMapping("/list/{userCode}")
    public ResponseEntity<DailyQuestDto> getDailyQuests(@PathVariable long userCode) {
        return dailyQuestService.getDailyQuests(userCode);
    }
}


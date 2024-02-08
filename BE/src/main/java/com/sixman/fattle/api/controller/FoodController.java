package com.sixman.fattle.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sixman.fattle.api.service.FoodService;
import com.sixman.fattle.dto.response.FoodInfoResponse;
import com.sixman.fattle.dto.response.TodaysFoodResponse;
import com.sixman.fattle.exceptions.FileSaveFailedException;
import com.sixman.fattle.exceptions.NoFileException;
import com.sixman.fattle.exceptions.NoImageExceptoin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
@Tag(name = "Food 컨트롤러", description = "식단 업로드, 관리를 위한 API")
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/todays/{userCode}")
    public ResponseEntity<TodaysFoodResponse> todaysFood(@PathVariable long userCode) {
        TodaysFoodResponse response = foodService.todaysFood(userCode);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "음식 사진 업로드 & 음식 정보 얻기",
            description = "음식 사진을 업로드해 음식 정보 얻기")
    @ApiResponse(responseCode = "200", description = "음식 정보 응답")
    @PostMapping("/img-upload/{userCode}/{type}")
    public ResponseEntity<FoodInfoResponse> imgUpload(@PathVariable long userCode, @PathVariable int type, MultipartFile uploadFile)
            throws FileSaveFailedException, NoImageExceptoin, NoFileException {
        String folderPath = foodService.saveImage(userCode, type, uploadFile);
        FoodInfoResponse info = foodService.getFoodInfo(folderPath);
        return ResponseEntity.ok(info);
    }

}

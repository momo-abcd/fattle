package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.FoodService;
import com.sixman.fattle.dto.response.TodaysFoodResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @PostMapping("/img-upload/{userCode}/{foodCode}")
    public ResponseEntity<?> imgUpload(@PathVariable long userCode, @PathVariable int foodCode, MultipartFile uploadFile){
        String folderPath = foodService.saveImage(userCode, foodCode, uploadFile);
        JSONObject info = foodService.getFoodInfo(folderPath);
        return ResponseEntity.ok(info);
    }

}

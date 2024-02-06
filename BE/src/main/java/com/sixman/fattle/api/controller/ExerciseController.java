package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.ExerciseService;
import com.sixman.fattle.dto.request.ExerciseCreateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor
@Tag(name = "Exercise 컨트롤러", description = "운동 기록, 관리를 위한 API")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping("/record")
    public ResponseEntity<?> exerciseRecord(@RequestBody ExerciseCreateRequest exerciseInfo) {
        return exerciseService.exerciseRecord(exerciseInfo);
    }


}

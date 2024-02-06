package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.request.ExerciseCreateRequest;
import org.springframework.http.ResponseEntity;

public interface ExerciseService {

    ResponseEntity<ExerciseCreateRequest> exerciseRecord(ExerciseCreateRequest exerciseInfo);

}

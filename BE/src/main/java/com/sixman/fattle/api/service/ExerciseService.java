package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.request.QuestRequest;
import com.sixman.fattle.entity.Exercise;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExerciseService {

    ResponseEntity<?> exerciseRecord(QuestRequest request);

    List<Exercise> getTodayExercise(long userCode);

}

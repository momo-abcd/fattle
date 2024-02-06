package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.request.ExerciseCreateRequest;
import com.sixman.fattle.entity.Exercise;
import com.sixman.fattle.repository.ExerciseRepository;
import com.sixman.fattle.repository.ExerciseTypeRepository;
import com.sixman.fattle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private final ExerciseRepository exerciseRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ExerciseTypeRepository exerciseTypeRepository;

    @Override
    public ResponseEntity<ExerciseCreateRequest> exerciseRecord(ExerciseCreateRequest exerciseInfo) {
        Exercise exercise = Exercise.builder()
                .user(userRepository.getUser(exerciseInfo.getUserCode()))
                .exerciseType(exerciseTypeRepository.findByTypeCode(exerciseInfo.getTypeCode()))
                .recDate(LocalDateTime.now())
                .build();
        exerciseRepository.save(exercise);

        return ResponseEntity.ok(exerciseInfo);
    }

}

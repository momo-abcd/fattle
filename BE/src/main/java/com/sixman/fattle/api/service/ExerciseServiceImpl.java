package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.request.QuestRequest;
import com.sixman.fattle.entity.Exercise;
import com.sixman.fattle.repository.ExerciseRepository;
import com.sixman.fattle.repository.ExerciseTypeRepository;
import com.sixman.fattle.repository.UserRepository;
import com.sixman.fattle.utils.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private final UserRepository userRepository;

    private final ExerciseTypeRepository exerciseTypeRepository;

    private final ExpService expService;

    @Override
    public ResponseEntity<?> exerciseRecord(QuestRequest request) {
        Exercise exercise = Exercise.builder()
                .user(userRepository.getUser(request.getUserCode()))
                .exerciseType(exerciseTypeRepository.findByTypeCode(request.getExercise()))
                .build();
        exerciseRepository.save(exercise);

        String exTypeName = exerciseTypeRepository.getName(request.getExercise());

        expService.setExp(request.getUserCode(), Const.EXP_TYPE_EXERCISE, exTypeName, Const.DAILY_QUEST_EXP);

        return ResponseEntity.ok().build();
    }

    @Override
    public List<Exercise> getTodayExercise(long userCode) {
        return exerciseRepository.;
    }

}

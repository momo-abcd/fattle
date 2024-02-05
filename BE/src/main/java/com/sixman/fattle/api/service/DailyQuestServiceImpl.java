package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.dto.DailyQuestDto;
import com.sixman.fattle.dto.dto.ExerciseDto;
import com.sixman.fattle.dto.response.DailyQuestResponse;
import com.sixman.fattle.entity.DailyQuest;
import com.sixman.fattle.entity.Exercise;
import com.sixman.fattle.entity.ExerciseType;
import com.sixman.fattle.entity.User;
import com.sixman.fattle.repository.DailyQuestRepository;
import com.sixman.fattle.repository.ExerciseRepository;
import com.sixman.fattle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sixman.fattle.utils.Const.*;

@Service
@RequiredArgsConstructor
public class DailyQuestServiceImpl implements DailyQuestService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final DailyQuestRepository dailyQuestRepository;

    private final ExerciseRepository exerciseRepository;

    public ResponseEntity<DailyQuestResponse> getDailyQuests(Long userCode) {
        ExerciseDto exerciseDto = new ExerciseDto(0,0,0,0,0,0);
        List<Exercise> exercises = exerciseRepository.findExerciseByUser_UserCdAndRecDate(userCode, LocalDate.now());
        if (!exercises.isEmpty()) {
            for (Exercise exercise : exercises) {
                String exerciseName = exercise.getExerciseType().getName();
                switch (exerciseName) {
                    case EXP_CONTENT_RUNNING:
                        exerciseDto.setRunning(1);
                        break;
                    case EXP_CONTENT_BURPEE:
                        exerciseDto.setBurpee(1);
                        break;
                    case EXP_CONTENT_PLANK:
                        exerciseDto.setPlank(1);
                        break;
                    case EXP_CONTENT_PULLUP:
                        exerciseDto.setPullup(1);
                        break;
                    case EXP_CONTENT_PUSHUP:
                        exerciseDto.setPushup(1);
                        break;
                    case EXERCISE_CODE_SQUAT:
                        exerciseDto.setSquat(1);
                        break;
                }
            }
        }
        DailyQuest lastDailyQuest = getDailyQuest(userCode);
        DailyQuestResponse dailyQuest = DailyQuestResponse.builder()
                .foodCnt(lastDailyQuest.getFoodCount())
                .exercise(exerciseDto)
                .exerciseCnt(lastDailyQuest.getExerciseCount())
                .build();
        return ResponseEntity.ok(dailyQuest);
    }
    public DailyQuest getDailyQuest(long userCode) {
        User user = userRepository.getUser(userCode);
        List<DailyQuest> dailyQuests = dailyQuestRepository.findByUser(user);
        if (!dailyQuests.isEmpty()) {
            return dailyQuests.get(dailyQuests.size() - 1);
        } else {
            return null;
        }
    }
}

package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.dto.ExerciseDto;
import com.sixman.fattle.dto.request.QuestRequest;
import com.sixman.fattle.dto.response.DailyQuestResponse;
import com.sixman.fattle.entity.Quest;
import com.sixman.fattle.entity.Exercise;
import com.sixman.fattle.repository.QuestRepository;
import com.sixman.fattle.repository.ExerciseRepository;
import com.sixman.fattle.repository.UserRepository;
import com.sixman.fattle.utils.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.sixman.fattle.utils.Const.*;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestServiceImpl implements QuestService {

    private final ExerciseService exerciseService;

    private final ExpService expService;

    private final QuestRepository questRepository;

    private final ExerciseRepository exerciseRepository;

    @Override
    public ResponseEntity<DailyQuestResponse> getDailyQuests(long userCode) {
        List<Exercise> exercises = exerciseService.getTodayExercise(userCode);

        ExerciseDto exerciseDto = new ExerciseDto(0,0,0,0,0,0);
        List<Exercise> exercises = exerciseRepository
                .findExerciseByUser_UserCdAndRecDateBetween(
                        userCode,
                        LocalDate.now().atStartOfDay(),
                        LocalDate.now().atTime(23, 59, 59));

        if (!exercises.isEmpty()) {
            for (Exercise exercise : exercises) {
                String exerciseName = exercise.getExerciseType().getName();

                switch (exerciseName) {
                    case EXP_CONTENT_RUNNING:
                        exerciseDto.setRUN(1);
                        continue;
                    case EXP_CONTENT_BURPEE:
                        exerciseDto.setBUR(1);
                        continue;
                    case EXP_CONTENT_PLANK:
                        exerciseDto.setPLA(1);
                        continue;
                    case EXP_CONTENT_PULLUP:
                        exerciseDto.setPUL(1);
                        continue;
                    case EXP_CONTENT_PUSHUP:
                        exerciseDto.setPUS(1);
                        continue;
                    case EXP_CONTENT_SQUAT:
                        exerciseDto.setSQU(1);
                }
            }
        }

        Quest lastQuest = getDailyQuest(userCode);

        DailyQuestResponse dailyQuest = DailyQuestResponse.builder()
                .foodCnt(lastQuest.getFoodCnt())
                .exercise(exerciseDto)
                .exerciseCnt(lastQuest.getExerciseCnt())
                .build();

        return ResponseEntity.ok(dailyQuest);
    }

    @Override
    public ResponseEntity<?> questRecord(QuestRequest request) {
        return exerciseService.exerciseRecord(request);
    }

    @Override
    public void questRecord(long userCode) {

    }

    @Override
    public Quest getDailyQuest(long userCode) {
        return questRepository.getDailyQuest(userCode);
    }

    @Override
    public void createQuest(long userCode) {
        questRepository.createQuest(userCode);
        expService.setExp(userCode, EXP_TYPE_DAILY, EXP_TYPE_DAILY, DAILY_QUEST_EXP);
    }

}

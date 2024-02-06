package com.sixman.fattle.dto.response;

import com.sixman.fattle.dto.dto.ExerciseDto;
import com.sixman.fattle.entity.ExerciseType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DailyQuestResponse {

    private int foodCnt;
    private ExerciseDto exercise;
//    private List<ExerciseType> exerciseType;
    private int exerciseCnt;

}

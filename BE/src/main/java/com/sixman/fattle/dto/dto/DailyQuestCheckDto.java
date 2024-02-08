package com.sixman.fattle.dto.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyQuestCheckDto {

    private int dayChk;

    private int exerciseCnt;

    private int foodCnt;

    private int isFinish;

    public int chkSum() {
        return dayChk + exerciseCnt + foodCnt;
    }

}

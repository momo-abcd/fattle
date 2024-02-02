package com.sixman.fattle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class DailyQuestDto {
    private Timestamp recordDate;
    private Long userCd;
    private boolean dayCheck;
    private int exerciseCount;
    private int foodCount;
    private boolean Finish;

}

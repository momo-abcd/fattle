package com.sixman.fattle.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class DailyQuestDto {
    private Timestamp recordDate;
    private boolean dayCheck;
    private int exerciseCount;
    private int foodCount;
    private boolean Finish;

}

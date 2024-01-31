package com.sixman.fattle.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class DailyQuestDto {
    private Date recordDate;
    private boolean dayCheck;
    private int exerciseCount;
    private int foodCount;
    private boolean finish;

}

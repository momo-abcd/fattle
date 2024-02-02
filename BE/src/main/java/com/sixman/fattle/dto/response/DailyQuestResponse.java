package com.sixman.fattle.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class DailyQuestResponse {
    private Timestamp recordDate;
    private Long userCd;
    private boolean dayCheck;
    private int exerciseCount;
    private int foodCount;
    private boolean Finish;

}

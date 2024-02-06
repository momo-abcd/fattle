package com.sixman.fattle.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class DailyQuestDto {

    private LocalDateTime recordDate;
    private long userCd;
    private boolean dayCheck;
    private int exerciseCount;
    private int foodCount;
    private boolean Finish;

}

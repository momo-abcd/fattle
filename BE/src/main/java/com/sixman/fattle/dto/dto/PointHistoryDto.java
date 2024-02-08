package com.sixman.fattle.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointHistoryDto {

    private String playerName;

    private String triggerName;

    private int type;

    private int point;

    private LocalDateTime recDate;

}

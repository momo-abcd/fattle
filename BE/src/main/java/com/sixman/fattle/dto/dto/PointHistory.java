package com.sixman.fattle.dto.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
public class PointHistory {

    @QueryProjection
    public PointHistory(String playerName, String triggerName, int type, int point, LocalDateTime recDate) {
        this.playerName = playerName;
        this.triggerName = triggerName;
        this.type = type;
        this.point = point;
        this.recDate = recDate;
    }

    private String playerName;

    private String triggerName;

    private int type;

    private int point;

    private LocalDateTime recDate;

}

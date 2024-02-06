package com.sixman.fattle.dto.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BattlePlayerInfo {

    private long userCode;

    private String nickname;

    private float beforeWeight;

    private float afterWeight;

    private float goalWeight;

    private int livePoint;

    private int foodPoint;

    private int liveUserPoint;

    private int foodUserPoint;

    private int questPoint;

    private int goalPoint;

    private String imgPath;

    public int sumPoints() {
        return livePoint + foodPoint + liveUserPoint + foodUserPoint + questPoint + goalPoint;
    }

}

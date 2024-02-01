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

    @QueryProjection
    public BattlePlayerInfo(long userCode, String nickname) {
        this.userCode = userCode;
        this.nickname = nickname;
    }

    private long userCode;

    private String nickname;

    private float beforeWeight;

    private float afterWeight;

    private float goalWeight;

    private String imgPath;

}

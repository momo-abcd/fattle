package com.sixman.fattle.dto.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BattleTriggerInfo {

    @QueryProjection
    public BattleTriggerInfo(long userCode, String nickname, String imgPath) {
        this.userCode = userCode;
        this.nickname = nickname;
        this.imgPath = imgPath;
    }

    private long userCode;

    private String nickname;

    private String imgPath;

}

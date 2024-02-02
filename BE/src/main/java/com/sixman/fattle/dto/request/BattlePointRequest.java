package com.sixman.fattle.dto.request;

import lombok.Getter;

@Getter
public class BattlePointRequest {

    private String battleCode;

    private long playerUserCode;

    private long triggerUserCode;

    private int type;

    private int point;

}

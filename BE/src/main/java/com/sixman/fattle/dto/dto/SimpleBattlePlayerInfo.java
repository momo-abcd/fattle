package com.sixman.fattle.dto.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleBattlePlayerInfo {

    private long userCode;

    private String nickname;

}

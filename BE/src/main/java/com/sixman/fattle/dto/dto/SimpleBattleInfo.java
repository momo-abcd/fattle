package com.sixman.fattle.dto.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class SimpleBattleInfo {

    private String battleCode;

    private String name;

    private int status;

    private Timestamp startDate;

    private Timestamp endDate;

    private int triggerCnt;

    private long userCode;

    private String nickname;

    private String imgPath;

    private List<BattlePlayerInfo> playerList;

}

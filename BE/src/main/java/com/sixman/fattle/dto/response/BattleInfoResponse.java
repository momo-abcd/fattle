package com.sixman.fattle.dto.response;

import com.sixman.fattle.dto.dto.BattlePlayerInfo;
import com.sixman.fattle.dto.dto.BattleTriggerInfo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BattleInfoResponse {

    private String battleCode;

    private String battleName;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private List<String> betting;

    private List<BattlePlayerInfo> playerList;

    private List<BattleTriggerInfo> triggerList;

}

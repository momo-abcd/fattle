package com.sixman.fattle.repository;

import com.sixman.fattle.dto.dto.SimpleBattleInfo;
import com.sixman.fattle.dto.request.BattleSettingRequest;
import com.sixman.fattle.dto.request.PlayerWeightRequest;
import com.sixman.fattle.dto.request.RegistPlayerRequest;
import com.sixman.fattle.dto.request.RegistTriggerRequest;
import com.sixman.fattle.entity.Battle;

import java.util.List;

public interface BattleRepositoryCustom {

    String getBattle(String battleCode);

    long createBattle(Battle battle);

    List<String> getBattleCodeList(long userCode);

    List<SimpleBattleInfo> getBattleList(List<String> battleCodeList);

    boolean setPlayer(RegistPlayerRequest request);

    void setTrigger(RegistTriggerRequest request);

    void setBattle(BattleSettingRequest request);

    boolean setBattleStatus(String battleCode, int status);

    void setPlayerWeight(PlayerWeightRequest request);

}

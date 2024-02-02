package com.sixman.fattle.repository;

import com.sixman.fattle.dto.dto.BattleInfo;
import com.sixman.fattle.dto.dto.BattlePlayerInfo;
import com.sixman.fattle.dto.dto.BattleTriggerInfo;
import com.sixman.fattle.dto.dto.SimpleBattleInfo;
import com.sixman.fattle.dto.request.BattleSettingRequest;
import com.sixman.fattle.dto.request.PlayerWeightRequest;
import com.sixman.fattle.dto.request.RegistPlayerRequest;
import com.sixman.fattle.dto.request.RegistTriggerRequest;
import com.sixman.fattle.entity.Battle;

import java.util.List;

public interface BattleRepositoryCustom {

    int isBattleCodeExist(String battleCode);

    long createBattle(Battle battle);

    List<String> getBattleCodeList(long userCode);

    List<BattleInfo> getBattleList(List<String> battleCodeList);

    boolean setPlayer(RegistPlayerRequest request);

    void setTrigger(RegistTriggerRequest request);

    void setBattle(BattleSettingRequest request);

    boolean setBattleStatus(String battleCode, int status);

    void setPlayerWeight(PlayerWeightRequest request);

    SimpleBattleInfo getBattleInfo(String battleCode);

    List<String> getBettings(String battleCode);

    List<BattlePlayerInfo> getPlayerList(String battleCode);

    List<BattleTriggerInfo> getTriggerList(String battleCode);

    int getRemainPoint(String battleCode, long userCode);
}

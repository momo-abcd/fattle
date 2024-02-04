package com.sixman.fattle.repository;

import com.sixman.fattle.dto.dto.*;
import com.sixman.fattle.dto.request.*;
import com.sixman.fattle.entity.Battle;

import java.util.List;

public interface BattleRepositoryCustom {

    int isBattleCodeExist(String battleCode);

    long createBattle(Battle battle);

    List<String> getBattleCodeList(long userCode);

    List<BattleInfo> getBattleList(List<String> battleCodeList);

    boolean setPlayer(PlayerRequest request);

    void setTrigger(TriggerRequest request);

    void setBattle(BattleSettingRequest request);

    boolean setBattleStatus(String battleCode, int status);

    void setPlayerWeight(PlayerWeightRequest request);

    SimpleBattleInfo getBattleInfo(String battleCode);

    List<String> getBettings(String battleCode);

    List<BattlePlayerInfo> getPlayerList(String battleCode);

    List<BattleTriggerInfo> getTriggerList(String battleCode);

    void deleteBoard(String battleCode);

    void deleteComment(List<Integer> boardCodeList);

    void deletePoint(String battleCode);

    void deleteTrigger(String battleCode);

    void deleteTrigger(String battleCode, long userCode);

    void deletePlayer(String battleCode);

    void deletePlayer(String battleCode, long userCode);

    void deleteBetting(String battleCode);

    void deleteBattle(String battleCode);

    int isPlayerExist(String battleCode, long userCode);

    int isTriggerExist(String battleCode, long userCode);

    long modifyPlayer(PlayerRequest request);

    int getCurrentPoint(String battleCode, long userCode);

    void setPoint(BattlePointRequest request);

    void setLiveUserPoint(BattlePointRequest request);

    void setFoodUserPoint(BattlePointRequest request);

    void setPoint(String battleCode, long userCode, int type, int point);

    List<PointHistory> getPointHistory(String battleCode);

    int getGoalPoint(PlayerWeightRequest request);

    int getLivePoint(String battleCode, long userCode);

    int foodCount(long userCode, int type);

    List<String> getBattleCodeListAsPlayer(long userCode);
}

package com.sixman.fattle.api.service;

import com.querydsl.core.Tuple;
import com.sixman.fattle.dto.dto.*;
import com.sixman.fattle.dto.request.*;
import com.sixman.fattle.dto.response.*;
import com.sixman.fattle.entity.Battle;
import com.sixman.fattle.repository.BattleRepository;
import com.sixman.fattle.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BattleService {

    private final BattleRepository battleRepository;
    private final BattlePointService battlePointService;

    private final int LIVE_USER_POINT = 1;
    private final int FOOD_USER_POINT = 2;
    private final int LIVE_BASIC_POINT = 3;
    private final int FOOD_BASIC_POINT = 4;
    private final int QUEST_POINT = 5;
    private final int GOAL_POINT = 6;

    private final int STATUS_WAIT = 0;
    private final int STATUS_START = 1;
    private final int STATUS_END = 2;

    public BattleCreateResponse createBattle(BattleCreateRequest request) {
        long userCode = request.getUserCode();

        String battleCode = "";

        do {
            battleCode = CodeGenerator.createBattleCode();
        } while (battleRepository.isBattleCodeExist(battleCode) > 0);

        Battle battle = Battle.builder()
                        .battleCd(battleCode)
                        .creatorCd(userCode)
                        .build();

        long result = battleRepository.createBattle(battle);

        return BattleCreateResponse.builder()
                .code(battleCode)
                .build();
    }

    public BattleListResponse getBattleList(long userCode) {
        List<String> battleCodeList = battleRepository.getBattleCodeList(userCode);

        List<BattleInfo> infoList = battleRepository.getBattleList(battleCodeList);

        return BattleListResponse.builder()
                .list(infoList)
                .build();
    }

    public HttpStatus registPlayer(PlayerRequest request) {
        if (battleRepository.setPlayer(request)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    public void registTrigger(TriggerRequest request) {
        battleRepository.setTrigger(request);
    }

    public void battleSetting(BattleSettingRequest request) {
        battleRepository.setBattle(request);
    }

    public HttpStatus battleStart(String battleCode) {
        return setBattleStatus(battleCode, STATUS_START);
    }

    public HttpStatus battleFinish(String battleCode) {
        return setBattleStatus(battleCode, STATUS_END);
    }

    private HttpStatus setBattleStatus(String battleCode, int status) {
        if (battleRepository.setBattleStatus(battleCode, status)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    public void setPlayerWeight(PlayerWeightRequest request) {
        battleRepository.setPlayerWeight(request);

        int point = battleRepository.getGoalPoint(request);

        battlePointService.setBattlePoint(request.getBattleCode(), request.getUserCode(), GOAL_POINT, point);
    }

    public BattleInfoResponse getBattleInfo(String battleCode) {
        SimpleBattleInfo battleInfo = battleRepository.getBattleInfo(battleCode);
        List<String> betting = battleRepository.getBettings(battleCode);
        List<BattlePlayerInfo> playerList = battleRepository.getPlayerList(battleCode);

        int status = battleInfo.getStatus();

        if (status != STATUS_END) {
            for (BattlePlayerInfo info : playerList) {
                info.setLivePoint(0);
                info.setFoodPoint(0);
                info.setQuestPoint(0);
                info.setGoalPoint(0);
            }
        }

        List<BattleTriggerInfo> triggerList = battleRepository.getTriggerList(battleCode);

        return BattleInfoResponse.builder()
                .battleCode(battleCode)
                .battleName(battleInfo.getName())
                .startDate(battleInfo.getStartDate().toLocalDateTime())
                .endDate(battleInfo.getEndDate().toLocalDateTime())
                .betting(betting)
                .playerList(playerList)
                .triggerList(triggerList)
                .build();
    }

    public HttpStatus deleteBattle(String battleCode) {
        int chk = battleRepository.isBattleCodeExist(battleCode);

        if (chk == 0) {
            return HttpStatus.BAD_REQUEST;
        }

        battleRepository.deleteBoard(battleCode);
        battleRepository.deletePoint(battleCode);
        battleRepository.deleteTrigger(battleCode);
        battleRepository.deletePlayer(battleCode);
        battleRepository.deleteBetting(battleCode);
        battleRepository.deleteBattle(battleCode);

        return HttpStatus.OK;
    }

    public HttpStatus deletePlayer(String battleCode, long userCode) {
        int chk = battleRepository.isPlayerExist(battleCode, userCode);

        if (chk == 0) {
            return HttpStatus.BAD_REQUEST;
        }

        battleRepository.deletePlayer(battleCode, userCode);

        return HttpStatus.OK;
    }

    public HttpStatus deleteTrigger(String battleCode, long userCode) {
        int chk = battleRepository.isTriggerExist(battleCode, userCode);

        if (chk == 0) {
            return HttpStatus.BAD_REQUEST;
        }

        battleRepository.deleteTrigger(battleCode, userCode);

        return HttpStatus.OK;
    }

    public HttpStatus modifyPlayer(PlayerRequest request) {
        long cnt = battleRepository.modifyPlayer(request);
        if (cnt == 0) {
            return HttpStatus.BAD_REQUEST;
        } else {
            return HttpStatus.OK;
        }
    }

}

package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.dto.BattleInfo;
import com.sixman.fattle.dto.dto.BattlePlayerInfo;
import com.sixman.fattle.dto.dto.BattleTriggerInfo;
import com.sixman.fattle.dto.dto.SimpleBattleInfo;
import com.sixman.fattle.dto.request.*;
import com.sixman.fattle.dto.response.BattleCreateResponse;
import com.sixman.fattle.dto.response.BattleInfoResponse;
import com.sixman.fattle.dto.response.BattleListResponse;
import com.sixman.fattle.dto.response.RemainPointResponse;
import com.sixman.fattle.entity.Battle;
import com.sixman.fattle.repository.BattleRepository;
import com.sixman.fattle.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BattleService {

    private final BattleRepository battleRepository;

    private final int STATUS_WAIT = 0;
    private final int STATUS_START = 1;
    private final int STATUS_END = 2;
    private final int MAX_POINT = 1000;
    private final int LIVE_USER_POINT = 1;
    private final int FOOD_USER_POINT = 2;

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

    public HttpStatus registPlayer(RegistPlayerRequest request) {
        if (battleRepository.setPlayer(request)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    public void registTrigger(RegistTriggerRequest request) {
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

    public RemainPointResponse getRemainPoint(String battleCode, long userCode) {
        int currentPoint = battleRepository.getRemainPoint(battleCode, userCode);
        return RemainPointResponse.builder()
                .maxPoint(MAX_POINT)
                .currentPoint(currentPoint)
                .remainPoint(MAX_POINT - currentPoint)
                .build();
    }

    public HttpStatus setBattlePoint(BattlePointRequest request) {
        String battleCode = request.getBattleCode();;
        long triggerUserCode = request.getTriggerUserCode();
        int type = request.getType();
        int point = request.getPoint();

        if (type == LIVE_USER_POINT) {
            int currentPoint = battleRepository.getRemainPoint(battleCode, triggerUserCode);
            if (currentPoint + point >= MAX_POINT) {
                return HttpStatus.BAD_REQUEST;
            }
            battleRepository.setLiveUserPoint(request);
        } else if (type == FOOD_USER_POINT) {
            if (0 >= point || point > 100) {
                return HttpStatus.BAD_REQUEST;
            }
            battleRepository.setFoodUserPoint(request);
        } else {
            return HttpStatus.BAD_REQUEST;
        }

        battleRepository.setPoint(request);

        return HttpStatus.OK;
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
}

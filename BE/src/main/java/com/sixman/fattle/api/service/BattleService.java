package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.dto.SimpleBattleInfo;
import com.sixman.fattle.dto.request.*;
import com.sixman.fattle.dto.response.BattleCreateResponse;
import com.sixman.fattle.dto.response.BattleListResponse;
import com.sixman.fattle.entity.Battle;
import com.sixman.fattle.repository.BattleRepository;
import com.sixman.fattle.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    public BattleCreateResponse createBattle(BattleCreateRequest request) {
        long userCode = request.getUserCode();

        String battleCode = "";

        do {
            battleCode = CodeGenerator.createBattleCode();
        } while (battleRepository.getBattle(battleCode) != null);

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

        List<SimpleBattleInfo> infoList = battleRepository.getBattleList(battleCodeList);

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
}

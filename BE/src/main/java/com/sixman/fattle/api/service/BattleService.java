package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.dto.SimpleBattleInfo;
import com.sixman.fattle.dto.request.BattleCreateRequest;
import com.sixman.fattle.dto.request.BattleSettingRequest;
import com.sixman.fattle.dto.request.RegistPlayerRequest;
import com.sixman.fattle.dto.request.RegistTriggerRequest;
import com.sixman.fattle.dto.response.BattleCreateResponse;
import com.sixman.fattle.dto.response.BattleListResponse;
import com.sixman.fattle.entity.Battle;
import com.sixman.fattle.repository.BattleRepository;
import com.sixman.fattle.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BattleService {

    private final BattleRepository battleRepository;

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

    public void registPlayer(RegistPlayerRequest request) {
        battleRepository.setPlayer(request);
    }

    public void registTrigger(RegistTriggerRequest request) {
        battleRepository.setTrigger(request);
    }

    public void battleSetting(BattleSettingRequest request) {
        battleRepository.setBattle(request);
    }
}

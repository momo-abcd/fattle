package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.dto.PointHistory;
import com.sixman.fattle.dto.request.BattlePointRequest;
import com.sixman.fattle.dto.response.PointHistoryResponse;
import com.sixman.fattle.dto.response.RemainPointResponse;
import com.sixman.fattle.repository.BattleRepository;
import com.sixman.fattle.utils.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BattlePointService {

    private final BattleRepository battleRepository;

    public RemainPointResponse getRemainPoint(String battleCode, long userCode) {
        int currentPoint = battleRepository.getCurrentPoint(battleCode, userCode);
        return RemainPointResponse.builder()
                .maxPoint(Const.MAX_LIVE_USER_POINT)
                .currentPoint(currentPoint)
                .remainPoint(Const.MAX_LIVE_USER_POINT - currentPoint)
                .build();
    }

    public HttpStatus setBattlePoint(BattlePointRequest request) {
        String battleCode = request.getBattleCode();;
        long triggerUserCode = request.getTriggerUserCode();
        int type = request.getType();
        int point = request.getPoint();

        if (type == Const.TYPE_LIVE_USER_POINT) {
            int currentPoint = battleRepository.getCurrentPoint(battleCode, triggerUserCode);
            if (currentPoint + point > Const.MAX_LIVE_USER_POINT) {
                return HttpStatus.BAD_REQUEST;
            }
            battleRepository.setLiveUserPoint(request);
        } else if (type == Const.TYPE_FOOD_USER_POINT) {
            if (0 > point || point > 30) {
                return HttpStatus.BAD_REQUEST;
            }
            battleRepository.setFoodUserPoint(request);
        } else {
            return HttpStatus.BAD_REQUEST;
        }

        battleRepository.setPoint(request);

        return HttpStatus.OK;
    }

    public void setBattlePoint(String battleCode, long userCode, int type, int point) {
        battleRepository.setPoint(battleCode, userCode, type, point);
    }

    public PointHistoryResponse getPointHistory(String battleCode) {
        List<PointHistory> pointHistoryList = battleRepository.getPointHistory(battleCode);
        return PointHistoryResponse.builder()
                .list(pointHistoryList)
                .build();
    }

    public HttpStatus liveOn(String battleCode, long userCode) {
        int cnt = battleRepository.getLivePoint(battleCode, userCode);

        if (cnt == 0) {
            battleRepository.setPoint(battleCode, userCode, Const.TYPE_LIVE_BASIC_POINT, Const.LIVE_BASIC_POINT);
            return HttpStatus.OK;
        } else {
            return HttpStatus.ACCEPTED;
        }
    }

    public void foodUpload(long userCode, int type) {
        int cnt = battleRepository.foodCount(userCode, type);

        if (cnt == 0) {
            List<String> list = battleRepository.getBattleCodeListAsPlayer(userCode);

            for (String code : list) {
                setBattlePoint(code, userCode, Const.TYPE_FOOD_BASIC_POINT, Const.FOOD_BASIC_POINT);
            }
        }
    }

    public void quest(long userCode) {
        List<String> list = battleRepository.getBattleCodeListAsPlayer(userCode);

        for (String code : list) {
            setBattlePoint(code, userCode, Const.TYPE_QUEST_POINT, Const.QUEST_POINT);
        }
    }

}

package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.dto.PointHistory;
import com.sixman.fattle.dto.request.BattlePointRequest;
import com.sixman.fattle.dto.response.PointHistoryResponse;
import com.sixman.fattle.dto.response.RemainPointResponse;
import com.sixman.fattle.repository.BattleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BattlePointService {

    private final BattleRepository battleRepository;

    private final int MAX_POINT = 1000;
    private final int LIVE_USER_POINT = 1;
    private final int FOOD_USER_POINT = 2;

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

    public PointHistoryResponse getPointHistory(String battleCode) {
        List<PointHistory> pointHistoryList = battleRepository.getPointHistory(battleCode);
        return PointHistoryResponse.builder()
                .list(pointHistoryList)
                .build();
    }

}

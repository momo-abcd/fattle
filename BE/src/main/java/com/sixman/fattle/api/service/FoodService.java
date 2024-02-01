package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.response.TodaysFoodResponse;
import com.sixman.fattle.entity.Food;
import com.sixman.fattle.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public TodaysFoodResponse todaysFood(long userCode) {
        List<Food> foodList = foodRepository.todaysFood(userCode);

        return TodaysFoodResponse.builder()
                .list(foodList)
                .build();
    }
}

package com.sixman.fattle.repository;

import com.sixman.fattle.entity.Food;

import java.util.List;

public interface FoodRepositoryCustom {

    List<Food> todaysFood(long userCode);

}

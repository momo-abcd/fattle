package com.sixman.fattle.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixman.fattle.entity.Food;
import com.sixman.fattle.entity.QFood;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FoodRepositoryImpl implements FoodRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QFood qfood = QFood.food;

    @Override
    public List<Food> todaysFood(long userCode) {
        return queryFactory
                .select(qfood)
                .from(qfood)
                .where(qfood.userCd.eq(userCode),
                        qfood.recDt.after(Timestamp.valueOf(LocalDate.now().atStartOfDay())))
                .fetch();
    }

}

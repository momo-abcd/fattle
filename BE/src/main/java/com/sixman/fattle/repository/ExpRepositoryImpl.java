package com.sixman.fattle.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixman.fattle.entity.ExpHistory;
import com.sixman.fattle.entity.QExpHistory;
import com.sixman.fattle.entity.QUser;
import com.sixman.fattle.utils.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExpRepositoryImpl implements ExpRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QExpHistory qexp = QExpHistory.expHistory;
    private final QUser quser = QUser.user;

    @Override
    public List<ExpHistory> getExpHistory(long userCode, LocalDate date) {
        return queryFactory
                .select(qexp)
                .from(qexp)
                .where(qexp.userCd.eq(userCode),
                        qexp.recDt.between(date.atStartOfDay(), date.plusDays(1).atStartOfDay()))
                .fetch();
    }

    @Override
    public void setExp(long userCode, String type, String content, int exp) {
        Tuple exps = queryFactory
                .select(
                        quser.growthExp,
                        quser.stackExp)
                .from(quser)
                .where(quser.userCd.eq(userCode))
                .fetchFirst();

        int growthExp = exps.get(quser.growthExp);
        int stackExp = exps.get(quser.stackExp);

        int remGrowthExp = Const.MAX_GROWTH_EXP - growthExp;

        int getGrowthExp = Math.min(exp, remGrowthExp);
        int getStackExp = exp - getGrowthExp;

        queryFactory
                .update(quser)
                .set(quser.growthExp, growthExp + getGrowthExp)
                .set(quser.stackExp, stackExp + getStackExp)
                .where(quser.userCd.eq(userCode))
                .execute();

        queryFactory
                .insert(qexp)
                .columns(
                        qexp.userCd,
                        qexp.type,
                        qexp.content,
                        qexp.point)
                .values(
                        userCode,
                        type,
                        content,
                        exp)
                .execute();
    }

}

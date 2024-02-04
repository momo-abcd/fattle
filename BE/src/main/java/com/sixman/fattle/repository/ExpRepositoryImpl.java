package com.sixman.fattle.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixman.fattle.entity.ExpHistory;
import com.sixman.fattle.entity.QExpHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExpRepositoryImpl implements ExpRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QExpHistory qexp = QExpHistory.expHistory;

    @Override
    public List<ExpHistory> getExpHistory(long userCode, LocalDate date) {
        return queryFactory
                .select(qexp)
                .from(qexp)
                .where(qexp.userCd.eq(userCode),
                        qexp.recDt.between(date.atStartOfDay(), date.plusDays(1).atStartOfDay()))
                .fetch();
    }

}

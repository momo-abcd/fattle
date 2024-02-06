package com.sixman.fattle.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixman.fattle.entity.QQuest;
import com.sixman.fattle.entity.Quest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class QuestRepositoryImpl implements QuestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QQuest qquest = QQuest.quest;

    @Override
    public Quest getDailyQuest(long userCode) {
        return queryFactory
                .select(qquest)
                .from(qquest)
                .where(qquest.userCd.eq(userCode),
                        qquest.recDt.after(LocalDate.now().atStartOfDay()))
                .fetchFirst();
    }

    @Override
    public void createQuest(long userCode) {
        queryFactory.insert(qquest)
                .columns(qquest.userCd)
                .values(userCode)
                .execute();
    }

}

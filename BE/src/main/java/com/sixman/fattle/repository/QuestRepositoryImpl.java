package com.sixman.fattle.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixman.fattle.entity.QQuest;
import com.sixman.fattle.entity.Quest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

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

    @Override
    public int getCount(long userCode) {
        Tuple cnt = queryFactory
                .select(
                qquest.dayChk,
                qquest.exerciseCnt,
                qquest.foodCnt)
                .from(qquest)
                .where(qquest.userCd.eq(userCode),
                        qquest.recDt.after(LocalDate.now().atStartOfDay()))
                .fetchFirst();

        return cnt.get(qquest.dayChk) + cnt.get(qquest.exerciseCnt) + cnt.get(qquest.foodCnt);
    }

    @Override
    public void setFinish(long userCode) {
        queryFactory
                .update(qquest)
                .set(qquest.isFinish, 1)
                .where(qquest.userCd.eq(userCode),
                        qquest.recDt.after(LocalDate.now().atStartOfDay()))
                .execute();
    }

}

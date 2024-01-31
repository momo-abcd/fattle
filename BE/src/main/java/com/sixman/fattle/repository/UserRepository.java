package com.sixman.fattle.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixman.fattle.dto.request.SignUpRequest;
import com.sixman.fattle.entity.QFood;
import com.sixman.fattle.entity.QHealth;
import com.sixman.fattle.entity.QUser;
import com.sixman.fattle.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JPAQueryFactory queryFactory;

    private final QUser quser = QUser.user;
    private final QHealth qhealth = QHealth.health;
    private final QFood qfood = QFood.food;

    public User getUser(long userCode) {
        return queryFactory.select(quser)
                .from(quser)
                .where(quser.userCd.eq(userCode))
                .fetch()
                .get(0);
    }

    public void joinUser(SignUpRequest request) {
        queryFactory.insert(quser)
                .columns(
                        quser.userCd,
                        quser.nickname,
                        quser.sex,
                        quser.goalWeight,
                        quser.goalCalory,
                        quser.goalProtein,
                        quser.goalFat)
                .values(
                        request.getUserCode(),
                        request.getNickname(),
                        request.getSex(),
                        request.getGoalWeight(),
                        request.getGoalCalory(),
                        request.getGoalProtein(),
                        request.getGoalFat())
                .execute();

        queryFactory.insert(qhealth)
                .columns(
                        qhealth.userCd,
                        qhealth.height,
                        qhealth.weight)
                .values(
                        request.getUserCode(),
                        request.getHeight(),
                        request.getWeight())
                .execute();
    }

    public int checkNickname(String nickname) {
        return queryFactory.select(quser.count())
                .from(quser)
                .where(quser.nickname.eq(nickname))
                .fetchFirst()
                .intValue();
    }



}

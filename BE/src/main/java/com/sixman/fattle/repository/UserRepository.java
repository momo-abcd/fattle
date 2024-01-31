package com.sixman.fattle.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixman.fattle.dto.request.SignUpRequest;
import com.sixman.fattle.dto.response.UserInfoResponse;
import com.sixman.fattle.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JPAQueryFactory queryFactory;

    private final QUser quser = QUser.user;
    private final QHealth qhealth = QHealth.health;
    private final QFood qfood = QFood.food;
    private final QRanking qranking = QRanking.ranking;

    public User getUser(long userCode) {
        return queryFactory
                .select(quser)
                .from(quser)
                .where(quser.userCd.eq(userCode))
                .fetchFirst();
    }

    public void joinUser(SignUpRequest request) {
        queryFactory
                .insert(quser)
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

        queryFactory
                .insert(qhealth)
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
        return queryFactory
                .select(quser.count())
                .from(quser)
                .where(quser.nickname.eq(nickname))
                .fetchFirst()
                .intValue();
    }

//    public UserInfoResponse getUserInfo(long userCode) {
//        int rank = queryFactory
//                .select(qranking.rank)
//                .from(qranking)
//                .where(qranking.userCd.eq(userCode))
//                .fetchFirst();
//
//        List<Tuple> foodList
//                = queryFactory
//                .select()
//
//        return null;
//    }

}

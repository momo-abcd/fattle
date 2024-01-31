package com.sixman.fattle.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixman.fattle.dto.dto.BattlePlayerInfo;
import com.sixman.fattle.dto.dto.SimpleBattleInfo;
import com.sixman.fattle.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BattleRepository {

    private final JPAQueryFactory queryFactory;

    private final QBattle qbattle = QBattle.battle;
    private final QUser quser = QUser.user;
    private final QAvatar qavatar = QAvatar.avatar;
    private final QBattlePlayer qbp = QBattlePlayer.battlePlayer;
    private final QBattleTrigger qbt = QBattleTrigger.battleTrigger;

    public int getBattle(String battleCode) {
        return queryFactory.select(qbattle)
                .from(qbattle)
                .where(qbattle.battleCd.in(battleCode))
                .fetch()
                .size();
    }

    public long createBattle(Battle battle) {
        return queryFactory.insert(qbattle)
                .columns(qbattle.battleCd, qbattle.creatorCd)
                .values(battle.getBattleCd(), battle.getCreatorCd())
                .execute();
    }

    public List<String> battleCodeList(long userCode) {
        List<String> listAsPlayer = queryFactory.select(qbp.battleCd)
                .from(qbp)
                .where(qbp.userCd.eq(userCode))
                .fetch();

        List<String> listAsTrigger = queryFactory.select(qbt.battleCd)
                .from(qbt)
                .where(qbt.userCd.eq(userCode))
                .fetch();

        listAsPlayer.addAll(listAsTrigger);

        return listAsPlayer;
    }

    public List<SimpleBattleInfo> getBattleList(List<String> battleCodeList) {
        List<SimpleBattleInfo> battleList = new ArrayList<>();

        List<Tuple> tupleList
                = queryFactory.select(
                        qbattle.battleCd,
                        qbattle.name,
                        qbattle.status,
                        qbattle.startDt,
                        qbattle.endDt,
                        quser.userCd,
                        quser.nickname,
                        qavatar.imgPath)
                .from(qbattle)
                .where(qbattle.battleCd.in(battleCodeList))
                .join(quser)
                .on(qbattle.creatorCd.eq(quser.userCd))
                .join(qavatar)
                .on(quser.avatarCd.eq(qavatar.avatarCd))
                .fetch();

        for (Tuple tuple : tupleList) {
            List<BattlePlayerInfo> playerList
                    = queryFactory.select(
                            Projections.constructor(
                                    BattlePlayerInfo.class,
                                    quser.userCd,
                                    quser.nickname))
                    .from(quser)
                    .where(quser.userCd.in(
                            JPAExpressions.select(qbp.userCd)
                                    .from(qbp)
                                    .where(qbp.battleCd.eq(tuple.get(qbattle.battleCd)))))
                    .fetch();

            int triggerCnt
                    = queryFactory.select(qbt.count())
                    .from(qbt)
                    .where(qbt.battleCd.eq(tuple.get(qbattle.battleCd)))
                    .fetchFirst()
                    .intValue();

            SimpleBattleInfo info = SimpleBattleInfo.builder()
                    .battleCode(tuple.get(qbattle.battleCd))
                    .name(tuple.get(qbattle.name))
                    .status(tuple.get(qbattle.status))
                    .startDate(tuple.get(qbattle.startDt))
                    .endDate(tuple.get(qbattle.endDt))
                    .triggerCnt(triggerCnt)
                    .userCode(tuple.get(quser.userCd))
                    .nickname(tuple.get(quser.nickname))
                    .imgPath(tuple.get(qavatar.imgPath))
                    .playerList(playerList)
                    .build();

            battleList.add(info);
        }

        return battleList;
    }

}

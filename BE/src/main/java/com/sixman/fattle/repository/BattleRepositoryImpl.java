package com.sixman.fattle.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixman.fattle.dto.dto.*;
import com.sixman.fattle.dto.request.BattleSettingRequest;
import com.sixman.fattle.dto.request.PlayerWeightRequest;
import com.sixman.fattle.dto.request.RegistPlayerRequest;
import com.sixman.fattle.dto.request.RegistTriggerRequest;
import com.sixman.fattle.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BattleRepositoryImpl implements BattleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QBattle qbattle = QBattle.battle;
    private final QUser quser = QUser.user;
    private final QAvatar qavatar = QAvatar.avatar;
    private final QBattlePlayer qbp = QBattlePlayer.battlePlayer;
    private final QBattleTrigger qbt = QBattleTrigger.battleTrigger;
    private final QBattleBetting qbb = QBattleBetting.battleBetting;

    @Override
    public int isBattleCodeExist(String battleCode) {
        return queryFactory.select(qbattle.count())
                .from(qbattle)
                .where(qbattle.battleCd.in(battleCode))
                .fetchFirst()
                .intValue();
    }

    @Override
    public long createBattle(Battle battle) {
        return queryFactory.insert(qbattle)
                .columns(qbattle.battleCd, qbattle.creatorCd)
                .values(battle.getBattleCd(), battle.getCreatorCd())
                .execute();
    }

    @Override
    public List<String> getBattleCodeList(long userCode) {
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

    @Override
    public List<BattleInfo> getBattleList(List<String> battleCodeList) {
        List<BattleInfo> battleList = new ArrayList<>();

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
            List<SimpleBattlePlayerInfo> playerList
                    = queryFactory.select(
                            Projections.constructor(
                                    SimpleBattlePlayerInfo.class,
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

            BattleInfo info = BattleInfo.builder()
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

    @Override
    public boolean setPlayer(RegistPlayerRequest request) {
        int cnt = queryFactory
                .select(qbp.count())
                .from(qbp)
                .where(qbp.battleCd.eq(request.getBattleCode()))
                .fetchFirst()
                .intValue();

        if (cnt >= 2) {
            return false;
        }

        queryFactory
                .insert(qbp)
                .columns(
                        qbp.battleCd,
                        qbp.userCd,
                        qbp.beforeWeight,
                        qbp.goalWeight)
                .values(
                        request.getBattleCode(),
                        request.getUserCode(),
                        request.getBeforeWeight(),
                        request.getGoalWeight())
                .execute();

        return true;
    }

    @Override
    public void setTrigger(RegistTriggerRequest request) {
        queryFactory
                .insert(qbt)
                .columns(
                        qbt.battleCd,
                        qbt.userCd)
                .values(
                        request.getBattleCode(),
                        request.getUserCode())
                .execute();
    }

    @Override
    public void setBattle(BattleSettingRequest request) {
        String battleCode = request.getBattleCode();

        queryFactory
                .update(qbattle)
                .set(qbattle.name, request.getBattleName())
                .set(qbattle.startDt, Timestamp.valueOf(request.getStartDate()))
                .set(qbattle.endDt, Timestamp.valueOf(request.getEndDate()))
                .where(qbattle.battleCd.eq(request.getBattleCode()))
                .execute();

        queryFactory
                .delete(qbb)
                .where(qbb.battleCd.eq(battleCode))
                .execute();

        List<String> bettingList = request.getBetting();

        for (String betting : bettingList) {
            queryFactory
                    .insert(qbb)
                    .set(qbb.battleCd, battleCode)
                    .set(qbb.content, betting)
                    .execute();
        }
    }

    @Override
    public boolean setBattleStatus(String battleCode, int status) {
        int curStatus = queryFactory
                .select(qbattle.status)
                .from(qbattle)
                .where(qbattle.battleCd.eq(battleCode))
                .fetchFirst();

        if (curStatus != 0 && status == 1) {
            return false;
        } else if (curStatus != 1 && status == 2) {
            return false;
        }

        queryFactory
                .update(qbattle)
                .set(qbattle.status, status)
                .execute();

        return true;
    }

    @Override
    public void setPlayerWeight(PlayerWeightRequest request) {
        queryFactory
                .update(qbp)
                .set(qbp.afterWeight, request.getWeight())
                .where(qbp.battleCd.eq(request.getBattleCode()),
                        qbp.userCd.eq(request.getUserCode()))
                .execute();
    }

    @Override
    public SimpleBattleInfo getBattleInfo(String battleCode) {
        return queryFactory
                .select(
                        Projections.constructor(
                                SimpleBattleInfo.class,
                                qbattle.name,
                                qbattle.status,
                                qbattle.startDt,
                                qbattle.endDt))
                .from(qbattle)
                .where(qbattle.battleCd.eq(battleCode))
                .fetchFirst();
    }

    @Override
    public List<String> getBettings(String battleCode) {
        return queryFactory
                .select(qbb.content)
                .from(qbb)
                .where(qbb.battleCd.eq(battleCode))
                .fetch();
    }

    @Override
    public List<BattlePlayerInfo> getPlayerList(String battleCode) {
        return queryFactory
                .select(
                        Projections.bean(
                                BattlePlayerInfo.class,
                                qbp.userCd,
                                quser.nickname,
                                qbp.beforeWeight,
                                qbp.afterWeight,
                                qbp.goalWeight,
                                qbp.livePt,
                                qbp.foodPt,
                                qbp.liveUserPt,
                                qbp.foodUserPt,
                                qbp.questPt,
                                qbp.goalPt,
                                qavatar.imgPath))
                .from(qbp)
                .where(qbp.battleCd.eq(battleCode))
                .join(quser)
                .on(qbp.userCd.eq(quser.userCd))
                .join(qavatar)
                .on(quser.avatarCd.eq(qavatar.avatarCd))
                .fetch();
    }

    @Override
    public List<BattleTriggerInfo> getTriggerList(String battleCode) {
        return queryFactory
                .select(
                        Projections.constructor(
                                BattleTriggerInfo.class,
                                qbt.userCd,
                                quser.nickname,
                                qavatar.imgPath))
                .from(qbt)
                .where(qbt.battleCd.eq(battleCode))
                .join(quser)
                .on(qbt.userCd.eq(quser.userCd))
                .join(qavatar)
                .on(quser.avatarCd.eq(qavatar.avatarCd))
                .fetch();
    }

}

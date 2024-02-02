package com.sixman.fattle.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixman.fattle.dto.dto.*;
import com.sixman.fattle.dto.request.*;
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
    private final QBattlePlayer qplayer = QBattlePlayer.battlePlayer;
    private final QBattleTrigger qtrigger = QBattleTrigger.battleTrigger;
    private final QBattleBetting qbetting = QBattleBetting.battleBetting;
    private final QBattlePoint qpoint = QBattlePoint.battlePoint;
    private final QFoodBoard qboard = QFoodBoard.foodBoard;
    private final QFoodComment qcomment = QFoodComment.foodComment;

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
        List<String> listAsPlayer = queryFactory.select(qplayer.battleCd)
                .from(qplayer)
                .where(qplayer.userCd.eq(userCode))
                .fetch();

        List<String> listAsTrigger = queryFactory.select(qtrigger.battleCd)
                .from(qtrigger)
                .where(qtrigger.userCd.eq(userCode))
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
                            JPAExpressions.select(qplayer.userCd)
                                    .from(qplayer)
                                    .where(qplayer.battleCd.eq(tuple.get(qbattle.battleCd)))))
                    .fetch();

            int triggerCnt
                    = queryFactory.select(qtrigger.count())
                    .from(qtrigger)
                    .where(qtrigger.battleCd.eq(tuple.get(qbattle.battleCd)))
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
                .select(qplayer.count())
                .from(qplayer)
                .where(qplayer.battleCd.eq(request.getBattleCode()))
                .fetchFirst()
                .intValue();

        if (cnt >= 2) {
            return false;
        }

        queryFactory
                .insert(qplayer)
                .columns(
                        qplayer.battleCd,
                        qplayer.userCd,
                        qplayer.beforeWeight,
                        qplayer.goalWeight)
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
                .insert(qtrigger)
                .columns(
                        qtrigger.battleCd,
                        qtrigger.userCd)
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
                .delete(qbetting)
                .where(qbetting.battleCd.eq(battleCode))
                .execute();

        List<String> bettingList = request.getBetting();

        for (String betting : bettingList) {
            queryFactory
                    .insert(qbetting)
                    .set(qbetting.battleCd, battleCode)
                    .set(qbetting.content, betting)
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
                .update(qplayer)
                .set(qplayer.afterWeight, request.getWeight())
                .where(qplayer.battleCd.eq(request.getBattleCode()),
                        qplayer.userCd.eq(request.getUserCode()))
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
                .select(qbetting.content)
                .from(qbetting)
                .where(qbetting.battleCd.eq(battleCode))
                .fetch();
    }

    @Override
    public List<BattlePlayerInfo> getPlayerList(String battleCode) {
        return queryFactory
                .select(
                        Projections.bean(
                                BattlePlayerInfo.class,
                                qplayer.userCd,
                                quser.nickname,
                                qplayer.beforeWeight,
                                qplayer.afterWeight,
                                qplayer.goalWeight,
                                qplayer.livePt,
                                qplayer.foodPt,
                                qplayer.liveUserPt,
                                qplayer.foodUserPt,
                                qplayer.questPt,
                                qplayer.goalPt,
                                qavatar.imgPath))
                .from(qplayer)
                .where(qplayer.battleCd.eq(battleCode))
                .join(quser)
                .on(qplayer.userCd.eq(quser.userCd))
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
                                qtrigger.userCd,
                                quser.nickname,
                                qavatar.imgPath))
                .from(qtrigger)
                .where(qtrigger.battleCd.eq(battleCode))
                .join(quser)
                .on(qtrigger.userCd.eq(quser.userCd))
                .join(qavatar)
                .on(quser.avatarCd.eq(qavatar.avatarCd))
                .fetch();
    }

    @Override
    public int getRemainPoint(String battleCode, long userCode) {
        return queryFactory
                .select(qtrigger.livePt)
                .from(qtrigger)
                .where(qtrigger.battleCd.eq(battleCode),
                        qtrigger.userCd.eq(userCode))
                .fetchFirst();
    }

    @Override
    public void setPoint(BattlePointRequest request) {
        queryFactory
                .insert(qpoint)
                .set(qpoint.battleCd, request.getBattleCode())
                .set(qpoint.playerCd, request.getPlayerUserCode())
                .set(qpoint.triggerCd, request.getTriggerUserCode())
                .set(qpoint.type, request.getType())
                .set(qpoint.point, request.getPoint())
                .execute();
    }

    @Override
    public void setLiveUserPoint(BattlePointRequest request) {
        int liveUserPoint
                = queryFactory
                .select(qplayer.liveUserPt)
                .from(qplayer)
                .where(qplayer.battleCd.eq(request.getBattleCode()),
                        qplayer.userCd.eq(request.getPlayerUserCode()))
                .fetchFirst();

        queryFactory
                .update(qplayer)
                .set(qplayer.liveUserPt, liveUserPoint + request.getPoint())
                .where(qplayer.battleCd.eq(request.getBattleCode()),
                        qplayer.userCd.eq(request.getPlayerUserCode()))
                .execute();
    }

    @Override
    public void setFoodUserPoint(BattlePointRequest request) {
        int foodUserPoint
                = queryFactory
                .select(qplayer.foodUserPt)
                .from(qplayer)
                .where(qplayer.battleCd.eq(request.getBattleCode()),
                        qplayer.userCd.eq(request.getPlayerUserCode()))
                .fetchFirst();

        queryFactory
                .update(qplayer)
                .set(qplayer.foodUserPt, foodUserPoint + request.getPoint())
                .where(qplayer.battleCd.eq(request.getBattleCode()),
                        qplayer.userCd.eq(request.getPlayerUserCode()))
                .execute();
    }

    @Override
    public void deleteBoard(String battleCode) {
        List<Integer> boardCodeList
                = queryFactory
                .select(qboard.foodBoardCd)
                .from(qboard)
                .where(qboard.battleCd.eq(battleCode))
                .fetch();

        deleteComment(boardCodeList);

        queryFactory
                .delete(qboard)
                .where(qboard.battleCd.eq(battleCode))
                .execute();
    }

    @Override
    public void deleteComment(List<Integer> boardCodeList) {
        queryFactory
                .delete(qcomment)
                .where(qcomment.foodBoardCd.in(boardCodeList))
                .execute();
    }

    @Override
    public void deletePoint(String battleCode) {
        queryFactory
                .delete(qpoint)
                .where(qpoint.battleCd.eq(battleCode))
                .execute();
    }

    @Override
    public void deleteTrigger(String battleCode) {
        queryFactory
                .delete(qtrigger)
                .where(qtrigger.battleCd.eq(battleCode))
                .execute();
    }

    @Override
    public void deletePlayer(String battleCode) {
        queryFactory
                .delete(qplayer)
                .where(qplayer.battleCd.eq(battleCode))
                .execute();
    }

    @Override
    public void deletePlayer(String battleCode, long userCode) {
        queryFactory
                .delete(qplayer)
                .where(qplayer.battleCd.eq(battleCode),
                        qplayer.userCd.eq(userCode))
                .execute();
    }

    @Override
    public void deleteTrigger(String battleCode, long userCode) {
        queryFactory
                .delete(qtrigger)
                .where(qtrigger.battleCd.eq(battleCode),
                        qtrigger.userCd.eq(userCode))
                .execute();
    }

    @Override
    public void deleteBetting(String battleCode) {
        queryFactory
                .delete(qbetting)
                .where(qbetting.battleCd.eq(battleCode))
                .execute();
    }

    @Override
    public void deleteBattle(String battleCode) {
        queryFactory
                .delete(qbattle)
                .where(qbattle.battleCd.eq(battleCode))
                .execute();
    }

    @Override
    public int isPlayerExist(String battleCode, long userCode) {
        return queryFactory
                .select(qplayer.count())
                .from(qplayer)
                .where(qplayer.battleCd.eq(battleCode),
                        qplayer.userCd.eq(userCode))
                .fetchFirst()
                .intValue();
    }

    @Override
    public int isTriggerExist(String battleCode, long userCode) {
        return queryFactory
                .select(qtrigger.count())
                .from(qtrigger)
                .where(qtrigger.battleCd.eq(battleCode),
                        qtrigger.userCd.eq(userCode))
                .fetchFirst()
                .intValue();
    }

}

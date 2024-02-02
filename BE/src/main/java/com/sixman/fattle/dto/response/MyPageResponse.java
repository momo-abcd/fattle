package com.sixman.fattle.dto.response;

import com.sixman.fattle.dto.DailyQuestDto;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;


@Data
@Builder
public class MyPageResponse {

    private long userCode;
    private String nickname;
    // 알림 추가 여부 논의 필요
    private int followerCnt;
    private int followingCnt;
    private String introduction;
    private float goalWeight;
    private int goalCalory;
    private int goalCarbo;
    private int goalProtein;
    private int goalFat;
    // 캘린더 기능 추가 필요
    private Timestamp recDate;
    private int dayCheck;
    private int exerciseCnt;
    private int foodCnt;
    private int isFinish;
    private List<DailyQuestDto> dailyQuests;
    private String avatarCode;
    private String imgPath;



}

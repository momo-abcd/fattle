package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.response.FollowResponse;
import com.sixman.fattle.dto.response.GoalUpdateResponse;
import com.sixman.fattle.dto.response.MyPageResponse;
import com.sixman.fattle.dto.response.MyPageUpdateResponse;
import com.sixman.fattle.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MyPageService {
    ResponseEntity<MyPageResponse> getMyPageInfo(Long userCode);
    ResponseEntity<MyPageUpdateResponse> updateMyPageInfo(Long userCode, MyPageUpdateResponse myPageInfo);
    ResponseEntity<GoalUpdateResponse> updateGoalInfo(Long userCode, GoalUpdateResponse myPageGoalInfo);
    ResponseEntity<List<FollowResponse>> getFollowingList(Long userCode);
    ResponseEntity<List<FollowResponse>> getFollowerList(Long userCode);
}
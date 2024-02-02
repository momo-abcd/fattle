package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.response.FollowResponse;
import com.sixman.fattle.dto.response.MyPageGoalUpdateResponse;
import com.sixman.fattle.dto.response.MyPageResponse;
import com.sixman.fattle.dto.response.MyPageUpdateResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MyPageService {
    ResponseEntity<MyPageResponse> getMyPageInfo(Long userCode);
    ResponseEntity<MyPageUpdateResponse> updateMyPageInfo(MyPageUpdateResponse myPageInfo);
    ResponseEntity<MyPageGoalUpdateResponse> updateGoalInfo(Long userCode, MyPageGoalUpdateResponse myPageGoalInfo);
    ResponseEntity<List<FollowResponse>> getFollowingList(Long userCode);
    ResponseEntity<List<FollowResponse>> getFollowerList(Long userCode);
}
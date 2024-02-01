package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.MyPageService;
import com.sixman.fattle.dto.response.FollowResponse;
import com.sixman.fattle.dto.response.GoalUpdateResponse;
import com.sixman.fattle.dto.response.MyPageUpdateResponse;
import com.sixman.fattle.entity.Follow;
import com.sixman.fattle.entity.User;
import com.sixman.fattle.repository.FollowRepository;
import com.sixman.fattle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/mypage")
@CrossOrigin("*")
@RequiredArgsConstructor
public class MyPageController {

    @Autowired
    private final MyPageService myPageService;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @GetMapping("/{userCode}")
    public ResponseEntity<?> getMyPageInfo(@PathVariable Long userCode) {
        return myPageService.getMyPageInfo(userCode);
    }

    @PatchMapping("/modify")
    public ResponseEntity<MyPageUpdateResponse> updateMyPageInfo(@RequestBody MyPageUpdateResponse myPageInfo) {
        return myPageService.updateMyPageInfo(myPageInfo);
    }

    @GetMapping("/following/{userCode}")
//    public ResponseEntity<List<User>> getFollowingList(@PathVariable Long userCode) {
    public ResponseEntity<List<FollowResponse>> getFollowingList(@PathVariable Long userCode) {
        return myPageService.getFollowingList(userCode);
    }


    @GetMapping("/follower/{userCode}")
    public ResponseEntity<List<FollowResponse>> getFollowerList(@PathVariable Long userCode) {
        return myPageService.getFollowerList(userCode);
    }





    @PutMapping("/modifyGoal/{userCode}")
    public ResponseEntity<GoalUpdateResponse> updateGoalInfo(@PathVariable Long userCode,
                                                             @RequestBody GoalUpdateResponse myPageGoalInfo) {
        return myPageService.updateGoalInfo(userCode, myPageGoalInfo);
    }


}
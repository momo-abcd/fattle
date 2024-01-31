package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.DailyQuestDto;
import com.sixman.fattle.dto.response.FollowResponse;
import com.sixman.fattle.dto.response.GoalUpdateResponse;
import com.sixman.fattle.dto.response.MyPageResponse;
import com.sixman.fattle.dto.response.MyPageUpdateResponse;
import com.sixman.fattle.entity.DailyQuest;
import com.sixman.fattle.entity.Follow;
import com.sixman.fattle.entity.User;
import com.sixman.fattle.repository.DailyQuestRepository;
import com.sixman.fattle.repository.FollowRepository;
import com.sixman.fattle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final FollowRepository followRepository;

    @Autowired
    private final DailyQuestRepository dailyQuestRepository;


    @Override
    public ResponseEntity<MyPageResponse> getMyPageInfo(Long userCode) {

        User user = userRepository.getUser(Long.parseLong(String.valueOf(userCode)));

        MyPageResponse myPageResponse = MyPageResponse.builder()
                .userCode(user.getUserCd())
                .nickname(user.getNickname())
                .introduction(user.getIntroduction())
                .goalWeight(user.getGoalWeight())
                .goalCalory(user.getGoalCalory())
                .goalCarbo(user.getGoalCarbo())
                .goalProtein(user.getGoalProtein())
                .goalFat(user.getGoalFat())
                .followerCnt(getFollowerCount(user))
                .followingCnt(getFollowingCount(user))
                .build();

        List<DailyQuest> monthlyQuests = getMonthlyQuests(user);
        List<DailyQuestDto> monthlyQuestDTOs = monthlyQuests.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        myPageResponse.setDailyQuests(monthlyQuestDTOs);

        return ResponseEntity.ok(myPageResponse);
    }

    private List<DailyQuest> getMonthlyQuests(User user) {

        YearMonth currentYearMonth = YearMonth.now();
//        Date startOfMonth = java.sql.Date.valueOf(currentYearMonth.atDay(1));
        LocalDate startOfMonth = currentYearMonth.atDay(1);
        LocalDate endOfMonth = currentYearMonth.atEndOfMonth();

        return dailyQuestRepository.findByUserAndRecordDateBetween(user, startOfMonth, endOfMonth);
    }

    @Override
    public ResponseEntity<MyPageUpdateResponse> updateMyPageInfo(Long userCode, MyPageUpdateResponse myPageInfo) {
//        userRepository.findById(Long.parseLong(String.valueOf(userCode)))
//                .ifPresent(user -> {
//                    user.setNickname(myPageInfo.getNickname());
//                    user.setIntroduction(myPageInfo.getIntroduction());
//                    userRepository.save(user);
//                });
//
//        return ResponseEntity.ok(myPageInfo);
        return ResponseEntity.internalServerError().build();
    }



    @Override
    public ResponseEntity<GoalUpdateResponse> updateGoalInfo(Long userCode, GoalUpdateResponse myPageGoalInfo) {
//        userRepository.findById(Long.parseLong(String.valueOf(userCode)))
//                .ifPresent(user -> {
//                    user.setGoalWeight(myPageGoalInfo.getGoalWeight());
//                    user.setGoalCalory(myPageGoalInfo.getGoalCalory());
//                    user.setGoalCarbo(myPageGoalInfo.getGoalCarbo());
//                    user.setGoalProtein(myPageGoalInfo.getGoalProtein());
//                    user.setGoalFat(myPageGoalInfo.getGoalFat());
//                    userRepository.save(user);
//                });
//
//        return ResponseEntity.ok(myPageGoalInfo);
        return ResponseEntity.internalServerError().build();
    }

    @Override
    public ResponseEntity<List<FollowResponse>> getFollowingList(Long userCode) {
        User user = userRepository.getUser(userCode);
        List<Follow> following = followRepository.findByFromUser(user);
        List<User> followingList = following.stream()
                .map(Follow::getToUser)
                .toList();
        List<FollowResponse> response = new ArrayList<>();

        for (User u : followingList) {
            response.add(FollowResponse.builder()
                            .nickname(u.getNickname())
                            .avatarCode(u.getAvatarCd())
                    .build());
        }
        return ResponseEntity.ok(response);

//        userRepository.findById(Long.parseLong(String.valueOf(userCode)))
//                .ifPresent(user -> {
//                    myPageResponse.setUserCode(user.getUserCode());
//                    myPageResponse.setNickname(user.getNickname());
//                    myPageResponse.setIntroduction(user.getIntroduction());
//                    myPageResponse.setGoalWeight(user.getGoalWeight());
//                    myPageResponse.setGoalCalory(user.getGoalCalory());
//                    myPageResponse.setGoalCarbo(user.getGoalCarbo());
//                    myPageResponse.setGoalProtein(user.getGoalProtein());
//                    myPageResponse.setGoalFat(user.getGoalFat());
//                    myPageResponse.setFollowerCnt(getFollowerCount(user));
//                    myPageResponse.setFollowingCnt(getFollowingCount(user));
////                    List<DailyQuest> dailyQuests = dailyQuestRepository.findByUser(user);
//                    List<DailyQuest> monthlyQuests = getMonthlyQuests(user);
//                    List<DailyQuestDto> monthlyQuestDTOs = monthlyQuests.stream()
//                            .map(this::convertToDTO)
//                            .collect(Collectors.toList());
//                    myPageResponse.setDailyQuests(monthlyQuestDTOs);
//                });
//
//        return ResponseEntity.ok(myPageResponse);
    }

    @Override
    public ResponseEntity<List<User>> getFollowerList(Long userCode) {
        User user = userRepository.getUser(userCode);
        List<Follow> followers = followRepository.findByToUser(user);
        List<User> followerList = followers.stream()
                .map(Follow::getFromUser)
                .toList();
        return ResponseEntity.ok(followerList);
    }



    private int getFollowerCount(User user) {
        return followRepository.countByToUser(user);
    }

    private int getFollowingCount(User user) {
        return followRepository.countByFromUser(user);
    }


    private DailyQuestDto convertToDTO(DailyQuest dailyQuest) {
        DailyQuestDto dto = new DailyQuestDto();
        dto.setRecordDate(dailyQuest.getRecordDate());
        dto.setDayCheck(dailyQuest.isDayCheck());
        dto.setExerciseCount(dailyQuest.getExerciseCount());
        dto.setFoodCount(dailyQuest.getFoodCount());
        dto.setFinish(dailyQuest.isFinish());
        return dto;
    }


}

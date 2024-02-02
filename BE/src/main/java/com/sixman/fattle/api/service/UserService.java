package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.request.SignUpRequest;
import com.sixman.fattle.dto.response.UserInfoResponse;
import com.sixman.fattle.entity.DailyQuest;
import com.sixman.fattle.entity.User;
import com.sixman.fattle.repository.DailyQuestRepository;
import com.sixman.fattle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DailyQuestRepository dailyQuestRepository;

    public HttpStatus login(long userCode) {
        User user =  userRepository.getUser(userCode);
        if (user == null) {
            return HttpStatus.NO_CONTENT;
        }

        List<DailyQuest> dailyQuests = dailyQuestRepository.findByUser(user);
        LocalDateTime now = LocalDateTime.now();
        if (!dailyQuests.isEmpty()) {
            DailyQuest lastDailyQuest = dailyQuests.get(dailyQuests.size() - 1);
            if (lastDailyQuest.getRecordDate().toLocalDateTime().isBefore(now.toLocalDate().atStartOfDay())) {
                DailyQuest dailyQuest = new DailyQuest();
                dailyQuest.setRecordDate(Timestamp.valueOf(now));
                dailyQuest.setUser(user);
                dailyQuest.setDayCheck(true);
                dailyQuest.setExerciseCount(0);
                dailyQuest.setFoodCount(0);
                dailyQuest.setFinish(false);
                dailyQuestRepository.save(dailyQuest);
            }
        } else {
        DailyQuest dailyQuest = new DailyQuest();
        dailyQuest.setRecordDate(Timestamp.valueOf(now));
        dailyQuest.setUser(user);
        dailyQuest.setDayCheck(true);
        dailyQuest.setExerciseCount(0);
        dailyQuest.setFoodCount(0);
        dailyQuest.setFinish(false);
        dailyQuestRepository.save(dailyQuest);
        }
        return HttpStatus.OK;

    }

    public void signUp(SignUpRequest request) {
        userRepository.joinUser(request);
    }

    public HttpStatus checkNickname(String nickname) {
        int cnt = userRepository.checkNickname(nickname);

        if (cnt == 0) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_ACCEPTABLE;
        }
    }

    public UserInfoResponse userInfo(long userCode) {
        return userRepository.getUserInfo(userCode);
    }

    public DailyQuest getDailyQuest(long userCode) {
        User user = userRepository.getUser(userCode);
        List<DailyQuest> dailyQuests = dailyQuestRepository.findByUser(user);
        if (!dailyQuests.isEmpty()) {
            DailyQuest lastDailyQuest = dailyQuests.get(dailyQuests.size() - 1);
            return lastDailyQuest;
        } else {
            return null;
        }
    }
}

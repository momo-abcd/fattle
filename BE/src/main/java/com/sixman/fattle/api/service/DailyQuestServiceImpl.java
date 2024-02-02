package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.response.DailyQuestResponse;
import com.sixman.fattle.entity.DailyQuest;
import com.sixman.fattle.entity.User;
import com.sixman.fattle.repository.DailyQuestRepository;
import com.sixman.fattle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyQuestServiceImpl implements DailyQuestService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final DailyQuestRepository dailyQuestRepository;

    public ResponseEntity<DailyQuestResponse> getDailyQuests(Long userCode) {
        DailyQuest lastDailyQuest = getDailyQuest(userCode);
        DailyQuestResponse dailyQuest = DailyQuestResponse.builder()
                .userCd(userCode)
                .recordDate(lastDailyQuest.getRecordDate())
                .dayCheck(lastDailyQuest.isDayCheck())
                .exerciseCount(lastDailyQuest.getExerciseCount())
                .foodCount(lastDailyQuest.getFoodCount())
                .Finish(lastDailyQuest.isFinish())
                .build();
        return ResponseEntity.ok(dailyQuest);
    }
    public DailyQuest getDailyQuest(long userCode) {
        User user = userRepository.getUser(userCode);
        List<DailyQuest> dailyQuests = dailyQuestRepository.findByUser(user);
        if (!dailyQuests.isEmpty()) {
            return dailyQuests.get(dailyQuests.size() - 1);
        } else {
            return null;
        }
    }
}

package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.request.QuestRequest;
import com.sixman.fattle.dto.response.DailyQuestResponse;
import com.sixman.fattle.entity.Quest;
import org.springframework.http.ResponseEntity;

public interface QuestService {

     ResponseEntity<DailyQuestResponse> getDailyQuests(long userCode);

     ResponseEntity<?> questRecord(QuestRequest request);

     void questRecord(long userCode);

     Quest getDailyQuest(long userCode);

     void createQuest(long userCode);

}

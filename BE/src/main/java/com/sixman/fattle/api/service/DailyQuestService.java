package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.dto.DailyQuestDto;
import com.sixman.fattle.dto.response.DailyQuestResponse;
import org.springframework.http.ResponseEntity;


public interface DailyQuestService {
     ResponseEntity<DailyQuestResponse> getDailyQuests(Long userCode);


}

package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.DailyQuestDto;
import org.springframework.http.ResponseEntity;


public interface DailyQuestService {
     ResponseEntity<DailyQuestDto> getDailyQuests(Long userCode);


}

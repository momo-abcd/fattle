package com.sixman.fattle.repository;

import com.sixman.fattle.entity.Quest;

public interface QuestRepositoryCustom {

    Quest getDailyQuest(long userCode);

    void createQuest(long userCode);

    int getCount(long userCode);

    void setFinish(long userCode);

}

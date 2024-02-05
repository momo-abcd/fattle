package com.sixman.fattle.repository;

import com.sixman.fattle.entity.DailyQuest;
import com.sixman.fattle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DailyQuestRepository extends JpaRepository<DailyQuest, Date> {
    List<DailyQuest> findByUser(User user);

}

package com.sixman.fattle.repository;

import com.sixman.fattle.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    List<Exercise> findExerciseByUser_UserCdAndRecDate(Long userCode, LocalDate recDate);

}

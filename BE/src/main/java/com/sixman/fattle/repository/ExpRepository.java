package com.sixman.fattle.repository;

import com.sixman.fattle.entity.ExpHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpRepository extends JpaRepository<ExpHistory, Long>, ExpRepositoryCustom {
}

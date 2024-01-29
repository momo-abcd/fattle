package com.sixman.fattle.repository;

import com.sixman.fattle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUserCode(long userCode);
}

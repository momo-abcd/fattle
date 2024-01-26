package com.sixman.fattle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_tb")
public class User {

    @Id
    @Column(name = "user_cd")
    private long userCode;

    @Column(name = "avatar_cd")
    private int avatarCode;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "sex")
    private String sex;

    @Column(name = "join_dt")
    @CreationTimestamp
    private Timestamp joinDate;

    @Column(name = "goal_weight")
    private float goalWeight;

    @Column(name = "goal_calory")
    private int goalCalory;

    @Column(name = "goal_carbo")
    private int goalCarbo;

    @Column(name = "goal_protein")
    private int goalProtein;

    @Column(name = "goal_fat")
    private int goalFat;

    @Column(name = "growth_exp")
    private int growthExp;

    @Column(name = "stack_exp")
    private int stackExp;

    @Column(name = "introduction")
    private String introduction;

}

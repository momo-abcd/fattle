package com.sixman.fattle.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "quest_tb")
public class DailyQuest {

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_cd", referencedColumnName = "user_cd")
    private User user;

    @Id
    @Column(name = "rec_dt", unique = true)
    private Date recordDate;

    @Column(name = "day_chk")
    private boolean dayCheck;

    @Column(name = "exercise_cnt")
    private int exerciseCount;

    @Column(name = "food_cnt")
    private int foodCount;

    @Column(name = "is_finish")
    private boolean isFinish;


    @Transient
    public Long getUserCode() {
        return user.getUserCd();
    }

}

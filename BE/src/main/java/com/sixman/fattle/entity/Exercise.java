package com.sixman.fattle.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "exercise_tb")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_cd")
    private int exerciseCode;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_cd", referencedColumnName = "user_cd")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "type_cd", referencedColumnName = "type_cd")
    private ExerciseType exerciseType;

    @Column(name = "rec_dt")
    private LocalDateTime recDate;

}
package com.sixman.fattle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "exercise_type_tb")
public class ExerciseType {

    @Id
    @Column(name = "type_cd")
    private String typeCode;

}

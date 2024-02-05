package com.sixman.fattle.dto.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExerciseCreateDto {

    private int exerciseCode;
    private Long userCode;
    private String typeCode;
    private LocalDateTime recDate;

}

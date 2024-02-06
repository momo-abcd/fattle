package com.sixman.fattle.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ExerciseDto {

    private int RUN;
    private int PUS;
    private int SQU;
    private int BUR;
    private int PUL;
    private int PLA;

}

package com.sixman.fattle.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ExerciseDto {

    int running;
    int pushup;
    int squat;
    int burpee;
    int pullup;
    int plank;

}

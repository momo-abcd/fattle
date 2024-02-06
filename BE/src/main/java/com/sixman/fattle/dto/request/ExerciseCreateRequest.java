package com.sixman.fattle.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExerciseCreateRequest {

    private int exerciseCode;
    private Long userCode;
    private String typeCode;
    private LocalDateTime recDate;

}

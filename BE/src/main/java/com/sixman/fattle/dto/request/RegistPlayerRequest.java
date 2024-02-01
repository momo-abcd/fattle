package com.sixman.fattle.dto.request;

import lombok.Getter;

@Getter
public class RegistPlayerRequest {

    private String battleCode;
    private long userCode;
    private float beforeWeight;
    private float goalWeight;

}

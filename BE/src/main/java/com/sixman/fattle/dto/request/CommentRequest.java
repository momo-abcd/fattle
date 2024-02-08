package com.sixman.fattle.dto.request;

import lombok.Getter;

@Getter
public class CommentRequest {

    private int boardCode;

    private long userCode;

    private String comment;

}

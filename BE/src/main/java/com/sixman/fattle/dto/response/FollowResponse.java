package com.sixman.fattle.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowResponse {

    private String nickname;
    private String avatarCode;

}

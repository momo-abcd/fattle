package com.sixman.fattle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileDto {

    private boolean setPrivacyInfo;
    private String id;
    private String connected_at;

}

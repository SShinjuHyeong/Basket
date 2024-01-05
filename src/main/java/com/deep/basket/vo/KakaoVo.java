package com.deep.basket.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KakaoVo {
    private long id;
    private String email;
    private String nickname;
}

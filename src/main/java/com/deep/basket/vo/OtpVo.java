package com.deep.basket.vo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("OtpVo")
@Data
public class OtpVo {
    private String username;
    private String code;
}

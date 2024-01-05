package com.deep.basket.vo;

import lombok.Data;

@Data
public class ResultVo {
    private String code;
    private String message;
    private String datetime;
    private String size;
    private Object data;
}

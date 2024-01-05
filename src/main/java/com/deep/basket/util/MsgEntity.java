package com.deep.basket.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
//JSON 직렬화 과정에서 객체를 JSON으로 변환할 때, 특정 조건 충족시 포함할 대상 속성을 설정하는 데 사용
//해당 속성에 대해 null 값이 아닌 경우에만 JSON으로 직렬화하도록 지시합니다.
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgEntity {
    private String msg;
    private Object result;

    public MsgEntity(String msg, Object result){
        this.msg = msg;
        this.result = result;
    }
}

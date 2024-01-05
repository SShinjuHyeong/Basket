package com.deep.basket.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//전역적으로 예외를 처리하고 응답을 제어하는 데 사용
//RESTful 웹 서비스에서 예외를 처리하고 JSON 또는 XML과 같은 데이터 형식으로 응답을 생성하는 경우에는
//@RestControllerAdvice를 사용한다. 예외 처리 로직 중앙 집중화할 수 있다.
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<MsgEntity> globalException(Exception e){
        return ResponseEntity.badRequest()
                .body(new MsgEntity(e.getMessage(),""));
    }
}

//ResponseEntity는 HTTP 응답을 나타내는 Spring의 클래스 , badRequest() 를 사용해 http 상태코드 400 을 응답설정


package com.deep.basket.controller.rest;

import com.deep.basket.vo.ResultVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@RestController
public class BaseRestController {
    protected ResponseEntity<?> success(Object data) {
        ResultVo restResult = new ResultVo();
        restResult.setCode("200");
        restResult.setMessage("");
        restResult.setData(data);
        restResult.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return new ResponseEntity<>(restResult, HttpStatus.OK);
    }
}

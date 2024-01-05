package com.deep.basket.controller.rest;

import com.deep.basket.service.KaKaoService;
import com.deep.basket.util.MsgEntity;
import com.deep.basket.vo.KakaoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class KaKaoController {
    private final KaKaoService kaKaoService;

    @PostMapping("/kakao/callback")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
        KakaoVo kakaoInfo = kaKaoService.getkaKaoInfo(request.getParameter("code"));
        return ResponseEntity.ok()
                .body(new MsgEntity("Success",kakaoInfo));
    }

}
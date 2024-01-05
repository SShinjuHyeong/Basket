package com.deep.basket.controller.rest;

import com.deep.basket.service.AccountService;
import com.deep.basket.service.JwtService;
import com.deep.basket.service.JwtServiceImpl;
import com.deep.basket.vo.UserVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class AccountRestController {
    private final Logger logger = (Logger) LogManager.getLogger(AccountRestController.class);

    @Autowired
    AccountService accountService;

    //로그인 체크 , 유저체크와 다른게 무엇인가?
    @PostMapping("/account/check")
    public UserVo checkUser(){
        logger.info(">>> >>> 로그인 확인...");
        UserVo vo = new UserVo("unknown","","",0,"");
        return vo;
    }


    //로그인
    @PostMapping("/account/login")
    public ResponseEntity TokenLogin(@RequestBody UserVo userData,
                                HttpServletResponse res){
        logger.info(">>> >>> 로그인 api 로 들어감 ... ");
        UserVo vo = accountService.findByEmailAndPassword(userData);
        logger.info(">>> >>> 유저 검색 완료 ... ");
        if(vo != null){
            JwtService jwtService = new JwtServiceImpl();
            String id = vo.getEmail();
            String token = jwtService.getToken("id",id);    //아이디를 토큰에 넣고

            Cookie cookie = new Cookie("token", token); //토큰에 넣고 토큰을 쿠키로 넣어 응답값
            cookie.setHttpOnly(true);   //js에서 접근 x
            cookie.setPath("/");        //루트로

            res.addCookie(cookie);
            logger.info(">>> >>> 쿠키 발급 > "+cookie);
            return new ResponseEntity<>(id, HttpStatus.OK); //문제없다 200코드
        }//리턴할 때 토큰 이메일 이름 또토큰..?

       throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    //유저 프로필 조회
    @GetMapping("/profile/info")
    public UserVo searchProfile(@AuthenticationPrincipal String username, HttpServletRequest req){
        logger.info(">>> >>> 유저 프로필 조회로 들어감 ... ");
        Cookie[] cookies = req.getCookies();

        if(cookies==null) {
            logger.info("쿠키실종..");
        }
        logger.info(">>> test >>> "+username+ ">>>"+cookies);
        UserVo vo = accountService.searchProfile(username,cookies);
        return vo;
    }

    //    return ResponseEntity.ok(responseService.getSingleResult(result));

}

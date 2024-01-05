package com.deep.basket.controller.view;

import com.deep.basket.controller.rest.AccountRestController;
import com.deep.basket.dao.UserDao;
import com.deep.basket.service.AccountService;
import com.deep.basket.vo.OtpVo;
import com.deep.basket.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Controller
public class HomeController {
    @Autowired
    private AccountService accountService;

    private final Logger logger = (Logger) LogManager.getLogger(HomeController.class);

    @PostMapping("/user/add")   //새 사용자 데이터베이스 추가 , 회원가입에 써먹기
    public void addUser(@RequestBody UserVo userVo) {
        logger.info(">>> addUser 들어감");
        int n = accountService.addUser(userVo);
        logger.info(">>> >>>"+n);
    }

    @PostMapping("/user/auth")  //추가 사용자 엔드포인트 호출 , otp 저장
    public void auth(@RequestBody UserVo userVo) {
        logger.info(">>> User auth 들어감");
        accountService.auth(userVo);
    }

    @PostMapping("/otp/check")  //otp check로 엔드포인트가 원하는 대로 작동되는지 확인, 이건 웹에서 해야할텐데
    public void check(@RequestBody OtpVo otpVo, HttpServletResponse response) {
        logger.info(">>> check 들어감");
        if(accountService.check(otpVo)) {   //otp가 유효하다면 http응답상태 200, 그렇지 않을경우 403 반환
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}

package com.deep.basket.service;

import com.deep.basket.controller.view.HomeController;
import com.deep.basket.dao.UserDao;
import com.deep.basket.util.GenerateCodeUtil;
import com.deep.basket.vo.OtpVo;
import com.deep.basket.vo.UserVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    private final Logger logger = (Logger) LogManager.getLogger(AccountService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserVo findByEmailAndPassword(UserVo userData) {

        return userDao.findByEmailAndPassword(userData);
    }

    public UserVo findByUsername(String username) {

        return userDao.findByUsername(username);
    }

    public Optional<OtpVo> findOtpByUsername(String username) {

        return userDao.findOtpByUsername(username);
    }

    //새 사용자 데이터베이스 추가
    public int addUser(UserVo userVo){
        userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));   //암호화
        int num =userDao.saveUserPassword(userVo);   //save 유저 패스워드 저장
        return num;
    }

    //사용자 인증 OTP 생성 후 SMS로 보내기
    public void auth(UserVo userVo){
        UserVo findUser =
                userDao.findByUsername(userVo.getUsername());   //user찾기

        if(findUser!=null) {
//            UserVo u = new UserVo();    //새객체 생성
//            u.setUserVo(findUser);
            if(passwordEncoder.matches(userVo.getPassword(),findUser.getPassword())) {
                int n = renewOtp(findUser);
                logger.info(">>> >>> renewOpt "+n);
            } else {
                throw new BadCredentialsException( "bad credentials... 암호 불일치.");
            }
        }else{
            throw new BadCredentialsException( "bad credentials... 사용자 검색 실패.");
        }
    }

    private int renewOtp(UserVo u) {
        int n = 0;
        String code = GenerateCodeUtil.generatedCode(); //otp를 위한 임의의 수 생성
        Optional<OtpVo> userOtp= userDao.findOtpByUsername(u.getUsername());    //사용자 이름으로 otp검색
        if(userOtp.isPresent()) {
            OtpVo otp = userOtp.get();
            otp.setCode(code);
        }else{
            OtpVo otp = new OtpVo();
            otp.setUsername(u.getUsername());
            otp.setCode(code);
            n = userDao.saveOtpUser(otp);  // save라는 뭐를 저장하는게 있는듯?
        }
        return n;
    }

    public boolean check(OtpVo otpToValidate) {
        Optional<OtpVo> userOtp = //사용자 이름으로 OTP 검색
            userDao.findOtpByUsername(otpToValidate.getUsername());

        if(userOtp.isPresent()) {   //데이터베이스에 otp 가 있고 비즈니스 논리 서버에서 받은 otp와 일치하면 true
            OtpVo otp = userOtp.get();
            if(otpToValidate.getCode().equals(otp.getCode())) {
                return true;
            }
        }

        return false;
    }

    public UserVo searchProfile(String username,Cookie[] cookies) {
        UserVo vo = new UserVo();
        HashMap<String,String> map = new HashMap<>();
        if(cookies != null){
            for(Cookie cookie : cookies){
                String name = cookie.getName();
                String value = cookie.getValue();
                map.put(name,value);
            }
            map.put("username",username);
            vo = userDao.searchProfile(map); //test
            //vo = userDao.searchProfile(name,value); //맵으로 해줘야하나
        } else {
            logger.info("쿠키사고...");
        }
        return vo;
    }
}

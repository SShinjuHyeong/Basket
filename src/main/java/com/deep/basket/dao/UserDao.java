package com.deep.basket.dao;

import com.deep.basket.vo.OtpVo;
import com.deep.basket.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Mapper
@Repository
    public interface UserDao {
    //user
    List<UserVo> userVoList(UserVo userVo);

    //find
    UserVo findByEmailAndPassword(UserVo userVo);

    //find
    UserVo findByUsername(String username);

    //find
    Optional<OtpVo> findOtpByUsername(String username);

    //유저 정보 저장
    int saveUserPassword(UserVo userVo);

    //opt 정보 저장
    int saveOtpUser(OtpVo otpVo);

    UserVo searchProfile(HashMap map);
}

package com.deep.basket.vo;

import lombok.*;
import org.apache.ibatis.type.Alias;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias("UserVo")
public class UserVo {
    private String username;
    private String algorithm;   //enum으로 받아야함
    private String password;
    private Integer id;
    private String email;

    public void setUserVo(UserVo findUser) {    //객체 맞춰주기
       findUser.setUsername(this.getUsername());
       findUser.setAlgorithm(this.getAlgorithm());
       findUser.setPassword(this.getPassword());
       findUser.setId(this.getId());
       findUser.setEmail(this.getEmail());
    }
}
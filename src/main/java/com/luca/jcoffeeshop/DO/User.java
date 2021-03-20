package com.luca.jcoffeeshop.DO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 用户 Data Object
 */
@Data
@Builder
public class User {

    private Long id;

    private String userId;

    private String nickname;

    private String phoneNumber;

    // todo 添加唯一索引
    private String username;

    private String password;

    /**
     * 注册时间
     */
    private Date signUpTime;

    private Date updateTime;

    private Boolean isDel;
}

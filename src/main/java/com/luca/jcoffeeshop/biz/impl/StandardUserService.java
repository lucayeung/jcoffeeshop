package com.luca.jcoffeeshop.biz.impl;

import com.luca.jcoffeeshop.DO.User;
import com.luca.jcoffeeshop.biz.UserService;
import com.luca.jcoffeeshop.dao.UserDao;
import com.luca.jcoffeeshop.query.LoginQuery;
import com.luca.jcoffeeshop.dto.LoginUserDTO;
import com.luca.jcoffeeshop.query.SignUpQuery;
import com.luca.jcoffeeshop.error.BizException;
import com.luca.jcoffeeshop.util.IdUtils;
import com.luca.jcoffeeshop.util.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Slf4j
@Service("standardUserService")
public class StandardUserService implements UserService {

    @Autowired
    @Qualifier("jdbcUserDao")
    private UserDao userDao;

    @Value("${password.salt}")
    private String salt;

    @Override
    public void signUp(SignUpQuery signUpQuery) {
        User user = User
                .builder()
                .userId(IdUtils.shortUUID())
                .nickname(signUpQuery.getNickname())
                .password(convertMd5Password(signUpQuery.getPassword()))
                .phoneNumber(signUpQuery.getPhoneNumber())
                .username(signUpQuery.getUsername())
                .build();

        try {
            userDao.saveUser(user);
        }
        catch (DuplicateKeyException ex) {
            String errMsg = "该用户名已存在";
            log.debug(errMsg);
            throw new BizException(errMsg);
        }
    }

    @Override
    public LoginUserDTO signIn(LoginQuery loginQuery) {
        String md5Password = convertMd5Password(loginQuery.getPassword());
        User user;
        try {
            user = userDao.getUserByUsernameAndPassword(loginQuery.getUsername(), md5Password);
        }
        catch (EmptyResultDataAccessException ex) {
            String errMsg = "用户名密码错误";
            log.debug(errMsg);
            throw new BizException(errMsg);
        }

        return LoginUserDTO
                .builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .signUpTime(user.getSignUpTime())
                .build();
    }

    private String convertMd5Password(String rawPassword) {
        String md5Password;
        try {
            md5Password = PasswordUtils.md5(salt, rawPassword);
        }
        catch (NoSuchAlgorithmException ex) {
            String errMsg = "MD5加密失败";
            log.info(errMsg, ex);
            throw new BizException(errMsg);
        }
        return md5Password;
    }

}

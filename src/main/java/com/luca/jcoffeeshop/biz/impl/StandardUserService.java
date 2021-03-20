package com.luca.jcoffeeshop.biz.impl;

import com.luca.jcoffeeshop.DO.User;
import com.luca.jcoffeeshop.biz.UserService;
import com.luca.jcoffeeshop.dao.UserDao;
import com.luca.jcoffeeshop.dto.LoginQuery;
import com.luca.jcoffeeshop.dto.LoginUserDTO;
import com.luca.jcoffeeshop.dto.SignUpDTO;
import com.luca.jcoffeeshop.error.BizException;
import com.luca.jcoffeeshop.util.IdUtils;
import com.luca.jcoffeeshop.util.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Slf4j
@Service("standard")
public class StandardUserService implements UserService {

    @Autowired
    @Qualifier("jdbc")
    private UserDao userDao;

    @Value("${password.salt}")
    private String salt;

    @Override
    public void signUp(SignUpDTO signUpDTO) {
        User user = User
                .builder()
                .userId(IdUtils.shortUUID())
                .nickname(signUpDTO.getNickname())
                .password(convertMd5Password(signUpDTO.getPassword()))
                .phoneNumber(signUpDTO.getPhoneNumber())
                .username(signUpDTO.getUsername())
                .build();
        userDao.saveUser(user);
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
            throw new BizException(errMsg, ex);
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

package com.luca.jcoffeeshop.api;

import com.luca.jcoffeeshop.biz.UserService;
import com.luca.jcoffeeshop.dto.LoginQuery;
import com.luca.jcoffeeshop.dto.LoginUserDTO;
import com.luca.jcoffeeshop.dto.ResponseResult;
import com.luca.jcoffeeshop.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    @Qualifier("standard")
    private UserService userService;

    @PostMapping("sign-up")
    public ResponseResult signUp(@RequestBody @Validated SignUpDTO signUpDTO) {
        userService.signUp(signUpDTO);
        return new ResponseResult("注册成功", 200);
    }

    @PostMapping("sign-in")
    public ResponseResult signIn(@RequestBody @Validated LoginQuery loginQuery, HttpServletRequest request) {
        LoginUserDTO loginUserDTO = userService.signIn(loginQuery);
        HttpSession session = request.getSession();
        session.setAttribute("user", loginUserDTO);
        return new ResponseResult("登录成功", 200, loginUserDTO);
    }
}

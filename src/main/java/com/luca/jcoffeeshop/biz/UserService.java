package com.luca.jcoffeeshop.biz;

import com.luca.jcoffeeshop.dto.LoginQuery;
import com.luca.jcoffeeshop.dto.LoginUserDTO;
import com.luca.jcoffeeshop.dto.SignUpDTO;

public interface UserService {

    void signUp(SignUpDTO signUpDTO);

    LoginUserDTO signIn(LoginQuery loginQuery);
}

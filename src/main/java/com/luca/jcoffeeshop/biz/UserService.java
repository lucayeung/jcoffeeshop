package com.luca.jcoffeeshop.biz;

import com.luca.jcoffeeshop.query.LoginQuery;
import com.luca.jcoffeeshop.dto.LoginUserDTO;
import com.luca.jcoffeeshop.query.SignUpQuery;

public interface UserService {

    void signUp(SignUpQuery signUpQuery);

    LoginUserDTO signIn(LoginQuery loginQuery);
}

package com.luca.jcoffeeshop.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @PostMapping("sign-up")
    public String signUp() {
        return "";
    }

    @PostMapping("sign-in")
    public String signIn() {
        return "";
    }
}

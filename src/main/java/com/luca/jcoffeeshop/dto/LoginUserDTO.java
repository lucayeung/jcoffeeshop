package com.luca.jcoffeeshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO {

    private String userId;

    private String nickname;

    private String username;

    private String phoneNumber;

    private Date signUpTime;
}

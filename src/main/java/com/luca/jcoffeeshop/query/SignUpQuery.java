package com.luca.jcoffeeshop.query;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor // todo is required?
public class SignUpQuery {

    @NotBlank(message = "用户昵称不能为空")
    @Length(min = 4, max = 16, message = "昵称必须在4-16个字符之间")
    private String nickname;

    @NotBlank(message = "手机号码不能为空")
    private String phoneNumber;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

}

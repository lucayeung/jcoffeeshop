package com.luca.jcoffeeshop.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExpress {

    /**
     * 收件人名称
     */
    @NotBlank(message = "收件人名称不能为空")
    private String name;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    private String phoneNumber;

    /**
     * 收件地址
     */
    @NotBlank(message = "收件地址不能为空")
    private String address;

}

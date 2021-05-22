package com.luca.jcoffeeshop.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartQuery {
    /**
     * 商品唯一标识
     */
    @NotBlank(message = "请选择商品")
    private String productId;

    /**
     * 数目, 默认为1
     */
    @Min(value = 1, message = "商品不能少于一件")
    private Integer count;
}

package com.luca.jcoffeeshop.query;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class RemoveFromCardQuery {
    /**
     * 商品唯一标识
     */
    @NotBlank(message = "请选择商品")
    private String productId;

    /**
     * 数目, 默认为1、清空当前项为-1
     */
    @Min(value = 1, message = "商品不能少于一件")
    private Integer count;

    /**
     * 是否移除购物项
     * 默认关闭
     */
    private boolean evict;
}

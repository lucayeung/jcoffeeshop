package com.luca.jcoffeeshop.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartVO {

    /**
     * 购物车的商品
     */
    private List<CartItemVO> items;

    /**
     * 购物车内的商品数量
     */
    private Integer count;

    /**
     * 购物车共计
     */
    private BigDecimal total;
}

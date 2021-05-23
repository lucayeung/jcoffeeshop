package com.luca.jcoffeeshop.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartItemVO {

    private String productId;

    private String name;

    /**
     * 商品单价
     */
    private BigDecimal price;

    private List<String> imgUrls;

    private String description;

    private String categoryName;

    /**
     * 购物车项数量
     */
    private Integer count;

    /**
     * 购物车项总价
     */
    private BigDecimal totalPrice;
}

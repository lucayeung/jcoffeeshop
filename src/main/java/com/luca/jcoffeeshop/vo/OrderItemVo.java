package com.luca.jcoffeeshop.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemVo {

    private String orderItemId;

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private BigDecimal orderItemPrice;

    /**
     * 商品数目
     */
    private Integer count;

}

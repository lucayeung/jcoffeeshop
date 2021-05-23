package com.luca.jcoffeeshop.DO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单项 Order Item Data Object
 */
@Data
@Builder
public class OrderItem {

    private Long id;

    private String orderItemId;

    private String orderId;

    private String productId;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    /**
     * 商品数目
     */
    private Integer count;

    /**
     * 订单项总价
     */
    private BigDecimal orderItemPrice;

    private Date createTime;

    private Date updateTime;

    private Boolean isDel;

}

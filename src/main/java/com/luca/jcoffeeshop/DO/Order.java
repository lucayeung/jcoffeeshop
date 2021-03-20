package com.luca.jcoffeeshop.DO;

import com.luca.jcoffeeshop.enums.OrderStatus;

import java.util.Date;

/**
 * 订单 Data Object
 */
public class Order {

    private Long id;

    /**
     * 订单流水号
     */
    private String orderId;

    private String userId;

    /**
     * 明细
     */
    private String details;

    /**
     * 状态
     */
    private OrderStatus status;

    private Date createTime;

    private Date updateTime;

    private Boolean isDel;

}

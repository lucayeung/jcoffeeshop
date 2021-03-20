package com.luca.jcoffeeshop.enums;

public enum OrderStatus {

    /**
     * 未支付
     */
    UNPAID,

    /**
     * 已过期
     */
    EXPIRED,

    /**
     * 已取消
     */
    CANCEL,

    /**
     * 已支付
     */
    PAID,

    /**
     * 已退款
     */
    REFUNDED,

    /**
     * 配送中
     */
    SHIPPED,

    /**
     * 已送达
     */
    RECEIPT,
    ;
}

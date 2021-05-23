package com.luca.jcoffeeshop.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum OrderStatus {

    /**
     * 未支付
     */
    UNPAID(0),

    /**
     * 已关闭
     */
    CLOSED(1),

    /**
     * 已取消
     */
    CANCEL(2),

    /**
     * 已支付
     */
    PAID(3),

    /**
     * 已发货
     */
    SHIPPED(4),

    /**
     * 已完成
     */
    FINISHED(5),
    ;

    final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public static Optional<OrderStatus> valueOf(int value) {
        return Arrays.stream(values())
                .filter(status -> status.value == value)
                .findFirst();
    }

}

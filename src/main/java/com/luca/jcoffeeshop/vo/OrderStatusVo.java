package com.luca.jcoffeeshop.vo;

import com.luca.jcoffeeshop.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OrderStatusVo {

    /**
     * 订单流水号
     */
    private String orderId;

    /**
     * 状态
     */
    private OrderStatus status;

    private Date createTime;

}

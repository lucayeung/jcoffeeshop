package com.luca.jcoffeeshop.vo;

import com.luca.jcoffeeshop.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderVo {

    private String orderId;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 收货人手机号码
     */
    private String phoneNumber;

    /**
     * 收货人名称
     */
    private String name;

    /**
     * 订单总计
     */
    private BigDecimal total;

    private OrderStatus orderStatus;

    private Date createTime;

    private Date updateTime;

    private List<OrderItemVo> orderItems;

}

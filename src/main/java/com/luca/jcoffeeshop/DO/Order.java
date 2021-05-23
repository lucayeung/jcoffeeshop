package com.luca.jcoffeeshop.DO;

import com.luca.jcoffeeshop.enums.OrderStatus;
import com.luca.jcoffeeshop.error.BizException;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单 Data Object
 */
@Data
@Builder
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
     * 订单总计
     */
    private BigDecimal total;

    /**
     * 商品数量
     */
    private Integer count;

    /**
     * 状态
     */
    private OrderStatus status;

    private Date createTime;

    private Date updateTime;

    private Boolean isDel;

    /**
     * 订单状态已付款
     */
    public void pay() {
        if (status.equals(OrderStatus.UNPAID)) {
            setStatus(OrderStatus.PAID);
        }
        else if (status.equals(OrderStatus.PAID)) {
            throw new BizException("该订单已过付款 :)");
        }
        else if (isExpired()) {
            throw new BizException("该订单已经过期或失效 :(");
        }
        else {
            throw new BizException("订单状态设置错误");
        }
    }

    /**
     * 订单已完成
     */
    public void receipt() {
        if (status.equals(OrderStatus.SHIPPED)) {
            setStatus(OrderStatus.FINISHED);
        }
        else if (status.equals(OrderStatus.FINISHED)) {
            throw new BizException("该订单已完成 :)");
        }
        else if (isExpired()) {
            throw new BizException("该订单已经过期或失效 :(");
        }
        else {
            throw new BizException("订单状态设置错误");
        }
    }

    /**
     * 订单已发货
     */
    public void ship() {
        if (status.equals(OrderStatus.PAID)) {
            setStatus(OrderStatus.SHIPPED);
        }
        else if (status.equals(OrderStatus.SHIPPED)) {
            throw new BizException("该订单已过发货了 :)");
        }
        else if (isExpired()) {
            throw new BizException("该订单已经过期或失效 :(");
        }
        else {
            throw new BizException("订单状态设置错误");
        }
    }

    public Boolean isExpired() {
        return status.equals(OrderStatus.CLOSED) || status.equals(OrderStatus.CANCEL);
    }
}

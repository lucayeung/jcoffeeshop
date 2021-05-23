package com.luca.jcoffeeshop.biz;

import com.luca.jcoffeeshop.query.UserExpress;
import com.luca.jcoffeeshop.vo.OrderStatusVo;
import com.luca.jcoffeeshop.vo.OrderVo;

import java.util.List;

public interface OrderService {

    void createOrder(String userId, UserExpress userExpress);

    OrderStatusVo queryOrderStatus(String userId, String orderId);

    List<OrderVo> queryUserOrders(String userId);

    void pay(String userId, String orderId);

    void receipt(String userId, String orderId);

    void ship(String userId, String orderId);
}

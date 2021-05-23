package com.luca.jcoffeeshop.dao;

import com.luca.jcoffeeshop.DO.Order;
import com.luca.jcoffeeshop.DO.OrderItem;

import java.util.List;

public interface OrderDao {

    void addOrder(Order order);

    void batchAddOrderItems(List<OrderItem> orderItems);

    Order getOrderByUserIdAndOrderId(String userId, String orderId);

    List<Order> getOrdersByUserId(String userId);

    List<OrderItem> getOrderItemsByOrderIdAndUserId(List<String> orderIds, String userId);

    void updateOrder(Order order);

}

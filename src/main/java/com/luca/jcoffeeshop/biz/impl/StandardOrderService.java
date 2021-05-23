package com.luca.jcoffeeshop.biz.impl;

import com.luca.jcoffeeshop.DO.Order;
import com.luca.jcoffeeshop.DO.OrderItem;
import com.luca.jcoffeeshop.DO.Product;
import com.luca.jcoffeeshop.biz.CartService;
import com.luca.jcoffeeshop.biz.OrderService;
import com.luca.jcoffeeshop.dao.OrderDao;
import com.luca.jcoffeeshop.dao.ProductDao;
import com.luca.jcoffeeshop.enums.OrderStatus;
import com.luca.jcoffeeshop.error.BizException;
import com.luca.jcoffeeshop.query.UserExpress;
import com.luca.jcoffeeshop.util.IdUtils;
import com.luca.jcoffeeshop.util.JsonUtils;
import com.luca.jcoffeeshop.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service("standardOrderService")
public class StandardOrderService implements OrderService {

    @Autowired
    @Qualifier("jdbcOrderDao")
    private OrderDao orderDao;

    @Autowired
    @Qualifier("jdbcProductDao")
    private ProductDao productDao;

    @Autowired
    @Qualifier("standardCartService")
    private CartService cartService;

    @Override
    @Transactional
    public void createOrder(String userId, UserExpress userExpress) {
        Date now = new Date();
        CartVO cartVo = cartService.myCart(userId);
        List<CartItemVO> cartItems = cartVo.getItems();
        if (CollectionUtils.isEmpty(cartItems)) {
            throw new BizException("您的购物车空空如也 :(");
        }

        // 处理订单信息 - 收件人、收货地址等
        String details;
        try {
            details = JsonUtils.toJson(userExpress);
        }
        catch (IOException ex) {
            log.warn("JSON序列化出错", ex);
            throw new BizException("服务器出错！请联系管理员");
        }

        // 处理订单
        Order order = Order
                .builder()
                .orderId(IdUtils.shortUUID())
                .count(cartVo.getCount())
                .userId(userId)
                .total(cartVo.getTotal())
                .createTime(now)
                .updateTime(now)
                .details(details)
                .status(OrderStatus.UNPAID)
                .build();
        orderDao.addOrder(order);

        // 处理订单项
        List<OrderItem> orderItems = new ArrayList<>();
        Map<String, CartItemVO> cartItemVoQueryMap = new HashMap<>();
        for (CartItemVO cartItemVo : cartItems) {
            cartItemVoQueryMap.put(cartItemVo.getProductId(), cartItemVo);

            OrderItem orderItem = OrderItem
                    .builder()
                    .orderItemId(IdUtils.shortUUID())
                    .orderItemPrice(cartItemVo.getTotalPrice())
                    .productId(cartItemVo.getProductId())
                    .productPrice(cartItemVo.getPrice())
                    .count(cartItemVo.getCount())
                    .createTime(now)
                    .updateTime(now)
                    .build();
            orderItems.add(orderItem);
        }
        orderDao.batchAddOrderItems(orderItems);

        // 扣减商品库存
        List<String> productIds = cartItems
                .stream()
                .map(CartItemVO::getProductId)
                .distinct()
                .collect(Collectors.toList());
        List<Product> products = productDao.queryProductsByIds(productIds);
        for (Product product : products) {
            CartItemVO cartItemVO = cartItemVoQueryMap.get(product.getProductId());
            int remain = product.getStock() - cartItemVO.getCount();
            if (remain < 0) {
                throw new BizException(String.format("%s库存不足，剩：%s件", product.getName(), product.getStock()));
            }
            else {
                product.setStock(remain);
            }
        }
        productDao.batchUpdate(products);

        // 清空购物车
        cartService.clear(userId);
    }

    @Override
    public OrderStatusVo queryOrderStatus(String userId, String orderId) {
        Order order = orderDao.getOrderByUserIdAndOrderId(userId, orderId);
        if (order == null) {
            throw new BizException("订单不存在或已被删除");
        }

        return OrderStatusVo
                .builder()
                .orderId(order.getOrderId())
                .status(order.getStatus())
                .createTime(order.getCreateTime())
                .build();
    }

    @Override
    public List<OrderVo> queryUserOrders(String userId) {
        List<Order> orders = orderDao.getOrdersByUserId(userId);
        // 没有任何订单
        if (CollectionUtils.isEmpty(orders)) {
            return Collections.emptyList();
        }

        List<String> orderIds = new ArrayList<>();
        for (Order order : orders) {
            orderIds.add(order.getOrderId());
        }
        // 订单关联项
        List<OrderItem> orderItems = orderDao.getOrderItemsByOrderIdAndUserId(orderIds, userId);
        List<String> productIds = orderItems
                .stream()
                .map(OrderItem::getProductId)
                .distinct()
                .collect(Collectors.toList());
        Map<String, List<OrderItem>> orderItemTable = orderItems
                .stream()
                .collect(groupingBy(OrderItem::getOrderId));

        // 订单项关联商品
        List<Product> products = productDao.queryProductsByIds(productIds);
        Map<String, Product> productQueryTable = new HashMap<>();
        for (Product product : products) {
            productQueryTable.put(product.getProductId(), product);
        }

        // 组装订单数据
        List<OrderVo> orderVoList = new ArrayList<>();
        for (Order order : orders) {
            List<OrderItem> items = orderItemTable.getOrDefault(order.getOrderId(), Collections.emptyList());
            List<OrderItemVo> orderItemVoList = mapToOrderItemVo(items);
            batchSetProductNameOfOrderItemVo(orderItemVoList, productQueryTable);

            UserExpress userExpress;
            try {
                userExpress = JsonUtils.toClass(order.getDetails(), UserExpress.class);
            }
            catch (IOException ex) {
                log.warn("JSON序列化异常", ex);
                throw new BizException("服务器错误，请联系管理员:(");
            }
            OrderVo orderVo = OrderVo
                    .builder()
                    .orderStatus(order.getStatus())
                    .orderId(order.getOrderId())
                    .address(userExpress.getAddress())
                    .phoneNumber(userExpress.getPhoneNumber())
                    .name(userExpress.getName())
                    .createTime(order.getCreateTime())
                    .updateTime(order.getUpdateTime())
                    .total(order.getTotal())
                    .orderItems(orderItemVoList)
                    .build();
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    @Override
    public void pay(String userId, String orderId) {
        Order order = orderDao.getOrderByUserIdAndOrderId(userId, orderId);
        if (order == null) {
            throw new BizException("订单不存在或已被删除");
        }

        order.pay();
        order.setUpdateTime(new Date());
        orderDao.updateOrder(order);
    }

    @Override
    public void receipt(String userId, String orderId) {
        Order order = orderDao.getOrderByUserIdAndOrderId(userId, orderId);
        if (order == null) {
            throw new BizException("订单不存在或已被删除");
        }

        order.receipt();
        order.setUpdateTime(new Date());
        orderDao.updateOrder(order);
    }

    @Override
    public void ship(String userId, String orderId) {
        Order order = orderDao.getOrderByUserIdAndOrderId(userId, orderId);
        if (order == null) {
            throw new BizException("订单不存在或已被删除");
        }

        order.ship();
        order.setUpdateTime(new Date());
        orderDao.updateOrder(order);
    }

    private void batchSetProductNameOfOrderItemVo(List<OrderItemVo> OrderItemVoList,
            Map<String, Product> productQueryTable) {
        for (OrderItemVo orderItemVo : OrderItemVoList) {
            Product product = productQueryTable.get(orderItemVo.getProductId());
            orderItemVo.setProductName(product.getName());
        }
    }

    public List<OrderItemVo> mapToOrderItemVo(List<OrderItem> orderItems) {
        List<OrderItemVo> orderItemVoList = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemVo orderItemVo = OrderItemVo
                    .builder()
                    .orderItemId(orderItem.getOrderItemId())
                    .orderItemPrice(orderItem.getOrderItemPrice())
                    .productId(orderItem.getProductId())
                    .count(orderItem.getCount())
                    .build();
            orderItemVoList.add(orderItemVo);
        }
        return orderItemVoList;
    }
}

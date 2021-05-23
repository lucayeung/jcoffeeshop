package com.luca.jcoffeeshop.dao.impl;

import com.luca.jcoffeeshop.DO.Order;
import com.luca.jcoffeeshop.DO.OrderItem;
import com.luca.jcoffeeshop.dao.OrderDao;
import com.luca.jcoffeeshop.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository("jdbcOrderDao")
public class JdbcOrderDao implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void addOrder(Order order) {
        String sql = "insert into t_order(order_id, user_id, details, total, count, status) " +
                "values(:orderId, :userId, :details, :total, :count, :status)";
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", order.getOrderId());
        params.put("userId", order.getUserId());
        params.put("details", order.getDetails());
        params.put("total", order.getTotal());
        params.put("count", order.getCount());
        params.put("status", order.getStatus().getValue());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void batchAddOrderItems(List<OrderItem> orderItems) {
        String sql = "insert into t_order_item(order_item_id, order_id, product_id, " +
                "product_price, count, order_item_price) " +
                "values(:orderItemId, :orderId, :productId, " +
                ":productPrice, :count, :orderItemPrice)";
        List<SqlParameterSource> sources = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("orderItemId", orderItem.getOrderItemId());
            source.addValue("orderId", orderItem.getOrderId());
            source.addValue("productId", orderItem.getProductId());
            source.addValue("productPrice", orderItem.getProductPrice());
            source.addValue("count", orderItem.getCount());
            source.addValue("orderItemPrice", orderItem.getOrderItemPrice());
            sources.add(source);
        }
        namedParameterJdbcTemplate.batchUpdate(sql, sources.toArray(new SqlParameterSource[0]));
    }

    @Override
    public Order getOrderByUserIdAndOrderId(String userId, String orderId) {
        String sql = "select order_id, user_id, details, total, count, status, create_time, update_time from t_order " +
                "where user_id = :userId and order_id = :orderId and is_del = 0";

        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("orderId", orderId);

        Order order;
        try {
            order = namedParameterJdbcTemplate.queryForObject(sql, params, orderRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {
            return null;
        }
        return order;
    }

    private RowMapper<Order> orderRowMapper() {
        return ((rs, rowNum) -> {
            Optional<OrderStatus> status = OrderStatus.valueOf(rs.getInt("status"));
            OrderStatus orderStatus = status.orElse(null);
            return Order
                    .builder()
                    .orderId(rs.getString("order_id"))
                    .userId(rs.getString("user_id"))
                    .details(rs.getString("details"))
                    .total(rs.getBigDecimal("total"))
                    .count(rs.getInt("count"))
                    .status(orderStatus)
                    .createTime(rs.getDate("create_time"))
                    .updateTime(rs.getDate("update_time"))
                    .build();
        });
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        // 按订单状态、创建日期排序，支持自定义排序、筛选
        String sql = "select order_id, user_id, details, total, count, status, create_time, update_time from t_order " +
                "where user_id = :userId and is_del = 0 order by status desc, update_time desc";

        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);

        List<Order> orders;
        try {
            orders = namedParameterJdbcTemplate.query(sql, params, orderRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {
            return Collections.emptyList();
        }
        return orders;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(List<String> orderIds) {
        String inClause = orderIds
                .stream()
                .map(orderId -> String.format("'%s'", orderId))
                .collect(Collectors.joining(","));
        String sql = "select order_item_id, order_id, product_id, product_price, " +
                "count, order_item_price, create_time, update_time from t_order_item " +
                "where order_id in (" + inClause + ") and  is_del = 0";

        List<OrderItem> orderItems;
        try {
            orderItems = namedParameterJdbcTemplate.query(sql, orderItemRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {
            return Collections.emptyList();
        }
        return orderItems;
    }

    private RowMapper<OrderItem> orderItemRowMapper() {
        return ((rs, rowNum) -> OrderItem
                .builder()
                .orderItemId(rs.getString("order_item_id"))
                .orderId(rs.getString("order_id"))
                .productId(rs.getString("product_id"))
                .productPrice(rs.getBigDecimal("product_price"))
                .count(rs.getInt("count"))
                .orderItemPrice(rs.getBigDecimal("order_item_price"))
                .createTime(rs.getDate("create_time"))
                .updateTime(rs.getDate("update_time"))
                .build());
    }

    @Override
    public void updateOrder(Order order) {
        String sql = "update t_order set status = :status where order_id = :orderId and is_del = 0";

        Map<String, Object> params = new HashMap<>();
        params.put("status", order.getStatus().getValue());
        params.put("orderId", order.getOrderId());

        namedParameterJdbcTemplate.update(sql, params);
    }

}

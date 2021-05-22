package com.luca.jcoffeeshop.dao.impl;

import com.luca.jcoffeeshop.DO.CartItem;
import com.luca.jcoffeeshop.dao.CartItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("jdbcCartItemDao")
public class JdbcCartItemDao implements CartItemDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public CartItem getByProductIdAndUserId(String productId, String userId) {
        String sql = "select item_id, product_id, user_id, create_time, update_time, count from t_cart_item " +
                "where user_id = :userId and product_id = :productId and is_del = 0";

        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        params.put("userId", userId);

        CartItem cartItem;
        try {
            cartItem = namedParameterJdbcTemplate.queryForObject(sql, params, cartItemRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {
            // 购物车内没有这件商品
            return null;
        }
        return cartItem;
    }

    @Override
    public List<CartItem> getAllByUserId(String userId) {
        String sql = "select item_id, product_id, user_id, create_time, update_time, count from t_cart_item " +
                "where user_id = :userId and is_del = 0";

        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);

        return namedParameterJdbcTemplate.query(sql, params, cartItemRowMapper());
    }

    @Override
    public void add(CartItem cartItem) {
        String sql = "insert into t_cart_item(item_id, product_id, count, user_id) " +
                "values(:itemId, :productId, :count, :userId)";
        Map<String, Object> params = new HashMap<>();
        params.put("itemId", cartItem.getItemId());
        params.put("productId", cartItem.getProductId());
        params.put("count", cartItem.getCount());
        params.put("userId", cartItem.getUserId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void update(CartItem cartItem) {
        String sql = "update t_cart_item set count = :count where item_id = :itemId";
        Map<String, Object> params = new HashMap<>();
        params.put("count", cartItem.getCount());
        params.put("itemId", cartItem.getItemId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteAllByUserId(String userId) {
        String sql = "update t_cart_item set is_del = 1 where user_id = :userId and is_del = 0";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteByProductIdAndUserId(String productId, String userId) {
        String sql = "update t_cart_item set is_del = 1 where product_id = :productId and user_id = :userId";
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("userId", userId);
        namedParameterJdbcTemplate.update(sql, params);
    }

    private RowMapper<CartItem> cartItemRowMapper() {
        // JdbcSQLSyntaxErrorException: Column "user_id" not found
        // 报错原因：SQL的select中必须出现rs.get方法的columnLabel
        return ((rs, rowNum) -> CartItem
                .builder()
                .productId(rs.getString("product_id"))
                .userId(rs.getString("user_id"))
                .itemId(rs.getString("item_id"))
                .count(rs.getInt("count"))
                .createTime(rs.getDate("create_time"))
                .updateTime(rs.getDate("update_time"))
                .build());
    }
}

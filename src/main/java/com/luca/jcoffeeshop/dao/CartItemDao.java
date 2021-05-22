package com.luca.jcoffeeshop.dao;

import com.luca.jcoffeeshop.DO.CartItem;

import java.util.List;

public interface CartItemDao {

    CartItem getByProductIdAndUserId(String productId, String userId);

    List<CartItem> getAllByUserId(String userId);

    void add(CartItem cartItem);

    void update(CartItem cartItem);

    void deleteAllByUserId(String userId);

    void deleteByProductIdAndUserId(String productId, String userId);

}

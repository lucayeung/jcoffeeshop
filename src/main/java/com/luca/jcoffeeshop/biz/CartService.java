package com.luca.jcoffeeshop.biz;

import com.luca.jcoffeeshop.query.AddToCartQuery;
import com.luca.jcoffeeshop.query.RemoveFromCardQuery;
import com.luca.jcoffeeshop.vo.CartVO;

public interface CartService {
    /**
     * 我的购物车
     */
    CartVO myCart(String userId);

    /**
     * 添加商品
     */
    void addProduct(String userId, AddToCartQuery addToCartQuery);

    /**
     * 移除商品
     */
    void removeProduct(String userId, RemoveFromCardQuery removeFromCardQuery);

    /**
     * 清空购物车
     */
    void clear(String userId);
}

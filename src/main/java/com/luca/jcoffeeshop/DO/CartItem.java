package com.luca.jcoffeeshop.DO;

import java.util.Date;

/**
 * 购物车明细 Data Object
 */
public class CartItem {

    private Long id;

    /**
     * 购物车明细ID
     */
    private String itemId;

    private String productId;

    /**
     * 数目
     */
    private Integer count;

    private String userId;

    private Date createTime;

    private Date updateTime;

    private Boolean isDel;

}

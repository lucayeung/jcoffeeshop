package com.luca.jcoffeeshop.biz.impl;

import com.luca.jcoffeeshop.DO.CartItem;
import com.luca.jcoffeeshop.DO.Category;
import com.luca.jcoffeeshop.DO.Product;
import com.luca.jcoffeeshop.biz.CartService;
import com.luca.jcoffeeshop.dao.CartItemDao;
import com.luca.jcoffeeshop.dao.CategoryDao;
import com.luca.jcoffeeshop.dao.ProductDao;
import com.luca.jcoffeeshop.error.BizException;
import com.luca.jcoffeeshop.query.AddToCartQuery;
import com.luca.jcoffeeshop.query.RemoveFromCardQuery;
import com.luca.jcoffeeshop.util.IdUtils;
import com.luca.jcoffeeshop.vo.CartItemVO;
import com.luca.jcoffeeshop.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service("standardCartService")
public class StandardCartService implements CartService {

    @Autowired
    @Qualifier("jdbcCartItemDao")
    private CartItemDao cartItemDao;

    @Autowired
    @Qualifier("jdbcProductDao")
    private ProductDao productDao;

    @Autowired
    @Qualifier("jdbcCategoryDao")
    private CategoryDao categoryDao;

    /**
     * 我的购物车
     * 实现说明：分解关联查询
     */
    @Override
    public CartVO myCart(String userId) {
        List<CartItem> userCartItems = cartItemDao.getAllByUserId(userId);
        // 购物车空空如也...
        if (CollectionUtils.isEmpty(userCartItems)) {
            return CartVO
                    .builder()
                    .items(Collections.emptyList())
                    .total(BigDecimal.ZERO)
                    .count(0)
                    .build();
        }

        List<String> productIds = userCartItems
                .stream()
                .map(CartItem::getProductId)
                .distinct()
                .collect(Collectors.toList());
        // 购物车内的商品
        List<Product> products = productDao.queryProductsByIds(productIds);
        Map<String, Product> productQueryMap = new HashMap<>();
        for (Product product : products) {
            productQueryMap.put(product.getProductId(), product);
        }

        List<String> categoryIds = products
                .stream()
                .map(Product::getCategoryId)
                .distinct()
                .collect(Collectors.toList());
        // 购物车内商品的分类详情
        List<Category> categories = categoryDao.queryCategoriesByIds(categoryIds);
        Map<String, Category> categoryQueryMap = new HashMap<>();
        for (Category category : categories) {
            categoryQueryMap.put(category.getCategoryId(), category);
        }

        // 组装购物项
        List<CartItemVO> items = new ArrayList<>();
        for (CartItem cartItem : userCartItems) {
            Product product = productQueryMap.get(cartItem.getProductId());
            if (product == null) {
                // 商品不存在或被删除
                continue;
            }

            Category category = categoryQueryMap.get(product.getCategoryId());
            // 计算购物项总价
            BigDecimal itemTotalPrice = product
                    .getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getCount()));

            CartItemVO cartItemVo = CartItemVO
                    .builder()
                    .productId(product.getProductId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .imgUrls(product.getImages())
                    .description(product.getDescription())
                    .categoryName(category.getName())
                    .count(cartItem.getCount())
                    .totalPrice(itemTotalPrice)
                    .build();
            items.add(cartItemVo);
        }
        // 计算购物车共计
        BigDecimal cartTotal = items
                .stream()
                .map(CartItemVO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartVO
                .builder()
                .items(items)
                .count(items.size())
                .total(cartTotal)
                .build();
    }

    @Override
    public void addProduct(String userId, AddToCartQuery addToCartQuery) {
        Date now = new Date();
        String productId = addToCartQuery.getProductId();
        CartItem cartItem = cartItemDao.getByProductIdAndUserId(productId, userId);
        if (cartItem == null) {
            // 新增
            Product product = productDao.getProductById(productId);
            if (product == null) {
                throw new BizException("商品不存在或已被删除");
            }

            cartItem = CartItem
                    .builder()
                    .itemId(IdUtils.shortUUID())
                    .productId(product.getProductId())
                    .userId(userId)
                    .createTime(now)
                    .updateTime(now)
                    .count(addToCartQuery.getCount())
                    .build();
            cartItemDao.add(cartItem);
        }
        else {
            // 更新数量
            cartItem.setCount(cartItem.getCount() + addToCartQuery.getCount());
            cartItem.setUpdateTime(now);
            cartItemDao.update(cartItem);
        }
    }

    @Override
    public void removeProduct(String userId, RemoveFromCardQuery removeFromCardQuery) {
        String productId = removeFromCardQuery.getProductId();
        CartItem cartItem = cartItemDao.getByProductIdAndUserId(productId, userId);
        if (cartItem == null) {
            return;
        }

        // 移除购物项
        if (removeFromCardQuery.isEvict()) {
            cartItemDao.deleteByProductIdAndUserId(productId, userId);
            return;
        }

        int result = cartItem.getCount() - removeFromCardQuery.getCount();
        if (result > 0) {
            cartItem.setCount(result);
            cartItem.setUpdateTime(new Date());
            cartItemDao.update(cartItem);
        }
        else if (result == 0) {
            cartItemDao.deleteByProductIdAndUserId(productId, userId);
        }
        else {
            throw new BizException("购物车内的这种商品没有那么多");
        }
    }

    @Override
    public void clear(String userId) {
        cartItemDao.deleteAllByUserId(userId);
    }
}

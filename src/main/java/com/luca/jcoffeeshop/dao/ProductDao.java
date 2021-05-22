package com.luca.jcoffeeshop.dao;

import com.luca.jcoffeeshop.DO.Product;

import java.util.List;

public interface ProductDao {

    List<Product> queryProductBy(Integer pageNum, Integer pageSize, String searchTerm);

    Integer getProductTotal(String search);

    void addProduct(Product product);

    List<Product> queryProductsByIds(List<String> productIds);

    Product getProductById(String productId);

}

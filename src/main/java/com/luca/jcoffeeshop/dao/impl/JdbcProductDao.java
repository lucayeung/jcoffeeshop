package com.luca.jcoffeeshop.dao.impl;

import com.luca.jcoffeeshop.DO.Product;
import com.luca.jcoffeeshop.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("jdbcProductDao")
public class JdbcProductDao implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> queryProductBy(Integer pageNum, Integer pageSize, String searchTerm) {
        String sql = "select product_id, name, price, stock, image_urls, description, category_id, " +
                "create_time, update_time from t_product where is_del = 0 and name like :search " +
                "limit :size offset :page";
        RowMapper<Product> rowMapper = (rs, rowNum) -> Product
                .builder()
                .productId(rs.getString("product_id"))
                .name(rs.getString("name"))
                .price(rs.getBigDecimal("price"))
                .stock(rs.getInt("stock"))
                .imgUrls(rs.getString("image_urls"))
                .description(rs.getString("description"))
                .categoryId(rs.getString("category_id"))
                .createTime(rs.getDate("create_time"))
                .updateTime(rs.getDate("update_time"))
                .build();
        Map<String, Object> params = new HashMap<>();
        params.put("page", pageSize * (pageNum - 1));
        params.put("size", pageSize);
        params.put("search", "%" + searchTerm + "%");
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    @Override
    public Integer getProductTotal(String search) {
        String sql = "select count(*) from t_product where is_del = 0 and name like :search";
        SqlParameterSource params = new MapSqlParameterSource("search", "%" + search + "%");
        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    @Override
    public void addProduct(Product product) {
        String sql = "insert into t_product(product_id, name, price, stock, image_urls, description, category_id) " +
                "values(:productId, :name, :price, :stock, :imageUrls, :description, :categoryId)";
        Map<String, Object> params = new HashMap<>();
        params.put("productId", product.getProductId());
        params.put("name", product.getName());
        params.put("price", product.getPrice());
        params.put("stock", product.getStock());
        params.put("imageUrls", product.getImgUrls());
        params.put("description", product.getDescription());
        params.put("categoryId", product.getCategoryId());
        namedParameterJdbcTemplate.update(sql, params);
    }
}

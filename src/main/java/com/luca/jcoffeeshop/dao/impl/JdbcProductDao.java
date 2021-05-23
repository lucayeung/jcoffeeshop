package com.luca.jcoffeeshop.dao.impl;

import com.luca.jcoffeeshop.DO.Product;
import com.luca.jcoffeeshop.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("jdbcProductDao")
public class JdbcProductDao implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> queryProductBy(Integer pageNum, Integer pageSize, String searchTerm) {
        String sql = "select product_id, name, price, stock, image_urls, description, category_id, " +
                "create_time, update_time from t_product where is_del = 0 and name like :search " +
                "limit :size offset :page";
        Map<String, Object> params = new HashMap<>();
        params.put("page", pageSize * (pageNum - 1));
        params.put("size", pageSize);
        params.put("search", "%" + searchTerm + "%");
        return namedParameterJdbcTemplate.query(sql, params, productRowMapper());
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

    @Override
    public List<Product> queryProductsByIds(List<String> productIds) {
        String inClause = productIds
                .stream()
                .distinct()
                .map(productId -> String.format("'%s'", productId))
                .collect(Collectors.joining(","));
        String sql = "select product_id, name, price, stock, image_urls, description, category_id, " +
                "create_time, update_time from t_product where product_id in (" + inClause + ") and is_del = 0";
        return namedParameterJdbcTemplate.query(sql, productRowMapper());
    }

    @Override
    public Product getProductById(String productId) {
        String sql = "select product_id, name, price, stock, image_urls, description, category_id, " +
                "create_time, update_time from t_product where product_id = :productId and is_del = 0";

        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);

        Product product;
        try {
            product = namedParameterJdbcTemplate.queryForObject(sql, params, productRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {
            // 商品不存在或已被删除
            return null;
        }
        return product;
    }

    @Override
    public void batchUpdate(List<Product> products) {
        // 按需扩展字段...
        String sql = "update t_product set stock = :stock where product_id = :productId and is_del = 0";
        List<SqlParameterSource> sources = new ArrayList<>();
        for (Product product : products) {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("stock", product.getStock());
            source.addValue("productId", product.getProductId());
            sources.add(source);
        }
        namedParameterJdbcTemplate.batchUpdate(sql, sources.toArray(new SqlParameterSource[0]));
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> Product
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
    }
}

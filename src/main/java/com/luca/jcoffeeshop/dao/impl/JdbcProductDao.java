package com.luca.jcoffeeshop.dao.impl;

import com.luca.jcoffeeshop.DO.Product;
import com.luca.jcoffeeshop.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("jdbcProductDao")
public class JdbcProductDao implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> queryProductBy(Integer pageNum, Integer pageSize, String searchTerm) {
        String sql = "select product_id, name, price, stock, image_urls, description, category_id, " +
                "create_time, update_time from t_product where is_del = 0";
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
        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Integer getProductTotal() {
        return 0;
    }
}

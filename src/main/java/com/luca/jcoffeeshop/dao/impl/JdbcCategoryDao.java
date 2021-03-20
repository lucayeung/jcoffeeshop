package com.luca.jcoffeeshop.dao.impl;

import com.luca.jcoffeeshop.DO.Category;
import com.luca.jcoffeeshop.DO.CategoryDetail;
import com.luca.jcoffeeshop.dao.CategoryDao;
import com.luca.jcoffeeshop.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("jdbcCategoryDao")
public class JdbcCategoryDao implements CategoryDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<CategoryDTO> getAllCategories() {
        String sql = "select category_id, name, description from t_category";
        RowMapper<CategoryDTO> rowMapper = (rs, rowNum) -> CategoryDTO
                .builder()
                .categoryId(rs.getString("category_id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .build();
        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<CategoryDetail> getAllCategoryDetails() {
        String sql = "select c.name, c.category_id, " +
                "count(p.product_id) as product_type_count, " +
                "sum(p.stock) as product_total_stock " +
                "from t_product p " +
                "left join t_category c on p.category_id = c.category_id " +
                "group by c.category_id";
        RowMapper<CategoryDetail> rowMapper = (rs, rowNum) -> CategoryDetail
                .builder()
                .categoryId(rs.getString("category_id"))
                .name(rs.getString("name"))
                .productTypeCount(rs.getInt("product_type_count"))
                .productTotalStock(rs.getInt("product_total_stock"))
                .build();
        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void addCategory(Category category) {

    }
}

package com.luca.jcoffeeshop.biz.impl;

import com.luca.jcoffeeshop.DO.Category;
import com.luca.jcoffeeshop.DO.CategoryDetail;
import com.luca.jcoffeeshop.biz.CategoryService;
import com.luca.jcoffeeshop.dao.CategoryDao;
import com.luca.jcoffeeshop.dto.CategoryDTO;
import com.luca.jcoffeeshop.query.AddCategoryQuery;
import com.luca.jcoffeeshop.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("standardCategoryService")
public class StandardCategoryService implements CategoryService {

    @Autowired
    @Qualifier("jdbcCategoryDao")
    private CategoryDao categoryDao;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDetail> categoryDetails = categoryDao.getAllCategoryDetails();
        Map<String, CategoryDetail> queryTable = new HashMap<>();
        for (CategoryDetail categoryDetail : categoryDetails) {
            queryTable.put(categoryDetail.getCategoryId(), categoryDetail);
        }

        List<CategoryDTO> categories = categoryDao.getAllCategories();
        for (CategoryDTO categoryDTO : categories) {
            CategoryDetail categoryDetail = queryTable.get(categoryDTO.getCategoryId());
            categoryDTO.setProductTypeCount(categoryDetail.getProductTypeCount());
            categoryDTO.setProductCount(categoryDetail.getProductTotalStock());
        }
        return categories;
    }

    @Override
    public void addCategory(AddCategoryQuery addCategoryQuery) {
        Category category = Category
                .builder()
                .categoryId(IdUtils.shortUUID())
                .name(addCategoryQuery.getName())
                .description(addCategoryQuery.getDescription())
                .build();
        categoryDao.addCategory(category);
    }
}

package com.luca.jcoffeeshop.biz;

import com.luca.jcoffeeshop.dto.CategoryDTO;
import com.luca.jcoffeeshop.query.AddCategoryQuery;

import java.util.List;

public interface CategoryService {
    /**
     * 获取所有类目
     */
    List<CategoryDTO> getAllCategories();

    /**
     * 添加类目
     */
    void addCategory(AddCategoryQuery addCategoryQuery);
}

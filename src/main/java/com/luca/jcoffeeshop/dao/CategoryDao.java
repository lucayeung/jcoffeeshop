package com.luca.jcoffeeshop.dao;

import com.luca.jcoffeeshop.DO.Category;
import com.luca.jcoffeeshop.DO.CategoryDetail;
import com.luca.jcoffeeshop.dto.CategoryDTO;

import java.util.List;

public interface CategoryDao {

    List<CategoryDTO> getAllCategories();

    List<CategoryDetail> getAllCategoryDetails();

    void addCategory(Category category);

    List<Category> queryCategoriesByIdIn(String categoryIds);
}

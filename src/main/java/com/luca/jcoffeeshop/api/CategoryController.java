package com.luca.jcoffeeshop.api;

import com.luca.jcoffeeshop.biz.CategoryService;
import com.luca.jcoffeeshop.dto.CategoryDTO;
import com.luca.jcoffeeshop.dto.ResponseResult;
import com.luca.jcoffeeshop.query.AddCategoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    @Qualifier("standardCategory")
    private CategoryService categoryService;

    @GetMapping("categories")
    public ResponseResult getAllCategories() {
        List<CategoryDTO> response = categoryService.getAllCategories();
        return new ResponseResult("类目查询成功", 200, response);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody @Validated AddCategoryQuery addCategoryQuery) {
        categoryService.addCategory(addCategoryQuery);
        return new ResponseResult("添加类目成功", 200);
    }
}

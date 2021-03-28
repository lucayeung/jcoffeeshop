package com.luca.jcoffeeshop.api;

import com.luca.jcoffeeshop.biz.ProductService;
import com.luca.jcoffeeshop.dto.MenuDTO;
import com.luca.jcoffeeshop.dto.ResponseResult;
import com.luca.jcoffeeshop.query.AddProductQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    @Qualifier("standardProductService")
    private ProductService productService;

    @GetMapping("menu")
    public ResponseResult menu(@RequestParam(name = "page", defaultValue = "1", required = false) Integer pageNum,
            @RequestParam(name = "size", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(name = "search", required = false) String searchTerm) {
        MenuDTO menu = productService.menu(pageNum, pageSize, searchTerm);
        return new ResponseResult("成功", 200, menu);
    }

    @PostMapping
    public ResponseResult addProduct(@RequestBody @Validated AddProductQuery addProductQuery) {
        productService.addProduct(addProductQuery);
        return new ResponseResult("添加商品成功", 200);
    }
}

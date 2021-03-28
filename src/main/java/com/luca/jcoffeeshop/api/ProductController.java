package com.luca.jcoffeeshop.api;

import com.luca.jcoffeeshop.biz.ProductService;
import com.luca.jcoffeeshop.dto.MenuDTO;
import com.luca.jcoffeeshop.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    public ResponseResult addProduct() {
        return new ResponseResult("添加商品成功", 200);
    }
}

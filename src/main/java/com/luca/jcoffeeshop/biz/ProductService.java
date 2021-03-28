package com.luca.jcoffeeshop.biz;

import com.luca.jcoffeeshop.dto.MenuDTO;
import com.luca.jcoffeeshop.query.AddProductQuery;

public interface ProductService {

    MenuDTO menu(Integer pageNum, Integer pageSize, String searchTerm);

    void addProduct(AddProductQuery addProductQuery);

}

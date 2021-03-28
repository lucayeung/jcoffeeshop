package com.luca.jcoffeeshop.biz.impl;

import com.luca.jcoffeeshop.DO.Category;
import com.luca.jcoffeeshop.DO.Product;
import com.luca.jcoffeeshop.biz.ProductService;
import com.luca.jcoffeeshop.dao.CategoryDao;
import com.luca.jcoffeeshop.dao.ProductDao;
import com.luca.jcoffeeshop.dto.MenuCategoryDTO;
import com.luca.jcoffeeshop.dto.MenuDTO;
import com.luca.jcoffeeshop.dto.ProductDTO;
import com.luca.jcoffeeshop.query.AddProductQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("standardProductService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    @Qualifier("jdbcProductDao")
    private ProductDao productDao;

    @Autowired
    @Qualifier("jdbcCategoryDao")
    private CategoryDao categoryDao;

    @Override
    public MenuDTO menu(Integer pageNum, Integer pageSize, String searchTerm) {
        String search = "";
        if (StringUtils.hasLength(searchTerm)) {
            search = searchTerm;
        }
        List<Product> products = productDao.queryProductBy(pageNum, pageSize, search);

        String categoryIds = products
                .stream()
                .map(product -> String.format("'%s'", product.getCategoryId()))
//                .map(Product::getCategoryId)
                .distinct()
                .collect(Collectors.joining(","));
        List<Category> categories = categoryDao.queryCategoriesByIdIn(categoryIds);

        Integer productTotal = productDao.getProductTotal(search);

        Map<String, List<ProductDTO>> menuProducts = products
                .stream()
                // 库存不足
                .filter(product -> product.getStock() > 0)
                .map(product -> ProductDTO
                        .builder()
                        .name(product.getName())
                        .stock(product.getStock())
                        .price(product.getPrice())
                        .imgUrls(product.getImages())
                        .productId(product.getProductId())
                        .createTime(product.getCreateTime())
                        .updateTime(product.getUpdateTime())
                        .categoryId(product.getCategoryId())
                        .description(product.getDescription())
                        .build())
                .collect(Collectors.groupingBy(ProductDTO::getCategoryId));

        List<MenuCategoryDTO> menuCategories =  categories
                .stream()
                .map(category -> MenuCategoryDTO
                        // FIXME(luca): BeanUtils#copyProperties
                        .builder()
                        .categoryId(category.getCategoryId())
                        .name(category.getName())
                        .description(category.getDescription())
                        .createTime(category.getCreateTime())
                        .products(menuProducts.getOrDefault(category.getCategoryId(), Collections.emptyList()))
                        .build())
                .collect(Collectors.toList());

        return MenuDTO
                .builder()
                .categories(menuCategories)
                .total(productTotal)
                .build();
    }

    @Override
    public void addProduct(AddProductQuery addProductQuery) {

    }
}

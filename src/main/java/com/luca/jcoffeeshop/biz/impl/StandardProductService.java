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
import com.luca.jcoffeeshop.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("standardProductService")
public class StandardProductService implements ProductService {

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

        List<String> categoryIds = products
                .stream()
                .map(Product::getCategoryId)
                .collect(Collectors.toList());
        List<Category> categories = categoryDao.queryCategoriesByIds(categoryIds);

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
    @Transactional // 两个数据库操作以上
    public void addProduct(AddProductQuery addProductQuery) {
        Product product = Product.builder()
                .productId(IdUtils.shortUUID())
                .name(addProductQuery.getName())
                .categoryId(addProductQuery.getCategoryId())
                .description(addProductQuery.getDescription())
                .stock(addProductQuery.getStock())
                .price(addProductQuery.getPrice())
                .imgUrls(addProductQuery.getImages())
                .build();
        productDao.addProduct(product);
    }
}

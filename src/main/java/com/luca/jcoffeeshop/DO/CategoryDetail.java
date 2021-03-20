package com.luca.jcoffeeshop.DO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDetail {

    private String categoryId;

    private String name;

    /**
     * 商品类型数目
     */
    private Integer productTypeCount;

    /**
     * 商品总库存
     */
    private Integer productTotalStock;
}

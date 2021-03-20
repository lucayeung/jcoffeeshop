package com.luca.jcoffeeshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    
    private String categoryId;

    private String name;

    private String description;

    /**
     * 类目下商品类型的数目
     */
    private Integer productTypeCount;

    /**
     * 类目下商品的总数量
     */
    private Integer productCount;
}

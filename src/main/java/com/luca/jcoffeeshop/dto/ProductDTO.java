package com.luca.jcoffeeshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String productId;

    private String categoryId;

    private String name;

    private BigDecimal price;

    private Integer stock;

    //private String imgUrls;
    private List<String> imgUrls;

    private String description;

    //private String categoryId;

    private Date createTime;

    private Date updateTime;

}

package com.luca.jcoffeeshop.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductQuery {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private String categoryId;

    private String imgUrls;
}

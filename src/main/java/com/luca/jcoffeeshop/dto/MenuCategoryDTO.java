package com.luca.jcoffeeshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryDTO {

    private String categoryId;

    private String name;

    private String description;

    private Date createTime;

    private List<ProductDTO> products;

}

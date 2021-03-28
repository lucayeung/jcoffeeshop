package com.luca.jcoffeeshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {

    private List<MenuCategoryDTO> categories;

    /**
     * 商品总数目
     */
    private Integer total;
}

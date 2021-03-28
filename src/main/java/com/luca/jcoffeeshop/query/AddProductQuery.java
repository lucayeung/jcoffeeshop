package com.luca.jcoffeeshop.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductQuery {

    @NotBlank(message = "商品名称不能为空")
    private String name;

    @NotNull(message = "商品描述不能为NULL")
    private String description;

    @NotNull(message = "商品必须填写价格")
    private BigDecimal price;

    @Min(value = 0, message = "库存必须是正整数")
    @Max(value = 999, message = "库存超过上限99")
    @NotNull(message = "库存不能为空")
    private Integer stock;

    @NotBlank(message = "商品类目不能为空")
    private String categoryId;

    @Size(max = 10)
    private List<String> imgUrls;

    public String getImages() {
        return String.join(",", imgUrls);
    }
}

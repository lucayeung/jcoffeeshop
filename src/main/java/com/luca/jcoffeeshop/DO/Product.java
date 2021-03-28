package com.luca.jcoffeeshop.DO;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 商品 Data Object
 */
@Data
@Builder
public class Product {

    private Long id;

    private String productId;

    private String name;

    private BigDecimal price;

    private Integer stock;

    /**
     * 商品例图
     * 例如: url1,url2,url3,url4
     */
    private String imgUrls;

    private String description;

    private String categoryId;

    private Date createTime;

    private Date updateTime;

    private Boolean isDel;

    /**
     * 解析商品图片拼接路径
     */
    public List<String> getImages() {
        if (StringUtils.hasText(imgUrls)) {
            return Collections.emptyList();
        }

        String[] images = imgUrls.split(",");
        return Arrays.asList(images);
    }
}

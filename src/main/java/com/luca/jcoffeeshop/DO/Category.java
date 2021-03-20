package com.luca.jcoffeeshop.DO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 商品类目 Data Object
 */
@Data
@Builder
public class Category {

    /**
     * 数据库主键
     */
    private Long id;

    /**
     * 类目唯一标识
     * 64位短UUID
     */
    private String categoryId;

    /**
     * 类目名称
     */
    private String name;

    /**
     * 类目描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除
     */
    private Boolean isDel;
}

package com.luca.jcoffeeshop.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCategoryQuery {

    @NotBlank(message = "类目名称不能为空")
    @Length(min = 2, max = 10, message = "类目名称为2-10个字符")
    private String name;

    @NotBlank(message = "类目描述不能为空")
    private String description;
}

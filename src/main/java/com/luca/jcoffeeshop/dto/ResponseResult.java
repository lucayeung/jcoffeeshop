package com.luca.jcoffeeshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult implements Serializable {

    private String message;

    private Integer code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public ResponseResult(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}

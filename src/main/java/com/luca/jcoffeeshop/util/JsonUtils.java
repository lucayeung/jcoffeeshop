package com.luca.jcoffeeshop.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> String toJson(T o) throws IOException {
        return mapper.writeValueAsString(o);
    }

    public static <T>T toClass(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

}

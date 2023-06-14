package com.alibaba.repeater.console.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class MapUtil {
    /**
     * 将map转换为POJO对象
     *
     * @param map       map
     * @param beanClass class
     * @return r
     */
    public static <T> T mapConvertToObject(Map<String, Object> map, Class<T> beanClass) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, beanClass);
    }
}

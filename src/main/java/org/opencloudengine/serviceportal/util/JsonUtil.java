package org.opencloudengine.serviceportal.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by swsong on 2015. 8. 16..
 */
public class JsonUtil {
    public static Map<String, Object> json2Object(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeReference = new TypeReference<HashMap<String, Object>>() { };
        return objectMapper.readValue(json, typeReference);
    }

    public static <T> T json2Object(String json, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<T> typeReference = new TypeReference<T>() { };
        return objectMapper.readValue(json, typeReference);
    }

    public static String object2String(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}

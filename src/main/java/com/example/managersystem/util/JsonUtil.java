package com.example.managersystem.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;

/**
 * {@code @className:}      JsonUtils
 * {@code @author:}     dengxiangtian
 * {@code @description:}  TODO
 * {@code @date:}    2024/4/27 7:49 AM
 */
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public JsonUtil() {
    }

    public static String toJson(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }

    public static void toJsonFile(Object data, File file) throws IOException {
        mapper.writeValue(file, data);
    }


    public static <T> T fromJson(File jsonFile, TypeReference<T> typeReference) throws IOException {
        return mapper.readValue(jsonFile, typeReference);
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) throws IOException {
        return mapper.readValue(json, typeReference);
    }

    public static ObjectMapper createDefaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    static {
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
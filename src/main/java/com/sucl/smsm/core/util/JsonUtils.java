package com.sucl.smsm.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JacksonJsonParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author sucl
 * @since 2018/12/8
 */
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T toObject(String source) {
        try {
            return MAPPER.readValue(source, new TypeReference<T>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String,Object> toMap(String json){
        return new JacksonJsonParser().parseMap(json);
    }

    public static List<Object> toList(String json){
        return new JacksonJsonParser().parseList(json);
    }
}

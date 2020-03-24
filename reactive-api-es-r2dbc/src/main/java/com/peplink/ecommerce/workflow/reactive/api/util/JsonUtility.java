package com.peplink.ecommerce.workflow.reactive.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtility {

    private final static Logger _LOGGER = LoggerFactory.getLogger(JsonUtility.class);

    public static String objectToJsonString(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        try {
            String jsonString = null;
            ObjectMapper mapper = new ObjectMapper();
            jsonString = mapper.writeValueAsString(obj);
            return jsonString;
        } catch (JsonProcessingException e) {
            _LOGGER.error("Error", e);
            return null;
        }
    }

    public static <T> T jsonStringToObject(String json, Class<T> aClass) {
        if (StringUtils.isEmpty(json) || ObjectUtils.isEmpty(aClass)) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, aClass);
        } catch (JsonProcessingException e) {
            _LOGGER.error("Error", e);
            return null;
        }
    }

}

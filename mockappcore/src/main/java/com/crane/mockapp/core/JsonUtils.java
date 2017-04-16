package com.crane.mockapp.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.InputStream;

public class JsonUtils {

    public static int asInt(JsonNode node, String fieldName, int defaultValue) {
        if (node == null)
            return defaultValue;
        JsonNode childNode = node.get(fieldName);
        return childNode == null ? defaultValue : childNode.asInt(defaultValue);
    }

    public static int asInt(JsonNode node, String fieldName) {
        return asInt(node, fieldName, 0);
    }

    public static String asString(JsonNode node, String fieldName, String defaultValue) {
        if (node == null)
            return defaultValue;
        JsonNode childNode = node.get(fieldName);
        return childNode == null ? defaultValue : childNode.asText(defaultValue);
    }

    public static String asString(JsonNode node, String fieldName) {
        return asString(node, fieldName, null);
    }

    public static boolean asBoolean(JsonNode node, String fieldName, boolean defaultValue) {
        if (node == null)
            return defaultValue;
        JsonNode childNode = node.get(fieldName);
        return childNode == null ? defaultValue : childNode.asBoolean(defaultValue);
    }

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE);
        OBJECT_MAPPER.getDeserializationConfig().getDefaultVisibilityChecker()
                .withCreatorVisibility(JsonAutoDetect.Visibility.ANY)
                .withFieldVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE);
    }

    private final static JsonFactory JSON_FACTORY = new JsonFactory();

    public static <T> T fromInputStream(InputStream inputStream, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(inputStream, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromFile(File file, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(file, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean toFile(Object obj, File file, boolean pretty) {
        try {
            if (pretty)
                OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
            else
                OBJECT_MAPPER.disable(SerializationFeature.INDENT_OUTPUT);
            OBJECT_MAPPER.writeValue(file, obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String toString(Object obj, boolean pretty) {
        try {
            if (pretty)
                OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
            else
                OBJECT_MAPPER.disable(SerializationFeature.INDENT_OUTPUT);
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromString(String s, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(s, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

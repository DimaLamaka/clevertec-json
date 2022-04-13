package ru.clevertec.json;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class ConverterImpl<T> implements Converter<T> {
    @Override
    public String convertToJson(T t) throws IllegalAccessException {
        return new StringBuilder()
                .append("{\n")
                .append(toJson(t))
                .append("}\n")
                .toString();
    }

    @Override
    public void writeJsonToFile(String json,String path) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write(json);
        }
    }

    private String objToJson(Object obj, String name) throws IllegalAccessException {
        return new StringBuilder()
                .append("\"")
                .append(name)
                .append("\"")
                .append(": \n{\n")
                .append(toJson(obj))
                .append("}\n")
                .toString();
    }

    private String collectionAndArrayToJson(Object obj, String fieldName, boolean isArray) throws IllegalAccessException {
        StringBuilder result = new StringBuilder();
        Object[] objects = isArray ? (Object[]) obj : ((Collection<?>) obj).toArray();
        result.append("\"")
                .append(fieldName)
                .append("\"")
                .append(": [\n");

        for (Object nextObj : objects) {
            if (checkBySimple(nextObj.getClass(), nextObj)) {
                result.append("\"")
                        .append(nextObj)
                        .append("\",\n");

            } else {
                result.append("{\n").
                        append(toJson(nextObj))
                        .append("},\n");
            }
        }
        result.append("]");
        return result.toString().replace(",\n]", " \n]\n");
    }

    private String toJson(Object obj) throws IllegalAccessException {
        StringBuilder result = new StringBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Class<?> type = fields[i].getType();
            Object fieldObj = fields[i].get(obj);

            if (Objects.isNull(fieldObj)) {
                result.append("\"")
                        .append(fields[i].getName())
                        .append("\":")
                        .append("null");
            } else if (fieldObj instanceof Map<?, ?>) {
                result.append(mapToJson(fieldObj, fields[i].getName()));
            } else if (fieldObj instanceof Collection<?>) {
                result.append(collectionAndArrayToJson(fieldObj, fields[i].getName(), false));
            } else if (checkBySimple(type, fieldObj)) {
                result.append("\"")
                        .append(fields[i].getName())
                        .append("\":")
                        .append("\"")
                        .append(fieldObj)
                        .append("\"");
            } else if (type.isArray()) {
                result.append(collectionAndArrayToJson(fieldObj, fields[i].getName(), true));
            } else {
                result.append(objToJson(fieldObj, fields[i].getName()));
            }
            if (i != fields.length - 1) {
                result.append(",\n");
            } else {
                result.append("\n");
            }
        }
        return result.toString();
    }

    private String mapToJson(Object obj, String fieldName) throws IllegalAccessException {
        StringBuilder result = new StringBuilder();
        Map<Object, Object> map = (Map<Object, Object>) obj;
        result.append("\"").append(fieldName).append("\"").append(": {\n");

        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();

            if (checkBySimple(key.getClass(), key)) {
                result.append("\"")
                        .append(key)
                        .append("\"");
            } else {
                result.append("{")
                        .append(toJson(key))
                        .append("}");
            }
            result.append(" : ");
            if (checkBySimple(value.getClass(), value)) {
                result.append("\"")
                        .append(value)
                        .append("\"");
            } else {
                result.append("{")
                        .append(toJson(value))
                        .append("}");
            }
            result.append(",");
        }

        result.append("}");
        return result.toString().replace(",}", " }\n");
    }

    private boolean checkBySimple(Class<?> type, Object fieldObj) {
        return type.isPrimitive() || fieldObj instanceof String || fieldObj instanceof Number
                || fieldObj instanceof Character || fieldObj instanceof Boolean;
    }

}

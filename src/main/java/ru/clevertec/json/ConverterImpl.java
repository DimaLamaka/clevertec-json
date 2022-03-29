package ru.clevertec.json;

import java.lang.reflect.Field;

public class ConverterImpl<T> implements Converter<T> {
    @Override
    public String toJSON(T t) throws IllegalAccessException {
        StringBuilder result = new StringBuilder();
        Field[] fields = t.getClass().getDeclaredFields();
        result.append("{\n");
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> type = field.getType();
            if (field.getType().isPrimitive() || type.getSimpleName().equals("String")) {
                result.append("\"")
                        .append(field.getName())
                        .append("\":")
                        .append("\"")
                        .append(field.get(t))
                        .append("\"\n");
            } else if (type.isArray()) {
                result.append(arrayToJson(field.get(t), field.getName()));
            } else {
                result.append(objToJson(field.get(t), field.getName()));
            }
        }
        result.append("}\n");
        return result.toString();
    }

    private String objToJson(Object obj, String name) throws IllegalAccessException {
        StringBuilder result = new StringBuilder();
        result.append("\"").append(name).append("\"").append(": \n{\n");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> type = field.getType();
            if (field.getType().isPrimitive() || type.getSimpleName().equals("String")) {
                result.append("\"")
                        .append(field.getName())
                        .append("\":")
                        .append("\"")
                        .append(field.get(obj))
                        .append("\"\n");
            }else if (type.isArray()) {
                result.append(arrayToJson(field.get(obj), field.getName()));
            }
            else {
                result.append(objToJson(field.get(obj), field.getName()));
            }
        }
        result.append("}\n");
        return result.toString();
    }

    private String arrayToJson(Object obj, String name) throws IllegalAccessException {
        StringBuilder result = new StringBuilder();
        result.append("\"").append(name).append("\"").append(": \n[\n");
        Object[] objects = (Object[]) obj;
        for (Object object : objects) {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType().isArray()) {
                    result.append(arrayToJson(field.get(object), field.getName()));
                } else if (field.getType().isPrimitive() || field.getType().getSimpleName().equals("String")) {
                    result.append("\"")
                            .append(field.getName())
                            .append("\":")
                            .append("\"")
                            .append(field.get(object))
                            .append("\"\n");
                } else {
                    result.append(objToJson(field.get(obj), field.getName()));
                }
            }
            result.append(",\n");
        }
        result.append("]\n");
        return result.toString();
    }

}

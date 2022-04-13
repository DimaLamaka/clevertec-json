package ru.clevertec.json;

import java.io.IOException;

public interface Converter<T> {
    String convertToJson(T t) throws IllegalAccessException;
    void writeJsonToFile(String json,String path) throws IOException;
}

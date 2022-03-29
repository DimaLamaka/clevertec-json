package ru.clevertec.json;

public interface Converter<T> {
    String toJSON(T t) throws IllegalAccessException;
}

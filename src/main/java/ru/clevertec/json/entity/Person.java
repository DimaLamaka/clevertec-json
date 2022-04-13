package ru.clevertec.json.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    private String name;
    private int age;
    private String email;
    private Address address;
    private Book[] books;
    private List<Phone> phones;
    private Map<String,String> pets;
}

package ru.clevertec.json.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String name;
    private int age;
    private String email;
    private Address address;
    private Book[] books;
}

package ru.clevertec.json.entity;

import java.util.List;

public class Employee {
    private int age;
    private String name;
    private Phone[] phones;


    public Employee(int age, String name, Phone[] phones) {
        this.age = age;
        this.name = name;
        this.phones = phones;
    }
}

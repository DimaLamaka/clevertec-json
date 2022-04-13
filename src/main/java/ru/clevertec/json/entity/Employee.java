package ru.clevertec.json.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private int age;
    private String name;
    private Integer[] phones;
}

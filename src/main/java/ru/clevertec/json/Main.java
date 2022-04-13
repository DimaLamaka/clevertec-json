package ru.clevertec.json;

import ru.clevertec.json.entity.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) throws IllegalAccessException {

        Converter<Person> converter = new ConverterImpl<>();
        Person person = Person.builder()
                .phones(Arrays.asList(
                        new Phone("mob","2542525211"),
                        new Phone("dom","4335213")))
                .address(new Address("Esenina street"))
                .books(new Book[]{
                        new Book("JAVA",new Author("Ivan","Ivanov")),
                        new Book("GRADLE",new Author("Sidr","Sidorov"))})
                .name("Evdosiy")
                .age(66)
                .email("evdosiy@tut.by")
                .pets(new HashMap<String,String>(){{
                    put("Cat","Barbos");
                    put("Dog","Sharik");
                }})
                .build();

        String json = converter.convertToJson(person);
        String path = "src/main/resources/employee.json";

        try {
            converter.writeJsonToFile(json,path);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }
}


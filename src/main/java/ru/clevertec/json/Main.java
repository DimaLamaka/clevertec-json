package ru.clevertec.json;

import ru.clevertec.json.entity.*;


public class Main {
    public static void main(String[] args) throws IllegalAccessException {

        Converter<Person> converter = new ConverterImpl<>();
        Person person = new Person();
        Address address = new Address();
        Book[] books = new Book[]{new Book("JAVA"),new Book("GRADLE")};
        Phone phone = new Phone("mob","2542525211");
        address.setPhone(phone);
        address.setStreet("spring street");
        person.setName("me");
        person.setAge(21);
        person.setEmail("me@doit.com");
        person.setAddress(address);
        person.setBooks(books);
        String s = converter.toJSON(person);
        System.out.println(s);




    }
}


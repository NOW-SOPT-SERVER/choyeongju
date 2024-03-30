package org.example;

import org.example.classes.Person;
import org.example.classes.PersonBuilder;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("조영주", 23, "female");

        System.out.println(person.getName());
        person.setName("귀요미");
        System.out.println(person.getName());

        person.walk();

        Person personWithBuilder = new PersonBuilder()
                .name("도소현")
                .age(24)
                .sex("female")
                .build();
    }
}
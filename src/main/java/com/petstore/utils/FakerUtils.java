package com.petstore.utils;

import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FakerUtils {

    public static String generateAnimalName() {
        Faker faker = new Faker();
        return faker.animal().name();
    }

    public static String generateStatus() {
        List<String> statuses = Arrays.asList("available", "pending", "sold");
        Random random = new Random();
        return statuses.get(random.nextInt(statuses.size()));
    }

    public static String generateDescription() {
        Faker faker = new Faker();
        return "Description " + faker.regexify("[ A-Za-z0-9_@./#&+-]{50}");
    }

    public static Integer generateNumber() {
        Faker faker = new Faker();
        faker.random();
        return faker.number().numberBetween(0, 5000);
    }


    public static String generateCategoryName() {
        Faker faker = new Faker();
        return "Category " + faker.regexify("[A-Za-z0-9 ,_-]{10}");
    }
}

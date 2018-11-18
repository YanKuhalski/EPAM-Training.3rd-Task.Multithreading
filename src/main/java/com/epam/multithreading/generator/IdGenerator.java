package com.epam.multithreading.generator;

public class IdGenerator {
    private static long uberId = 1;

    public static long generateUberId() {
        return uberId++;
    }
}

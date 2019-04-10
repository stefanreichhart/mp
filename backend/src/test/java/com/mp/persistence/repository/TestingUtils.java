package com.mp.persistence.repository;

import java.util.UUID;

public final class TestingUtils {

    private TestingUtils() {}

    public static String getRandomUniqueName() {
        return UUID.randomUUID().toString();
    }
}

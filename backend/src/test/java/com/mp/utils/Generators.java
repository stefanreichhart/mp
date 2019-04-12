package com.mp.utils;

import java.util.UUID;

public final class Generators {

    private Generators() {}

    public static String getRandomUniqueName() {
        return UUID.randomUUID().toString();
    }

}

package com.mp.data;

import org.junit.jupiter.params.provider.Arguments;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public final class TestData {

    private TestData() {}

    public static Stream<? extends Arguments> getSqlTemporals() {
        return Stream.of(
                Arguments.of(
                        new Timestamp(2019, 10, 22, 23, 59, 59, 999999999),
                        LocalDateTime.of(2019, 11, 22, 23, 59, 59, 999999999)),
                Arguments.of(
                        new Timestamp(2018, 0, 3, 5, 4, 1, 34),
                        LocalDateTime.of(2018, 1, 3, 5, 4, 1, 34)),
                Arguments.of(
                        new Timestamp(2022, 11, 30, 23, 0, 0, 1),
                        LocalDateTime.of(2022, 12, 30, 23, 0, 0, 1))
        );
    }
}

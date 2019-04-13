package com.mp.data;

import org.junit.jupiter.params.provider.Arguments;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public final class TestData {

    private TestData() {
    }

    public static Stream<? extends Arguments> getLocalDateTimeAndString() {
        return Stream.of(
                Arguments.of(
                        "2019-11-22T21:01:59.999Z",
                        LocalDateTime.of(2019, 11, 22, 21, 1, 59, 999000000)),
                Arguments.of(
                        "2018-01-03T22:22:33",
                        LocalDateTime.of(2018, 1, 3, 22, 22, 33)),
                Arguments.of(
                        "2010-05-11 14:51:04.009",
                        LocalDateTime.of(2010, 5, 11, 14, 51, 4, 9000000)),
                Arguments.of(
                        "2023-12-31 11:36:01",
                        LocalDateTime.of(2023, 12, 31, 11, 36, 1)),
                Arguments.of(
                        "2219-06-30 01:09",
                        LocalDateTime.of(2219, 6, 30, 1, 9)),
                Arguments.of(
                        "1987-01-04 23",
                        LocalDateTime.of(1987, 1, 4, 23, 0)),
                Arguments.of(
                        "1999-02-01",
                        LocalDateTime.of(1999, 2, 1, 0, 0)));
    }

    public static Stream<? extends Arguments> getLocalDateTimeAndSqlTimestamp() {
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

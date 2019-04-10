package com.mp.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LocalDateTimeUtilTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "2019-11-22T23:59:59.999Z",
            "2019-11-22T23:59:59",
            "2019-11-22 23:59:59.999",
            "2019-11-22 23:59:59",
            "2019-11-22 23:59",
            "2019-11-22"
    })
    public void fromString(String source) {
        assertNotNull(LocalDateTimeUtil.fromString(source));
    }

    @ParameterizedTest(name = "{index} => sql={0}, localDateTime={1}")
    @MethodSource("getSqlTemporals")
    public void toSqlTemporal(Timestamp expected, LocalDateTime localDateTime) {
        assertEquals(expected, LocalDateTimeUtil.toSqlTemporal(localDateTime));
    }

    @ParameterizedTest(name = "{index} => sql={0}, localDateTime={1}")
    @MethodSource("getSqlTemporals")
    public void fromSqlTemporal(Timestamp sql, LocalDateTime expected) {
        assertEquals(expected, LocalDateTimeUtil.fromSqlTemporal(sql));
    }

    private static Stream<? extends Arguments> getSqlTemporals() {
        return Stream.of(
                Arguments.of(
                        new Timestamp(2019, 11, 22, 23, 59, 59, 999999999),
                        LocalDateTime.of(2019, 11, 22, 23, 59, 59, 999999999)));
    }

}

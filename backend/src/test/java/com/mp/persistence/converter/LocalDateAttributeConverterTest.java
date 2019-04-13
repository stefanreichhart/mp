package com.mp.persistence.converter;

import com.mp.data.TestData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalDateAttributeConverterTest {

    private LocalDateAttributeConverter converter = new LocalDateAttributeConverter();

    @ParameterizedTest(name = "{index} => sql={0}, localDateTime={1}")
    @MethodSource("getSqlTemporals")
    public void toSqlTemporal(Timestamp expected, LocalDateTime localDateTime) {
        assertEquals(expected, converter.convertToDatabaseColumn(localDateTime));
    }

    @ParameterizedTest(name = "{index} => sql={0}, localDateTime={1}")
    @MethodSource("getSqlTemporals")
    public void fromSqlTemporal(Timestamp sql, LocalDateTime expected) {
        assertEquals(expected, converter.convertToEntityAttribute(sql));
    }

    public static Stream<? extends Arguments> getSqlTemporals() {
        return TestData.getLocalDateTimeAndSqlTimestamp();
    }

}

package com.mp.api.config;

import com.mp.MeasurementPointsLocalApplication;
import com.mp.data.TestData;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeasurementPointsLocalApplication.class)
public class StringToLocalDateTimeConverterTest {

    @Autowired
    private StringToLocalDateTimeConverter converter;

    @ParameterizedTest(name = "{index} => sql={0}, localDateTime={1}")
    @MethodSource("getSqlTemporals")
    public void fromSqlTemporal(String source, LocalDateTime expected) {
        assertEquals(expected, converter.convert(source));
    }

    public static Stream<? extends Arguments> getSqlTemporals() {
        return TestData.getLocalDateTimeAndString();
    }
}

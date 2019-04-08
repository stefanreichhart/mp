package com.measurementpointslocal.api.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    // TODO fix this
    private static final String[] PATTERNS = {
            "yyyy-MM-dd HH:mm:ss"
    };

    @Override
    public LocalDateTime convert(String source) {
        for (String pattern : PATTERNS) {
            try {
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException exception) {
                // ignore, continue
            }
        }
        return null;
    }
}

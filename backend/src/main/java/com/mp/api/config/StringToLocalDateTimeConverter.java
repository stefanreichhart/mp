package com.mp.api.config;

import com.mp.utils.LocalDateTimeUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        return LocalDateTimeUtil.fromString(source);
    }

}

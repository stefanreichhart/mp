package com.mp.persistence.converter;

import com.mp.utils.LocalDateTimeUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return LocalDateTimeUtil.toSqlTemporal(localDateTime);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlDate) {
        return LocalDateTimeUtil.fromSqlTemporal(sqlDate);
    }

}

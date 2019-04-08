package com.measurementpointslocal.persistence.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return (localDateTime == null ? null : new Timestamp(localDateTime.getYear(), localDateTime.getMonth().getValue(), localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond(), 0));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlDate) {
        return LocalDateTime.of(sqlDate.getYear(), sqlDate.getMonth(), sqlDate.getDay(), sqlDate.getHours(), sqlDate.getMinutes(), sqlDate.getSeconds());
    }

}

package com.mp.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeUtil {

    private LocalDateTimeUtil() {}

    private static final String[] PATTERNS = {
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm"
    };

    public static LocalDateTime fromString(String source) {
        for (String pattern : PATTERNS) {
            try {
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException exception) {
                // ignore, continue
            }
        }
        return null;
    }

    public static Timestamp toSqlTemporal(LocalDateTime localDateTime) {
        return (localDateTime == null ? null : new Timestamp(localDateTime.getYear(), localDateTime.getMonth().getValue(), localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond(), localDateTime.getNano()));
    }

    public static LocalDateTime fromSqlTemporal(Timestamp sqlDate) {
        return LocalDateTime.of(sqlDate.getYear(), sqlDate.getMonth(), sqlDate.getDate(), sqlDate.getHours(), sqlDate.getMinutes(), sqlDate.getSeconds(), sqlDate.getNanos());
    }

}

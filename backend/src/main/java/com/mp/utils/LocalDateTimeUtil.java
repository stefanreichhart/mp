package com.mp.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeUtil {

    private LocalDateTimeUtil() {}

    private static final String[] DATE_TIME_PATTERNS = {
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd HH"
    };

    private static final String[] DATE_PATTERNS = {
            "yyyy-MM-dd",
            "yyyy/MM/dd"
    };

    public static LocalDateTime fromString(String source) {
        for (String pattern : DATE_TIME_PATTERNS) {
            try {
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException exception) {
                // ignore, continue
            }
        }
        for (String pattern : DATE_PATTERNS) {
            try {
                LocalDate localDate = LocalDate.parse(source, DateTimeFormatter.ofPattern(pattern));
                return LocalDateTime.of(localDate, LocalTime.of(0,0, 0, 0));
            } catch (DateTimeParseException exception) {
                // ignore, continue
            }
        }
        return null;
    }

    public static Timestamp toSqlTemporal(LocalDateTime localDateTime) {
        return localDateTime == null ? null : new Timestamp(localDateTime.getYear(), localDateTime.getMonth().getValue()-1, localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond(), localDateTime.getNano());
    }

    public static LocalDateTime fromSqlTemporal(Timestamp sqlDate) {
        return sqlDate == null ? null : LocalDateTime.of(sqlDate.getYear(), sqlDate.getMonth()+1, sqlDate.getDate(), sqlDate.getHours(), sqlDate.getMinutes(), sqlDate.getSeconds(), sqlDate.getNanos());
    }

}

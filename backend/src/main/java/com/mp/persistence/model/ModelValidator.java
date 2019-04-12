package com.mp.persistence.model;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public final class ModelValidator {

    private ModelValidator() {}

    public static void requireNotNullNotEmpty(String name, String property) {
        if (name == null || StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(property + " must not be null or empty");
        }
    }

    public static void requireDateRange(LocalDateTime validFrom, LocalDateTime validTo) {
        if (validFrom != null && validTo != null && validFrom.isAfter(validTo)) {
            throw new IllegalArgumentException(" from-date/time must be before to-date/time");
        }
    }

    public static void requireNotNull(Object object, String property) {
        if (object == null) {
            throw new IllegalArgumentException(property + " must not be null");
        }
    }

    public static <T,U> void requireUnique(List<T> list, T insertable, Function<T,U> accessor) {
        if (list != null) {
            final U comparable = accessor.apply(insertable);
            if (comparable != null) {
                Optional<T> found = list.stream()
                        .filter(each -> !insertable.equals(each) && comparable.equals(accessor.apply(each)))
                        .findFirst();
                if (found.isPresent()) {
                    throw new IllegalArgumentException(insertable + " is already present in " + list);
                }
            }
        }
    }
}

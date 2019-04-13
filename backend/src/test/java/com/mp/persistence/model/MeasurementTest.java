package com.mp.persistence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MeasurementTest {

    private Measurement measurement;

    @BeforeEach
    public void setUp() {
        assertDoesNotThrow(() -> measurement = new Measurement());
    }

    @Test
    public void ratingThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> measurement.setRating(null));
    }

    @Test
    public void pollThrowsException() {
        assertDoesNotThrow(() -> measurement.setPoll(null));
    }

    @Test
    public void criteriaThrowsException() {
        assertDoesNotThrow(() -> measurement.setCriteria(null));
    }

    @Test
    public void person() {
        assertDoesNotThrow(() -> measurement.setPerson(null));
    }

}

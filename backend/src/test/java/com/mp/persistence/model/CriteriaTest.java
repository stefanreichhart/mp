package com.mp.persistence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CriteriaTest {

    private Criteria criteria;

    @BeforeEach
    public void setUp() {
        assertDoesNotThrow(() -> criteria = new Criteria());
    }

    @Test
    public void name() {
        assertDoesNotThrow(() -> criteria.setName("name"));
    }

    @Test
    public void nameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> criteria.setName(null));
        assertThrows(IllegalArgumentException.class, () -> criteria.setName(""));
        assertThrows(IllegalArgumentException.class, () -> criteria.setName(" "));
    }

    @Test
    public void poll() {
        assertDoesNotThrow(() -> criteria.setPoll(null));
    }

}

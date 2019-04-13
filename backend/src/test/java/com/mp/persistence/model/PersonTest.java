package com.mp.persistence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersonTest {

    private Person person;

    @BeforeEach
    public void setUp() {
        assertDoesNotThrow(() -> person = new Person());
    }

    @Test
    public void name() {
        assertDoesNotThrow(() -> person.setName("name"));
    }

    @Test
    public void nameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> person.setName(null));
        assertThrows(IllegalArgumentException.class, () -> person.setName(""));
        assertThrows(IllegalArgumentException.class, () -> person.setName(" "));
    }

}

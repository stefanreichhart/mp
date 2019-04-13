package com.mp.utils;

import com.mp.persistence.utils.ModelFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModelFactoryTest {

    private ModelFactory modelFactory = new ModelFactory();

    @Test
    public void createPoll() {
        assertDoesNotThrow(() -> modelFactory.createPoll());
    }

    @Test
    public void createMeasurement() {
        assertDoesNotThrow(() -> modelFactory.createMeasurement());
        assertDoesNotThrow(() -> modelFactory.createMeasurement(1));
        assertDoesNotThrow(() -> modelFactory.createMeasurement(1, null, null, null));
        assertThrows(IllegalArgumentException.class, () -> modelFactory.createMeasurement(null));
        assertThrows(IllegalArgumentException.class, () -> modelFactory.createMeasurement(null, null, null, null));
    }

    @Test
    public void createPerson() {
        assertDoesNotThrow(() -> modelFactory.createPerson());
    }

    @Test
    public void createCriteria() {
        assertDoesNotThrow(() -> modelFactory.createCriteria());
    }

}

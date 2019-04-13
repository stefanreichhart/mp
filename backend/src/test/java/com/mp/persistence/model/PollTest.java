package com.mp.persistence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PollTest {

    private Poll poll;
    private LocalDateTime datetime;

    @BeforeEach
    public void setUp() {
        assertDoesNotThrow(() -> poll = new Poll());
        datetime = LocalDateTime.now();
    }

    @Test
    public void titleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> poll.setTitle(null));
        assertThrows(IllegalArgumentException.class, () -> poll.setTitle(""));
        assertThrows(IllegalArgumentException.class, () -> poll.setTitle(" "));
    }

    @Test
    public void description() {
        assertDoesNotThrow(() -> poll.setDescription(null));
        assertDoesNotThrow(() -> poll.setDescription(""));
        assertDoesNotThrow(() -> poll.setDescription(" "));
    }

    @Test
    public void validFrom() {
        assertDoesNotThrow(() -> poll.setValidFrom(null));
        assertDoesNotThrow(() -> poll.setValidFrom(datetime));
        assertDoesNotThrow(() -> poll.setValidFrom(null));
    }

    @Test
    public void validTo() {
        assertDoesNotThrow(() -> poll.setValidTo(null));
        assertDoesNotThrow(() -> poll.setValidTo(datetime));
        assertDoesNotThrow(() -> poll.setValidTo(null));
    }

    @Test
    public void validToValidFrom() {
        assertDoesNotThrow(() -> poll.setValidTo(null));
        assertDoesNotThrow(() -> poll.setValidFrom(null));
        assertDoesNotThrow(() -> poll.setValidTo(datetime));
        assertDoesNotThrow(() -> poll.setValidFrom(datetime));
        assertDoesNotThrow(() -> poll.setValidTo(null));
        assertDoesNotThrow(() -> poll.setValidFrom(null));
    }

    @Test
    public void validFromValidToThrowsException() {
        assertDoesNotThrow(() -> poll.setValidTo(datetime));
        assertThrows(IllegalArgumentException.class, () -> poll.setValidFrom(datetime.plusSeconds(1)));
    }

    @Test
    public void validToValidFromThrowsException() {
        assertDoesNotThrow(() -> poll.setValidFrom(datetime));
        assertThrows(IllegalArgumentException.class, () -> poll.setValidTo(datetime.minusSeconds(1)));
    }

}

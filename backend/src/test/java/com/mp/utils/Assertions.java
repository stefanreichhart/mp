package com.mp.utils;

import com.mp.persistence.model.Criteria;
import com.mp.persistence.model.Person;
import com.mp.persistence.model.Poll;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public final class Assertions {

    private Assertions() {}

    public static void assertNotEmpty(List list) {
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    public static void assertEmpty(List list) {
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    public static void assertExistingCriteria(Criteria expected, Optional<Criteria> actual) {
        assertTrue(actual.isPresent());
        assertSavedCriteriaEquals(expected, actual.get());
    }

    private static void assertSavedCriteriaEquals(Criteria expected, Criteria actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCreated(), actual.getCreated());
    }

    public static void assertExistingPollEquals(Poll expected, Optional<Poll> actual) {
        assertTrue(actual.isPresent());
        assertSavedPollEquals(expected, actual.get());
    }

    public static void assertSavedPollEquals(Poll expected, Poll actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCreated(), actual.getCreated());
        assertEquals(expected.getValidFrom(), actual.getValidFrom());
        assertEquals(expected.getValidTo(), actual.getValidTo());
    }

    public static void assertSavedPoll(Poll poll) {
        assertNotNull(poll);
        assertNotNull(poll.getId());
        assertNotNull(poll.getCreated());
        assertNotNull(poll.getUpdated());
    }

    public static void assertSavedPersonsEquals(Person expected, Optional<Person> actual) {
        assertSavedPerson(expected);
        assertTrue(actual.isPresent());
        assertSavedPersonsEquals(expected, actual.get());
    }

    public static void assertSavedPersonsEquals(Person expected, Person actual) {
        assertSavedPerson(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCreated(), actual.getCreated());
    }

    public static void assertSavedPerson(Person person) {
        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getCreated());
        assertNotNull(person.getUpdated());

    }

}

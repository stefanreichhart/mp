package com.mp.utils;

import com.mp.persistence.model.Criteria;
import com.mp.persistence.model.Measurement;
import com.mp.persistence.model.Person;
import com.mp.persistence.model.Poll;
import org.junit.platform.commons.util.StringUtils;

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

    public static void assertSavedCriteriaEqualsFoundCriteria(Criteria expected, Optional<Criteria> actual) {
        assertTrue(actual.isPresent());
        assertSavedCriteriaEqualsFoundCriteria(expected, actual.get());
    }

    private static void assertSavedCriteriaEqualsFoundCriteria(Criteria expected, Criteria actual) {
        assertSavedCriteria(expected);
        assertSavedCriteria(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCreated(), actual.getCreated());
    }

    private static void assertSavedCriteria(Criteria criteria) {
        assertNotNull(criteria);
        assertFalse(StringUtils.isBlank(criteria.getName()));
        assertNotNull(criteria.getPoll());
        assertNotNull(criteria.getCreated());
        assertNotNull(criteria.getUpdated());
    }

    public static void assertSavedPollEqualsFoundPoll(Poll expected, Optional<Poll> actual) {
        assertTrue(actual.isPresent());
        assertSavedPollEqualsFoundPoll(expected, actual.get());
    }

    public static void assertSavedPollEqualsFoundPoll(Poll expected, Poll actual) {
        assertSavedPoll(expected);
        assertSavedPoll(actual);
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
        assertNotNull(poll.getTitle());
        assertFalse(StringUtils.isBlank(poll.getTitle()));
        assertNotNull(poll.getCreated());
        assertNotNull(poll.getUpdated());
    }

    public static void assertSavedPersonsEqualsFoundPerson(Person expected, Optional<Person> actual) {
        assertSavedPerson(expected);
        assertTrue(actual.isPresent());
        assertSavedPersonsEqualsFoundPerson(expected, actual.get());
    }

    public static void assertSavedPersonsEqualsFoundPerson(Person expected, Person actual) {
        assertSavedPerson(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCreated(), actual.getCreated());
    }

    public static void assertSavedPerson(Person person) {
        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getName());
        assertFalse(StringUtils.isBlank(person.getName()));
        assertNotNull(person.getCreated());
        assertNotNull(person.getUpdated());

    }

    public static void assertSavedMeasurementEqualsFoundMeasurement(Measurement expected, Optional<Measurement> actual) {
        assertTrue(actual.isPresent());
        assertSavedMeasurementEqualsFoundMeasurement(expected, actual.get());
    }

    public static void assertSavedMeasurementEqualsFoundMeasurement(Measurement expected, Measurement actual) {
        assertSavedMeasurement(expected);
        assertSavedMeasurement(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getRating(), actual.getRating());
        assertEquals(expected.getCreated(), actual.getCreated());
        assertEquals(expected.getPoll(), actual.getPoll());
        assertEquals(expected.getCriteria(), actual.getCriteria());
        assertEquals(expected.getPerson(), actual.getPerson());
    }

    public static void assertSavedMeasurement(Measurement measurement) {
        assertNotNull(measurement);
        assertNotNull(measurement.getId());
        assertNotNull(measurement.getRating());
        assertNotNull(measurement.getPoll());
        assertNotNull(measurement.getCriteria());
        assertNotNull(measurement.getCreated());
        assertNotNull(measurement.getUpdated());
    }

}

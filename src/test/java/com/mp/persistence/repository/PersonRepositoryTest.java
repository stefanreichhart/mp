package com.mp.persistence.repository;

import com.mp.MeasurementPointsLocalApplication;
import com.mp.persistence.PersonPersistence;
import com.mp.persistence.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeasurementPointsLocalApplication.class)
@ActiveProfiles("test")
@Transactional
public class PersonRepositoryTest {

    @Autowired
    private PersonPersistence persistence;

    @Autowired
    private PersonRepository repository;

    @Test
    public void repository() {
        repository.findAll();
        repository.count();
        repository.deleteAll();
    }

    @Test
    public void findAll() {
        List<Person> persons = repository.findAll();
        assertNotNull(persons);
    }

    @Test
    public void find_absent() {
        String name = TestingUtils.getRandomUniqueName();
        Optional<Person> byName = repository.findByName(name);
        assertFalse(byName.isPresent());
    }

    @Test
    public void create() {
        Person savedPerson = persistence.create();
        assertExists(savedPerson);
    }

    @Test
    public void find_create_delete() {
        String name = TestingUtils.getRandomUniqueName();
        Optional<Person> byName = repository.findByName(name);
        assertFalse(byName.isPresent());

        Person savedPerson = persistence.create(name);
        assertExists(savedPerson);

        byName = repository.findByName(name);
        assertFind(savedPerson, byName);

        repository.delete(byName.get());
        denyExists(byName.get());
    }

    @Test
    public void prevent_duplicates() {
        Person person = persistence.create();
        assertExists(person);
        assertThrows(Exception.class, () -> persistence.create(person.getName()));
    }

    public void assertFind(Person expected, Optional<Person> actual) {
        assertNotNull(actual);
        assertTrue(actual.isPresent());
        assertNotNull(actual.get());
        assertEquals(expected, actual.get());
    }

    public void assertExists(Person person) {
        assertNotNull(person);
        Optional<Person> byId = repository.findById(person.getId());
        assertTrue(byId.isPresent());
        assertEquals(person, byId.get());
    }

    public void denyExists(Person person) {
        assertNotNull(person);
        Optional<Person> byId = repository.findById(person.getId());
        assertFalse(byId.isPresent());
    }

}

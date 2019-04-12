package com.mp.persistence.repository;

import com.mp.MeasurementPointsLocalApplication;
import com.mp.persistence.ModelFactory;
import com.mp.persistence.model.Person;
import com.mp.utils.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeasurementPointsLocalApplication.class)
@ActiveProfiles("test")
@Transactional
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelFactory modelFactory;

    @Test
    public void basics() {
        personRepository.findAll();
        personRepository.count();
        personRepository.deleteAll();
    }

    @Test
    public void savePersonThrowsException() {
        assertThrows(DataIntegrityViolationException.class, () -> personRepository.saveAndFlush(new Person()));
    }

    @Test
    public void savePersonWithNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> createAndSavePerson(null));
    }

    @Test
    public void savePersonWithEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> createAndSavePerson(""));
    }

    @Test
    public void savePersonWithBlankNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> createAndSavePerson("    "));
    }

    @Test
    public void savePerson() {
        Person saved = createAndSavePerson("name");
        Assertions.assertSavedPerson(saved);
        assertEquals("name", saved.getName());
    }

    @Test
    public void saveDuplicatePersonThrowsException() {
        Person saved = createAndSavePerson("name");
        Assertions.assertSavedPerson(saved);
        assertThrows(DataIntegrityViolationException.class, () -> createAndSavePerson("name"));
    }

    @Test
    public void findByIdNonExistingPerson() {
        Optional<Person> notFound = personRepository.findById(UUID.randomUUID());
        assertTrue(notFound.isEmpty());
    }

    @Test
    public void findByNameNonExistingPerson() {
        Optional<Person> notFound = personRepository.findByName(UUID.randomUUID().toString());
        assertTrue(notFound.isEmpty());
    }

    @Test
    public void findById() {
        Person saved1 = createAndSavePerson("p1");
        Person saved2 = createAndSavePerson("p2");
        Person saved3 = createAndSavePerson("p3");
        Assertions.assertSavedPersonsEquals(saved1, personRepository.findById(saved1.getId()));
        Assertions.assertSavedPersonsEquals(saved2, personRepository.findById(saved2.getId()));
        Assertions.assertSavedPersonsEquals(saved3, personRepository.findById(saved3.getId()));
    }

    @Test
    public void findByName() {
        Person saved1 = createAndSavePerson("p1");
        Person saved2 = createAndSavePerson("p2");
        Person saved3 = createAndSavePerson("p3");
        Assertions.assertSavedPersonsEquals(saved1, personRepository.findByName(saved1.getName()));
        Assertions.assertSavedPersonsEquals(saved2, personRepository.findByName(saved2.getName()));
        Assertions.assertSavedPersonsEquals(saved3, personRepository.findByName(saved3.getName()));
    }

    private Person createAndSavePerson(String name) {
        Person person = modelFactory.createPerson(name);
        return personRepository.saveAndFlush(person);
    }

}

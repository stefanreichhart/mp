package com.mp.persistence.repository;

import com.mp.MeasurementPointsLocalApplication;
import com.mp.persistence.CriteriaPersistence;
import com.mp.persistence.PersonPersistence;
import com.mp.persistence.model.Criteria;
import com.mp.persistence.model.Measurement;
import com.mp.persistence.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeasurementPointsLocalApplication.class)
@WebAppConfiguration
public class MeasurementRepositoryTest {

    @Autowired
    public MeasurementRepository measurementRepository;

    @Autowired
    public PersonRepository personRepository;

    @Autowired
    public CriteriaRepository criteriaRepository;

    @Autowired
    public PersonPersistence personPersistence;

    @Autowired
    public CriteriaPersistence criteriaPersistence;

    @Test
    public void repository() {
        measurementRepository.findAll();
        measurementRepository.count();
        measurementRepository.deleteAll();
    }

    @Test
    public void findAll() {
        List<Measurement> measurements = measurementRepository.findAll();
        assertNotNull(measurements);
    }

    // TODO broken due to model changes
    @Test
    public void createAndDelete() {
        Person person = new Person();
        person.setName(UUID.randomUUID().toString());
        Person savedPerson = personRepository.save(person);

        Criteria criteria = new Criteria();
        criteria.setName("criteria");
        Criteria savedCriteria = criteriaRepository.save(criteria);

        Measurement measurement = new Measurement();
        measurement.setRating(1);
        measurement.setPerson(person);
        measurement.setCriteria(criteria);

        Measurement saved = measurementRepository.save(measurement);
        Optional<Measurement> found = measurementRepository.findByUuid(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(saved, found.get());

        measurementRepository.deleteAll();
    }
}

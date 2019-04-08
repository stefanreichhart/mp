package com.measurementpointslocal.persistence.repository;

import com.measurementpointslocal.MeasurementPointsLocalApplication;
import com.measurementpointslocal.persistence.CriteriaPersistence;
import com.measurementpointslocal.persistence.PersonPersistence;
import com.measurementpointslocal.persistence.model.Criteria;
import com.measurementpointslocal.persistence.model.Measurement;
import com.measurementpointslocal.persistence.model.Person;
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

    @Test
    public void createAndDelete() {
        Person person = new Person();
        person.setName(UUID.randomUUID().toString());
        Person savedPerson = personRepository.save(person);

        Criteria criteria = new Criteria();
        criteria.setName("criteria");
        Criteria savedCriteria = criteriaRepository.save(criteria);

        Measurement measurement = new Measurement();
        measurement.setComment("comment");
        measurement.setRating(1);
        measurement.setPerson(person);
        measurement.setCriteria(criteria);

        Measurement saved = measurementRepository.save(measurement);
        Optional<Measurement> found = measurementRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(saved, found.get());

        measurementRepository.deleteAll();
    }
}

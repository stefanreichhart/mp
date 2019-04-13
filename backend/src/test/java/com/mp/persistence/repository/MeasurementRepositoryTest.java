package com.mp.persistence.repository;

import com.mp.MeasurementPointsLocalApplication;
import com.mp.persistence.model.Criteria;
import com.mp.persistence.model.Measurement;
import com.mp.persistence.model.Person;
import com.mp.persistence.model.Poll;
import com.mp.persistence.utils.ModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.mp.utils.Assertions.assertNotEmpty;
import static com.mp.utils.Assertions.assertSavedMeasurementEqualsFoundMeasurement;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeasurementPointsLocalApplication.class)
@ActiveProfiles("test")
@Transactional
public class MeasurementRepositoryTest {

    @Autowired
    public PollRepository pollRepository;

    @Autowired
    public MeasurementRepository measurementRepository;

    @Autowired
    public PersonRepository personRepository;

    @Autowired
    public CriteriaRepository criteriaRepository;

    @Autowired
    public ModelFactory modelFactory;

    private Measurement measurement;

    private Poll poll;

    private Criteria criteria;

    private Person person;

    @Test
    public void repository() {
        assertDoesNotThrow(() -> measurementRepository.findAll());
        assertDoesNotThrow(() -> measurementRepository.count());
        assertDoesNotThrow(() -> measurementRepository.deleteAll());
    }

    @BeforeEach
    public void setUp() {
        poll = pollRepository.save(modelFactory.createPoll());
        criteria = modelFactory.createCriteria();
        criteria.setPoll(poll);
        criteria = criteriaRepository.save(criteria);
        person = personRepository.save(modelFactory.createPerson());
    }

    @Test
    public void saveMeasurementThrowsException() {
        assertThrows(DataIntegrityViolationException.class, () -> measurementRepository.saveAndFlush(new Measurement()));
    }

    @Test
    public void saveMeasurementWithInvalidRatingThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> createAndSaveMeasurement(null, poll, criteria, person));
    }

    @Test
    public void saveMeasurementWithInvalidPollThrowsException() {
        assertThrows(DataIntegrityViolationException.class, () -> createAndSaveMeasurement(1, null, criteria, null));
    }

    @Test
    public void saveMeasurementWithInvalidCriteriaThrowsException() {
        assertThrows(DataIntegrityViolationException.class, () -> createAndSaveMeasurement(1, poll, null, null));
    }

    @Test
    public void saveMeasurementWithMultipleInvalidParamsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> createAndSaveMeasurement(null, null, null, null));
        assertThrows(DataIntegrityViolationException.class, () -> createAndSaveMeasurement(1, null, null, null));
    }

    @Test
    public void saveMeasurement() {
        Measurement savedMeasurement = createAndSaveMeasurement(1, poll, criteria, person);
        assertSavedMeasurementEqualsFoundMeasurement(measurement, savedMeasurement);
    }

    @Test
    public void saveMeasurementWithoutPerson() {
        Measurement savedMeasurement = createAndSaveMeasurement(1, poll, criteria, null);
        assertSavedMeasurementEqualsFoundMeasurement(measurement, savedMeasurement);
    }

    @Test
    public void findById() {
        Measurement savedMeasurement = createAndSaveMeasurement(1, poll, criteria, null);
        Optional<Measurement> foundMeasurement = measurementRepository.findById(savedMeasurement.getId());
        assertSavedMeasurementEqualsFoundMeasurement(savedMeasurement, foundMeasurement);
    }

    @Test
    public void findByPoll() {
        Measurement savedMeasurement = createAndSaveMeasurement(1, poll, criteria, null);
        List<Measurement> foundMeasurements = measurementRepository.findByPoll(poll);
        assertNotEmpty(foundMeasurements);
        assertEquals(1, foundMeasurements.size());
        assertSavedMeasurementEqualsFoundMeasurement(savedMeasurement, foundMeasurements.get(0));
    }

    private Measurement createAndSaveMeasurement(Integer rating, Poll poll, Criteria criteria, Person person) {
        measurement = modelFactory.createMeasurement(rating, poll, criteria, person);
        return measurementRepository.saveAndFlush(measurement);
    }

}

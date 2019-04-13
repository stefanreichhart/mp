package com.mp.persistence.repository;

import com.mp.MeasurementPointsLocalApplication;
import com.mp.persistence.CriteriaPersistence;
import com.mp.persistence.PersonPersistence;
import com.mp.persistence.model.Criteria;
import com.mp.persistence.model.Measurement;
import com.mp.persistence.model.Person;
import com.mp.persistence.model.Poll;
import com.mp.persistence.utils.ModelFactory;
import com.mp.utils.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.mp.utils.Assertions.assertNotEmpty;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeasurementPointsLocalApplication.class)
@ActiveProfiles("test")
@Transactional
public class PollAndMeasurementRepositoryScenarioTest {

    @Autowired
    public PollRepository pollRepository;

    @Autowired
    public MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementPersistenceHelper measurementPersistenceHelper;

    @Autowired
    public PersonRepository personRepository;

    @Autowired
    public CriteriaRepository criteriaRepository;

    @Autowired
    public PersonPersistence personPersistence;

    @Autowired
    public CriteriaPersistence criteriaPersistence;

    @Autowired
    public ModelFactory modelFactory;

    Poll poll1, poll2;

    Criteria criteria1, criteria2, criteria3;

    Person person1, person2;

    Measurement measurement1, measurement1From, measurement1To, measurement2;

    LocalDateTime created;

    @BeforeEach
    public void setUp() throws InterruptedException {
        poll1 = pollRepository.save(modelFactory.createPoll());
        poll2 = pollRepository.save(modelFactory.createPoll());
        criteria1 = modelFactory.createCriteria();
        criteria1.setPoll(poll1);
        criteriaRepository.save(criteria1);
        criteria2 = modelFactory.createCriteria();
        criteria2.setPoll(poll1);
        criteriaRepository.save(criteria2);
        criteria3 = modelFactory.createCriteria();
        criteria3.setPoll(poll2);
        criteriaRepository.save(criteria3);
        person1 = personRepository.save(modelFactory.createPerson());
        person2 = null;
        created = LocalDateTime.now();
        sleep(100);
        measurement1 = measurementRepository.saveAndFlush(modelFactory.createMeasurement(1, poll1, criteria1, person1));
        sleep(100);
        measurement1From = measurementRepository.saveAndFlush(modelFactory.createMeasurement(2, poll1, criteria1, null));
        sleep(100);
        measurement1To = measurementRepository.saveAndFlush(modelFactory.createMeasurement(0, poll1, criteria2, person1));
        measurement2 = measurementRepository.saveAndFlush(modelFactory.createMeasurement(3, poll2, criteria3, person1));
    }

    @Test
    public void findById() {
        Optional<Measurement> byUuid1 = measurementRepository.findById(measurement1.getId());
        Assertions.assertSavedMeasurementEqualsFoundMeasurement(measurement1, byUuid1);
    }

    @Test
    public void findByPoll() {
        List<Measurement> measurements1 = measurementRepository.findByPoll(poll1);
        assertNotNull(measurements1);
        assertNotEmpty(measurements1);
        assertEquals(3, measurements1.size());
        assertTrue(measurements1.contains(measurement1));
        assertTrue(measurements1.contains(measurement1From));
        assertTrue(measurements1.contains(measurement1To));
        assertFalse(measurements1.contains(measurement2));

        List<Measurement> measurements2 = measurementRepository.findByPoll(poll2);
        assertNotNull(measurements2);
        assertNotEmpty(measurements2);
        assertEquals(1, measurements2.size());
        assertFalse(measurements2.contains(measurement1));
        assertFalse(measurements2.contains(measurement1From));
        assertFalse(measurements2.contains(measurement1To));
        assertTrue(measurements2.contains(measurement2));
    }

    @Test
    public void findByPollAndCriteriaAndFromAndTo() {
        List<Measurement> results = measurementPersistenceHelper.findByPollAndCriteriaAndFrom(null, null, null, null);
        assertEquals(4, results.size());
        assertTrue(results.contains(measurement1));
        assertTrue(results.contains(measurement1To));
        assertTrue(results.contains(measurement1From));
        assertTrue(results.contains(measurement2));

        results = measurementPersistenceHelper.findByPollAndCriteriaAndFrom(poll1.getId(), null, null, null);
        assertEquals(3, results.size());
        assertTrue(results.contains(measurement1));
        assertTrue(results.contains(measurement1To));
        assertTrue(results.contains(measurement1From));

        results = measurementPersistenceHelper.findByPollAndCriteriaAndFrom(poll1.getId(), criteria1.getId(), null, null);
        assertEquals(2, results.size());
        assertTrue(results.contains(measurement1));
        assertTrue(results.contains(measurement1From));

        results = measurementPersistenceHelper.findByPollAndCriteriaAndFrom(poll1.getId(), criteria2.getId(), null, null);
        assertEquals(1, results.size());
        assertTrue(results.contains(measurement1To));

        results = measurementPersistenceHelper.findByPollAndCriteriaAndFrom(poll2.getId(), null, null, null);
        assertEquals(1, results.size());
        assertTrue(results.contains(measurement2));

        results = measurementPersistenceHelper.findByPollAndCriteriaAndFrom(poll1.getId(), null, created.plus(Duration.ofMillis(199)), null);
        assertEquals(2, results.size());
        assertTrue(results.contains(measurement1From));
        assertTrue(results.contains(measurement1To));

        results = measurementPersistenceHelper.findByPollAndCriteriaAndFrom(poll1.getId(), null, null, created.plus(Duration.ofMillis(199)));
        assertEquals(1, results.size());
        assertTrue(results.contains(measurement1));

        results = measurementPersistenceHelper.findByPollAndCriteriaAndFrom(null, criteria3.getId(), null, null);
        assertEquals(1, results.size());
        assertTrue(results.contains(measurement2));
    }

}

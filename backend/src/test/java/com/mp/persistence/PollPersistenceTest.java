package com.mp.persistence;

import com.mp.MeasurementPointsLocalApplication;
import com.mp.persistence.model.Criteria;
import com.mp.persistence.model.Poll;
import com.mp.persistence.repository.CriteriaRepository;
import com.mp.persistence.repository.PollRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.mp.utils.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeasurementPointsLocalApplication.class)
@ActiveProfiles("test")
@Transactional
public class PollPersistenceTest {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private CriteriaRepository criteriaRepository;

    @Autowired
    private ModelFactory modelFactory;

    private Poll poll, savedPoll;
    private Criteria criteria, savedCriteria;

    @Test
    public void addCriteriaThrowsException() {
        poll = modelFactory.createPoll();
        assertThrows(IllegalArgumentException.class, () -> poll.addCriteria(null));
    }

    @Test
    public void addCriteria() {
        poll = modelFactory.createPoll();
        criteria = modelFactory.createCriteria();
        poll.addCriteria(criteria);
        savedPoll = pollRepository.saveAndFlush(poll);

        assertSavedPoll(savedPoll);
        assertNotEmpty(savedPoll.getCriterias());

        savedCriteria = savedPoll.getCriterias().get(0);
        assertEquals(criteria.getId(), savedCriteria.getId());
        assertSavedPoll(savedCriteria.getPoll());
        assertSavedPollEquals(savedPoll, savedCriteria.getPoll());
    }

    @Test
    public void addDuplicateCriteriaThrowsException() {
        addCriteria();
        Criteria duplicateCriteria = modelFactory.createCriteria(criteria.getName());
        assertThrows(IllegalArgumentException.class, () -> poll.addCriteria(duplicateCriteria));
    }

    @Test
    public void addSameCriteriaTwice() {
        addCriteria();
        savedPoll.addCriteria(savedCriteria);
        savedPoll.addCriteria(criteria);
        savedPoll = pollRepository.saveAndFlush(savedPoll);

        assertSavedPoll(savedPoll);
        assertNotEmpty(savedPoll.getCriterias());
        assertEquals(1, savedPoll.getCriterias().size());
    }

    @Test
    public void addForeignCriteriaThrowsException() {
        addCriteria();

        Poll poll2 = modelFactory.createPoll();
        assertThrows(IllegalArgumentException.class, () -> poll2.addCriteria(criteria));
        assertThrows(IllegalArgumentException.class, () -> poll2.addCriteria(savedCriteria));
    }

    @Test
    public void removeCriteriaThrowsException() {
        poll = modelFactory.createPoll();
        assertThrows(IllegalArgumentException.class, () -> poll.removeCriteria(null));
    }

    @Test
    public void removeCriteria() {
        addCriteria();

        savedPoll.removeCriteria(savedCriteria);
        savedPoll = pollRepository.saveAndFlush(savedPoll);

        assertNotNull(savedPoll.getCriterias());
        assertEmpty(savedPoll.getCriterias());
        assertNull(savedCriteria.getPoll());

        Optional<Criteria> existingCriteria = criteriaRepository.findByUuid(savedCriteria.getId());
        assertTrue(existingCriteria.isEmpty());

        Optional<Poll> existingPoll = pollRepository.findByUuid(savedPoll.getId());
        assertExistingPollEquals(savedPoll, existingPoll);
    }


    @Test
    public void removeDanglingCriteriaDoesNotChangeAnything() {
        poll = modelFactory.createPoll();
        savedPoll = pollRepository.saveAndFlush(poll);

        Criteria danglingCriteria = modelFactory.createCriteria("criteria");
        savedPoll.removeCriteria(danglingCriteria);

        savedPoll = pollRepository.saveAndFlush(poll);
        assertNotNull(savedPoll.getCriterias());
        assertEmpty(savedPoll.getCriterias());
    }

    @Test
    public void removeForeignCriteriaDoesNotChangeAnything() {
        addCriteria();

        Poll poll2 = modelFactory.createPoll();
        poll2.removeCriteria(criteria);
        poll2.removeCriteria(savedCriteria);
        savedPoll = pollRepository.saveAndFlush(poll);
        Poll savedPoll2 = pollRepository.saveAndFlush(poll2);

        assertSavedPoll(savedPoll);
        assertNotEmpty(savedPoll.getCriterias());
        assertSavedPoll(savedPoll2);
        assertEmpty(savedPoll2.getCriterias());

        Optional<Criteria> existingCriteria = criteriaRepository.findByUuid(criteria.getId());
        assertTrue(existingCriteria.isPresent());
        assertSavedPollEquals(savedPoll, existingCriteria.get().getPoll());
    }

}

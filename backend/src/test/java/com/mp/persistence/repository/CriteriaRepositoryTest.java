package com.mp.persistence.repository;

import com.mp.MeasurementPointsLocalApplication;
import com.mp.persistence.model.Criteria;
import com.mp.persistence.model.Poll;
import com.mp.persistence.utils.ModelFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.mp.utils.Assertions.assertEmpty;
import static com.mp.utils.Assertions.assertSavedCriteriaEqualsFoundCriteria;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeasurementPointsLocalApplication.class)
@ActiveProfiles("test")
@Transactional
public class CriteriaRepositoryTest {

    @Autowired
    private CriteriaRepository criteriaRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private ModelFactory modelFactory;

    @Test
    public void repository() {
        assertDoesNotThrow(() -> criteriaRepository.findAll());
        assertDoesNotThrow(() -> criteriaRepository.count());
        assertDoesNotThrow(() -> criteriaRepository.deleteAll());
    }

    @Test
    public void saveCriteriaThrowsException() {
        Criteria criteria = modelFactory.createCriteria();
        assertThrows(DataIntegrityViolationException.class, () -> criteriaRepository.saveAndFlush(criteria));
    }

    @Test
    public void saveCriteriaUsingInvalidTransientRelationshipThrowsException() {
        Poll poll = modelFactory.createPoll();
        Criteria criteria = modelFactory.createCriteria();
        poll.addCriteria(criteria);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> criteriaRepository.saveAndFlush(criteria));
    }

    @Test
    public void saveCriteria() {
        Poll poll = modelFactory.createPoll();
        Criteria criteria = modelFactory.createCriteria();
        poll.addCriteria(criteria);
        assertDoesNotThrow(() -> pollRepository.saveAndFlush(poll));
    }

    @Test
    public void findById() {
        Poll poll4 = createAndSavePollWithCriterias(4);
        assertEquals(4, poll4.getCriterias().size());
        poll4.getCriterias().stream().forEach(criteria -> {
            Optional<Criteria> found = assertDoesNotThrow(() -> criteriaRepository.findById(criteria.getId()));
            assertSavedCriteriaEqualsFoundCriteria(criteria, found);
        });
    }

    @Test
    public void findByPoll() {
        Poll poll0 = createAndSavePollWithCriterias(0);
        List<Criteria> criterias0 = assertDoesNotThrow(() -> criteriaRepository.findByPoll(poll0));
        assertEmpty(criterias0);
        Poll poll2 = createAndSavePollWithCriterias(2);
        Poll poll3 = createAndSavePollWithCriterias(3);
        List<Criteria> criterias2 = assertDoesNotThrow(() -> criteriaRepository.findByPoll(poll2));
        assertEquals(2, criterias2.size());
        List<Criteria> criterias3 = assertDoesNotThrow(() -> criteriaRepository.findByPoll(poll3));
        assertEquals(3, criterias3.size());
    }

    private Poll createAndSavePollWithCriterias(int count) {
        Poll poll = assertDoesNotThrow(() -> modelFactory.createPoll());
        assertEquals(0, poll.getCriterias().size());
        for (int i = 1; i <= count; i++) {
            Criteria criteria = assertDoesNotThrow(() -> modelFactory.createCriteria());
            assertDoesNotThrow(() -> poll.addCriteria(criteria));
        }
        assertEquals(count, poll.getCriterias().size());
        return assertDoesNotThrow(() -> pollRepository.saveAndFlush(poll));
    }

}

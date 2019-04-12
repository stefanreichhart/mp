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
import static com.mp.utils.Assertions.assertExistingCriteria;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        criteriaRepository.findAll();
        criteriaRepository.count();
        criteriaRepository.deleteAll();
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
        //pollRepository.saveAndFlush(poll);
    }

    @Test
    public void saveCriteria() {
        Poll poll = modelFactory.createPoll();
        Criteria criteria = modelFactory.createCriteria();
        poll.addCriteria(criteria);
        pollRepository.saveAndFlush(poll);
    }

    private Poll createAndSavePollWithCriterias(int count) {
        Poll poll = modelFactory.createPoll();
        assertEquals(0, poll.getCriterias().size());
        for (int i = 1; i <= count; i++) {
            Criteria criteria = modelFactory.createCriteria();
            poll.addCriteria(criteria);
        }
        assertEquals(count, poll.getCriterias().size());
        return pollRepository.saveAndFlush(poll);
    }

    @Test
    public void findById() {
        Poll poll4 = createAndSavePollWithCriterias(4);
        assertEquals(4, poll4.getCriterias().size());
        poll4.getCriterias().stream().forEach(criteria -> {
            Optional<Criteria> found = criteriaRepository.findByUuid(criteria.getId());
            assertExistingCriteria(criteria, found);
        });
    }

    @Test
    public void findByPoll() {
        Poll poll0 = createAndSavePollWithCriterias(0);
        List<Criteria> criterias0 = criteriaRepository.findByPoll(poll0);
        assertEmpty(criterias0);
        Poll poll2 = createAndSavePollWithCriterias(2);
        Poll poll3 = createAndSavePollWithCriterias(3);
        List<Criteria> criterias2 = criteriaRepository.findByPoll(poll2);
        assertEquals(2, criterias2.size());
        List<Criteria> criterias3 = criteriaRepository.findByPoll(poll3);
        assertEquals(3, criterias3.size());
    }

}

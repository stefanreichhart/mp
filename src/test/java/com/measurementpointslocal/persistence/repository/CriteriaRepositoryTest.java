package com.measurementpointslocal.persistence.repository;

import com.measurementpointslocal.MeasurementPointsLocalApplication;
import com.measurementpointslocal.persistence.CriteriaPersistence;
import com.measurementpointslocal.persistence.model.Criteria;
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
public class CriteriaRepositoryTest {

    @Autowired
    private CriteriaRepository repository;

    @Autowired
    private CriteriaPersistence persistence;

    @Test
    public void repository() {
        repository.findAll();
        repository.count();
        repository.deleteAll();
    }

    @Test
    public void findAll() {
        List<Criteria> criterias = repository.findAll();
        assertNotNull(criterias);
    }

    @Test
    public void find_absent() {
        String name = TestingUtils.getRandomUniqueName();
        Optional<Criteria> byName = repository.findByName(name);
        assertFalse(byName.isPresent());
    }

    @Test
    public void create() {
        Criteria saved = persistence.create(TestingUtils.getRandomUniqueName());
        assertNotNull(saved);
        assertNotNull(saved.getId());
        Optional<Criteria> byId = repository.findById(saved.getId());
        assertTrue(byId.isPresent());
        assertEquals(saved, byId.get());
    }

    @Test
    public void prevent_duplicates() {
        Criteria saved1 = persistence.create(TestingUtils.getRandomUniqueName());
        assertThrows(Exception.class, () -> persistence.create(saved1.getName()));
    }

}

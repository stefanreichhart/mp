package com.mp.persistence;

import com.mp.persistence.model.Criteria;
import com.mp.persistence.repository.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CriteriaPersistence {

    @Autowired
    private CriteriaRepository repository;

    public Criteria create(String name) {
        Criteria newCriteria = new Criteria();
        newCriteria.setName(name);
        return repository.save(newCriteria);
    }

}

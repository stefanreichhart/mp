package com.mp.persistence;

import com.mp.persistence.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonPersistence {

    @Autowired
    private PersonRepository personRepository;

}

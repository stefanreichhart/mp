package com.measurementpointslocal.persistence;

import com.measurementpointslocal.persistence.model.Person;
import com.measurementpointslocal.persistence.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class PersonPersistence {

    @Autowired
    private PersonRepository personRepository;

    public Person create(String name) {
        Objects.requireNonNull(name, "Person name must not be null");
        Person person = new Person();
        person.setName(name);
        return personRepository.save(person);
    }

    public Person create() {
        return this.create(UUID.randomUUID().toString());
    }

}

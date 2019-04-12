package com.mp.persistence.utils;

import com.google.common.annotations.VisibleForTesting;
import com.mp.persistence.model.Criteria;
import com.mp.persistence.model.Person;
import com.mp.persistence.model.Poll;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ModelFactory {

    public Poll createPoll(String title, String description, LocalDateTime validFrom, LocalDateTime validTo) {
        Poll poll = new Poll();
        poll.setTitle(title);
        poll.setDescription(description);
        poll.setValidFrom(validFrom);
        poll.setValidTo(validTo);
        return poll;
    }

    @VisibleForTesting
    public Poll createPoll() {
        return this.createPoll(UUID.randomUUID().toString(), null, null, null);
    }

    public Person createPerson(String name) {
        Person person = new Person();
        person.setName(name);
        return person;
    }

    @VisibleForTesting
    private Person createPerson() {
        return this.createPerson(UUID.randomUUID().toString());
    }

    public Criteria createCriteria(String name) {
        Criteria criteria = new Criteria();
        criteria.setName(name);
        return criteria;
    }

    @VisibleForTesting
    public Criteria createCriteria() {
        return this.createCriteria(UUID.randomUUID().toString());
    }

}

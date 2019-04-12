package com.mp.persistence;

import com.mp.persistence.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PollPersistence {

    @Autowired
    PollRepository pollRepository;

}

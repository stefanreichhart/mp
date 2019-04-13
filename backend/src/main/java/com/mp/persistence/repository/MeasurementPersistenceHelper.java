package com.mp.persistence.repository;

import com.mp.persistence.model.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class MeasurementPersistenceHelper {

    @Autowired
    EntityManager entityManager;

    public List<Measurement> findByPollAndCriteriaAndFrom(UUID poll, UUID criteria, LocalDateTime from, LocalDateTime to) {
        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT m FROM Measurement m WHERE 1 = 1 ");
        if (poll != null) {
            builder.append(" AND m.poll.id = :poll");
        }
        if (criteria != null) {
            builder.append(" AND m.criteria.id = :criteria");
        }
        if (from != null) {
            builder.append(" AND m.created >= :from ");
        }
        if (to != null) {
            builder.append(" AND m.created <= :to ");
        }
        final TypedQuery<Measurement> query = entityManager.createQuery(builder.toString(), Measurement.class);
        if (poll != null) {
            query.setParameter("poll", poll);
        }
        if (criteria != null) {
            query.setParameter("criteria", criteria);
        }
        if (from != null) {
            query.setParameter("from", from);
        }
        if (to != null) {
            query.setParameter("to", to);
        }
        return query.getResultList();
    }

}

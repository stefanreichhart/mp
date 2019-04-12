package com.mp.persistence.repository;

import com.mp.persistence.model.Criteria;
import com.mp.persistence.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, UUID> {

    @Query("SELECT c FROM Criteria c WHERE c.id = :id")
    Optional<Criteria> findByUuid(UUID id);

    @Query("Delete FROM Criteria c WHERE c.poll.id = :id")
    void deleteByPollUuid(UUID id);

    @Query("SELECT c FROM Criteria c WHERE c.poll = :poll")
    List<Criteria> findByPoll(Poll poll);

}

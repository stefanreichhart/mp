package com.mp.persistence.repository;

import com.mp.persistence.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PollRepository extends JpaRepository<Poll, UUID> {

    @Query("DELETE FROM Poll p WHERE p.id = :id")
    void deleteByUuid(UUID id);

}

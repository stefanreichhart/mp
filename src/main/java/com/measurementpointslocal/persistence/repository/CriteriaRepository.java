package com.measurementpointslocal.persistence.repository;

import com.measurementpointslocal.persistence.model.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Long> {

    @Query("SELECT c FROM Criteria c WHERE c.name = :name")
    Optional<Criteria> findByName(String name);

}

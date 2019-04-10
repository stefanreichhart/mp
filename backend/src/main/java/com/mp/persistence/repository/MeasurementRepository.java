package com.mp.persistence.repository;

import com.mp.persistence.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    Optional<Measurement> findById(Long id);

    @Query("SELECT m FROM Measurement m WHERE (m.created >= :from) AND (m.created <= :to)")
    List<Measurement> findFromTo(LocalDateTime from, LocalDateTime to);

    @Query("SELECT m FROM Measurement m WHERE m.created >= :from")
    List<Measurement> findFrom(LocalDateTime from);

    @Query("SELECT m FROM Measurement m WHERE m.created <= :to")
    List<Measurement> findTo(LocalDateTime to);

}

package com.mp.persistence.repository;

import com.mp.api.dto.TableRowDto;
import com.mp.persistence.model.Measurement;
import com.mp.persistence.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    @Query("DELETE FROM Measurement m WHERE m.poll.id = :id")
    void deleteByPollUuid(UUID id);

    @Query("DELETE FROM Measurement m WHERE m.id IN ( :ids )")
    void deleteByIds(List<UUID> ids);

    @Query("SELECT new com.mp.api.dto.TableRowDto(TO_CHAR(m.created, 'yyyy-mm-dd'), m.criteria.name, AVG(m.rating)) FROM Measurement m WHERE m.poll.id = :id GROUP BY TO_CHAR(m.created, 'yyyy-mm-dd'), m.criteria.name ORDER BY TO_CHAR(m.created, 'yyyy-mm-dd')")
    List<TableRowDto> getData(UUID id);

    @Query("SELECT m FROM Measurement m WHERE m.poll = :poll")
    List<Measurement> findByPoll(Poll poll);

}

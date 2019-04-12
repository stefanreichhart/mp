package com.mp.persistence.repository;

import com.mp.persistence.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    @Query("SELECT p FROM Person p WHERE p.id = :id")
    Optional<Person> findByUuid(UUID id);

    @Query("SELECT p FROM Person p WHERE p.name = :name")
    Optional<Person> findByName(String name);

}

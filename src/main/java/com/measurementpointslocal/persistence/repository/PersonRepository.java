package com.measurementpointslocal.persistence.repository;

import com.measurementpointslocal.persistence.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p from Person p WHERE p.name = :name")
    Optional<Person> findByName(String name);

}

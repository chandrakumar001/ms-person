package com.chandrakumar.ms.api.person.repository;

import com.chandrakumar.ms.api.person.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    Optional<Person> findByEmailId(final String emailId);
}

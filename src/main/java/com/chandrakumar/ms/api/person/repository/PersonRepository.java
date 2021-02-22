package com.chandrakumar.ms.api.person.repository;

import com.chandrakumar.ms.api.person.entity.Person;
import com.chandrakumar.ms.api.person.util.PersonConstant;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.chandrakumar.ms.api.person.util.PersonConstant.*;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    @Query("select p from Person p where p.action <> 'DELETED'")
    List<Person> findAll();

    @Query("select p from Person p where p.emailId=:emailId and p.action <> 'DELETED'")
    Optional<Person> findByEmailId(@Param(EMAIL_ID) @NonNull final String emailId);

    @Query("select p from Person p where p.personId=:personId and p.action <> 'DELETED'")
    Optional<Person> findByPersonId(@Param(PERSON_ID) @NonNull final UUID personId);

    @Query("update Person p set p.lastModifiedBy=:lastModifiedBy, p.lastModifiedDate=:lastModifiedDate, p.action='DELETED' where p.personId=:personId")
    @Modifying
    void softDeleteByPersonId(
            @Param(PERSON_ID) @NonNull final UUID personId,
            @Param(LAST_MODIFIED_DATE) @NonNull final Date updatedDate,
            @Param(LAST_MODIFIED_BY) @NonNull final String updatedBy
    );
}

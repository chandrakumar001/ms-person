package com.chandrakumar.ms.api.person.entity;

import com.chandrakumar.ms.api.common.audit.Action;
import com.chandrakumar.ms.api.common.audit.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "person", schema = "person")
@Data
public class Person extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID personId;
    private String emailId;
    @Embedded
    private PersonName personName;
    private String age;
    private String favouriteColour;

    @Enumerated(STRING)
    private Action action;
}

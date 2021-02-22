package com.chandrakumar.ms.api.person.entity;

import com.chandrakumar.ms.api.common.audit.Action;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "person", schema = "person")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID personId;
    private String emailId;
    @Embedded
    private PersonName personName;
    private String age;
    private String favouriteColour;

    @CreatedBy
    protected String createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date creationDate;

    @LastModifiedBy
    protected String lastModifiedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastModifiedDate;

    @Enumerated(STRING)
    private Action action;

    @PreRemove
    public void preRemove() {
        this.setAction(Action.DELETED);
    }
}

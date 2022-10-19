package com.udacity.jdnd.course3.critter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor

@Entity
//polymorphic queries should be allowed but shared fields are not required to be on child classes
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
    @Id
    @GeneratedValue
    private Long id;

    @Nationalized
    private String name;



}

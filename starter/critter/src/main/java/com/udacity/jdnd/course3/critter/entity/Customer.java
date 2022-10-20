package com.udacity.jdnd.course3.critter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String name;

    @Nationalized
    private String phoneNumber;

    @Nationalized
    private String notes;

    @OneToMany(mappedBy = "owner")
    private List<Pet> pets;

    @ManyToMany(mappedBy = "owners")
    private List<Schedule> schedules;




}

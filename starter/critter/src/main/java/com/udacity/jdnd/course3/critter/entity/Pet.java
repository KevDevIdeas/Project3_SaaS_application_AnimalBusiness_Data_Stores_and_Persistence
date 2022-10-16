package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.helper.PetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Pet {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated (EnumType.STRING)
    private PetType type;

    @Nationalized
    private String name;

    @Nationalized
    private String notes;

    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Customer owner;

    @ManyToMany(mappedBy = "pets")
    private List<Schedule> schedules;

}

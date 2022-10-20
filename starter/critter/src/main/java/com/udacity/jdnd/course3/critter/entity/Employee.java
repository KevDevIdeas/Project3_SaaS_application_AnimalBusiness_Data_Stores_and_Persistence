package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.helper.EmployeeSkill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String name;

    @ElementCollection
    private Set<EmployeeSkill> skills;


    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(mappedBy = "employees")
    private List<Schedule> schedules;


}

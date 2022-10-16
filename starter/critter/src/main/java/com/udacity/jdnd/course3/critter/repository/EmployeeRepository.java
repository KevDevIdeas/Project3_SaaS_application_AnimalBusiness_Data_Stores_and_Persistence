package com.udacity.jdnd.course3.critter.repository;


import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.helper.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
@EnableJpaRepositories
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

   /*@Query("SELECT DISTINCT e FROM Employee e " +
            "INNER JOIN e.daysAvailable daysAvailable" +
            "INNER JOIN e.skills skills " +
            "WHERE e.daysAvailable = :dayOfWeek " +
            "AND (skills in (:skills))")
    List<Employee> findEmployeeWithSkillToDate(DayOfWeek dayOfWeek, Set<EmployeeSkill> skills);

    --> The above query did not work and I ended up with the error "Parameter value [TUESDAY] did not match expected type [java.util.Set (n/a)]". INNER JOIN statements most probably not required

    */


    @Query
    List<Employee> findDistinctByDaysAvailableAndSkillsIn(DayOfWeek dayOfWeek, Set<EmployeeSkill> skills);




}

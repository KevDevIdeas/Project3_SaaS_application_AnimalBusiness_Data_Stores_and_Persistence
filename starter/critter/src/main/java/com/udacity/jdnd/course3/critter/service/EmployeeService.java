package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.helper.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository repository;

    public Employee saveEmployee(Employee employee){
        return repository.save(employee);
    }

    public Employee getEmployeeById(Long id){
        Optional<Employee> optionalEmployee = repository.findById(id);
        Employee employee = optionalEmployee.get();
        return employee;
    }

    public void updateEmployeeAvailability(Long employeeId, Set<DayOfWeek> daysAvailable){

        // retrieve full employee from Database
        Optional<Employee> optionalEmployeeFromDb = repository.findById(employeeId);
        Employee employeeToBeUpdated = optionalEmployeeFromDb.get();

        //set the Availability
        employeeToBeUpdated.setDaysAvailable(daysAvailable);

        // save the employee again into the database
        repository.save(employeeToBeUpdated);
    }

    public List<Employee> getEmployeeForDate (LocalDate date,Set<EmployeeSkill> skills){

        //transform provided date to it's weekDay to use for querying the employees daysAvailable
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        //retrieve List of employees that are available at the given date and provide at least one of the required skill sets
        List <Employee> potentialCandidates = repository.findDistinctByDaysAvailableAndSkillsIn(dayOfWeek, skills);

        //Creating a subset from the potentialCandidates which includes only the employees that have all the required skills
        List<Employee> candidatesWithFullSkillSet = new ArrayList<>();
        for (Employee employee : potentialCandidates) {
            if (employee.getSkills().containsAll(skills)) {
                candidatesWithFullSkillSet.add(employee);
            }
        }
        return candidatesWithFullSkillSet;
    }


}

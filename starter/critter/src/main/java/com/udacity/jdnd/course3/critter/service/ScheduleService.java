package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository repository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PetRepository petRepository;

    public Schedule saveSchedule (Schedule schedule){
        Schedule savedSchedule = repository.save(schedule);

        //setting the saved schedule to the employees and updating them.
        //1st List employeees on schedule and next loop through the list and do for each employee on the list the same
        // getting the pre-existing schedule as employeeSchedules - if non exists create a new ArrayList.
        // add to the employeeSchedules the newly saved schedule and set the new list of schedules to the employee
        List<Employee> employees = savedSchedule.getEmployees();
        for (Employee employee : employees){
            List<Schedule> employeeSchedules = employee.getSchedules();
            if(employeeSchedules == null){
                employeeSchedules = new ArrayList<>();
            }
            employeeSchedules.add(savedSchedule);
            employee.setSchedules(employeeSchedules);

            // update the employee with the newly added schedule through the save() and having an employeeId on the object
            employeeRepository.save(employee);
        }

        //same for Pets
        List<Pet> pets = savedSchedule.getPets();
        for (Pet pet : pets){
            List<Schedule> petSchedules = pet.getSchedules();
            if(petSchedules == null){
                petSchedules = new ArrayList<>();
            }
            petSchedules.add(savedSchedule);
            pet.setSchedules(petSchedules);

            // update the employee with the newly added schedule through the save() and having an employeeId on the object
            petRepository.save(pet);
        }


        return savedSchedule;
    }

    public List<Schedule> getAllSchedules(){
        Schedule schedule= repository.findAll().get(0);
        return repository.findAll(); }
}

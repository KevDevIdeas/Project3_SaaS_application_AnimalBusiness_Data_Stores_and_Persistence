package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.helper.EmployeeSkill;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    PetService petService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule savedSchedule = scheduleService.saveSchedule(convertDTOToSchedule(scheduleDTO));
        return convertScheduleToDTO(savedSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List <Schedule> schedules = scheduleService.getAllSchedules();

        return convertEntityListToDTOList(schedules);

    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        List<Schedule> petSchedules = pet.getSchedules();

        return convertEntityListToDTOList(petSchedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        List<Schedule> employeeSchedule = employee.getSchedules();

        return convertEntityListToDTOList(employeeSchedule);

    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        //since the customer has a schedule via their pets first all the pets are fetched
        Customer customer = customerService.getCustomerById(customerId);
        List<Pet> petsOfCustomer = petService.getPetByOwner(customer.getId());

        //the result is a list of pets. To get all the schedule lists for all pets the stream map function is used
        //resulting in a list of the listed schedules from the pets
        List<List<Schedule>> listList = petsOfCustomer.stream().map((pet -> pet.getSchedules())).collect(Collectors.toList());

        //here the list of list is flatten into one list of schedules which is at the end converted to the DTO view.
        List <Schedule> customerSchedule = listList.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return convertEntityListToDTOList(customerSchedule);
    }

    //Entity to DTO conversion and vice versa

    private static ScheduleDTO convertScheduleToDTO (Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        //will not set the employeeIds and petIds - only id, activities and date are the same from DTO and entity class
        BeanUtils.copyProperties(schedule, scheduleDTO);

        //retrieving the employees from the schedules and set their ids
        List<Employee> employeesOnSchedule = schedule.getEmployees();
        scheduleDTO.setEmployeeIds(employeesOnSchedule.stream().map(employee -> employee.getId()).collect(Collectors.toList()));


        //retrieving the pets from the schedules and set their ids
        List<Pet> petsOnSchedule = schedule.getPets();
        scheduleDTO.setPetIds(petsOnSchedule.stream().map(pet -> pet.getId()).collect(Collectors.toList()));

        return scheduleDTO;
    }

    private static Schedule convertDTOToSchedule (ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        // will not set the employeeIds, petIds into the employees and pets
        //because in the entity the fields are nested in the object lists employees and pets and hence no 1to1 mapping from naming
        BeanUtils.copyProperties(scheduleDTO, schedule);

        //setting employeeId to the entity by constructing a list of employee objects which the entity holds
        //looping through the list and setting FOR EACH employee object an id
        List<Employee> employees = new ArrayList<>();
        List<Long> employeesId = new ArrayList<>(scheduleDTO.getEmployeeIds());

        // since the employees list ist still empty - the employeesId list has to be looped trhough
        //Next for each employeeId an empty employee object is created and added to the employees List.
        //thirdly, the Id form the employeesId is set in the list of employee object called employees
        for (Long employeeId : employeesId) {
            employees.add(new Employee());

            for (Employee employee : employees) {
                employee.setId(employeeId);
            }
        }
        schedule.setEmployees(employees);

        // doing the same for pets
        List<Pet> pets = new ArrayList<>();
        List<Long> petsId = new ArrayList<>(scheduleDTO.getPetIds());
        for (Long petId : petsId) {
            pets.add(new Pet());

            for (Pet pet : pets) {
                pet.setId(petId);
            }
        }
        schedule.setPets(pets);

        return schedule;
    }

    private static List<ScheduleDTO> convertEntityListToDTOList(List<Schedule> scheduleList){
        return scheduleList.stream()
                .map(schedule -> convertScheduleToDTO(schedule))
                .collect(Collectors.toList());
    }

}

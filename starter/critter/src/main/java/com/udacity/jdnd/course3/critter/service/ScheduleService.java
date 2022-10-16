package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository repository;

    public Schedule saveSchedule (Schedule schedule){
        return repository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        Schedule schedule= repository.findAll().get(0);
        return repository.findAll(); }
}

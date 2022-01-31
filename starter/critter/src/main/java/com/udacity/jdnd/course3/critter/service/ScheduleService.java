package com.udacity.jdnd.course3.critter.service;

import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;

@Service
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private ScheduleRepository scheduleRepository;

    public ScheduleDTO saveDTO(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule(
                scheduleDTO.getDate(),
                scheduleDTO.getActivities()
                );

        scheduleRepository.save(schedule);
        scheduleDTO.setId(schedule.getId());

        return scheduleDTO;
    }



}

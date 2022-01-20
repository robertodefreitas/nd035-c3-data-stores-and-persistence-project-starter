package com.udacity.jdnd.course3.critter.service;

import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;

public class ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private ScheduleRepository scheduleRepository;

}

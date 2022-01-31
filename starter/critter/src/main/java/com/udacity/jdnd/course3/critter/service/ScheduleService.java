package com.udacity.jdnd.course3.critter.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.model.user.Employee;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.user.EmployeeService;

@Service
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    public ScheduleDTO saveDTO(ScheduleDTO scheduleDTO){
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();

        //List<Long>:DTO -> List<Employee>:Entity
        List<Employee> employeeList = new ArrayList<>();
        List<Pet> petList = new ArrayList<>();

        if (!scheduleDTO.getEmployeeIds().isEmpty()){
            logger.info("[{}] List<Long>: Employee IDs is not empty. SIZE:{}",
                    methodeName,
                    scheduleDTO.getEmployeeIds().size());
            EmployeeDTO employeeDTO = employeeService.findEmployeeById(scheduleDTO.getEmployeeIds().get(0));
            employeeList.add(employeeService.convertEmployeeDTO2Employee(employeeDTO));
        }

        if (!scheduleDTO.getPetIds().isEmpty()){
            logger.info("[{}] List<Long>: Pet IDs is not empty. SIZE:{}",
                    methodeName,
                    scheduleDTO.getPetIds().size());
            PetDTO petDTO = petService.findPetById(scheduleDTO.getPetIds().get(0));
            petList.add(petService.convertPetDTO2Pet(petDTO));
        }

        Schedule schedule = new Schedule(
                employeeList,
                petList,
                scheduleDTO.getDate(),
                scheduleDTO.getActivities()
                );

        scheduleRepository.save(schedule);
        scheduleDTO.setId(schedule.getId());

        return scheduleDTO;
    }



}

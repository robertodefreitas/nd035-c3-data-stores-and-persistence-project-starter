package com.udacity.jdnd.course3.critter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.jdnd.course3.critter.dao.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.model.user.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;


    public Long save(Employee employee) {
        // save (CrudRepository) needs an entity like Employee, EmployeeDTO is not an entity
        employeeRepository.save(employee);

        // we need to give the id to EmployeeDTO
        // also change this methode from void to Long
        return employee.getId();
    }


    public EmployeeDTO findEmployeeById(Long employeeId) {
        // get the Object Employee from Option
        Employee employeeResult = employeeRepository.findById(employeeId).get();

        return convertEmployee2EmployeeDTO(employeeResult);
    }


    public EmployeeDTO convertEmployee2EmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());

        return employeeDTO;
    }
}



package com.udacity.jdnd.course3.critter.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.jdnd.course3.critter.dao.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.model.user.Employee;
import com.udacity.jdnd.course3.critter.repository.user.EmployeeRepository;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;


    public EmployeeDTO saveDTO(EmployeeDTO employeeDTO) {

        // method save (CrudRepository) needs an entity like Employee.
        // EmployeeDTO is not an entity
        Employee employee = new Employee(
                employeeDTO.getName(),
                employeeDTO.getSkills(),
                employeeDTO.getDaysAvailable()
        );

        // save (CrudRepository) needs an entity like Employee, EmployeeDTO is not an entity
        employeeRepository.save(employee);

        employeeDTO.setId(employee.getId());

        return employeeDTO;
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



package com.udacity.jdnd.course3.critter.service.user;

import java.time.DayOfWeek;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
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
        employeeRepository.save(employee);

        // we need to give the id to EmployeeDTO
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

    public Employee convertEmployeeDTO2Employee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();

        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());

        return employee;
    }


    public void setEmployeeAvailabilityById(Set<DayOfWeek> daysAvailable, Long employeeId){
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();

        //EmployeeDTO employeeDTO = findEmployeeById(employeeId);
        Employee resultEmployee = employeeRepository.findById(employeeId).get();
        logger.info("[{}] Employe ID: {}, Days Available: {}", methodeName, employeeId, resultEmployee.getDaysAvailable());

        resultEmployee.setDaysAvailable(daysAvailable);
        logger.info("[{}] Employe ID: {}, Days Available: {}", methodeName, employeeId, resultEmployee.getDaysAvailable());
        employeeRepository.save(resultEmployee);
    }
}



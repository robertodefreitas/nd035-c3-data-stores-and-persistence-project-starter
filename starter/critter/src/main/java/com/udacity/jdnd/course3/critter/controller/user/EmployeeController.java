package com.udacity.jdnd.course3.critter.controller.user;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.service.user.EmployeeService;

/**
 * Handles web requests related to Users
 *
 * UserController.java is split
 * to CustomerController and EmployeeController
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    /**
     * POST /user/employee
     */
    /* BODY raw:JSON
    {
      "name": "Hannah",
      "skills": ["PETTING", "FEEDING"],
      "daysAvailable": ["MONDAY", "FRIDAY", "SATURDAY"]
    }
    */
    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] POST /user/employee", methodeName);

        return employeeService.saveDTO(employeeDTO);
    }


    /**
     * POST /user/employee/1
     */
    /* BODY raw:JSON
    EMPTY
    */
    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable Long employeeId) {
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] POST /user/employee/{}", methodeName, employeeId);

        return employeeService.findEmployeeById(employeeId);
    }


    /**
     * PUT /user/employee/1
     */
    /* BODY raw:JSON
    ["MONDAY", "TUESDAY", "FRIDAY"]
    */
    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] PUT /user/employee/{}", methodeName, employeeId);

        employeeService.setEmployeeAvailabilityById(daysAvailable,employeeId);
    }


    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] GET /user/employee/availability", methodeName);

        return employeeService.findEmployeesForService(employeeRequestDTO);
        //throw new UnsupportedOperationException();
    }

}

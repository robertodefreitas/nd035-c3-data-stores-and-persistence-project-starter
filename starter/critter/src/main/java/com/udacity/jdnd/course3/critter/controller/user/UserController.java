package com.udacity.jdnd.course3.critter.controller.user;

import com.udacity.jdnd.course3.critter.dao.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.dao.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dao.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.model.user.Customer;
import com.udacity.jdnd.course3.critter.model.user.Employee;
import com.udacity.jdnd.course3.critter.service.user.CustomerService;
import com.udacity.jdnd.course3.critter.service.user.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;


    // this doesn't work, we work with "new className"
    //@Autowired
    //private Customer customer;

    /**
     * POST /user/customer
     */
    /* BODY raw:JSON
    {
      "name": "Aleex",
      "phoneNumber": "1234567890",
      "notes": "this is a note!"
    }
    */
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] POST /user/customer", methodeName);

        // method save (CrudRepository) needs an entity like Customer, CustomerDTO is not an entity
        Customer customer = new Customer(
                customerDTO.getName(),
                customerDTO.getPhoneNumber(),
                customerDTO.getNotes()
        );

        // we need to give the id to CustomerDTO
        // also change this methode from void to Long
        //customerService.save(customer);
        Long idDTO = customerService.save(customer);
        customerDTO.setId(idDTO);

        // instead of return we can use ...
        // throw new UnsupportedOperationException();
        // But we need here a return because of the testCreateCustomer
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] GET /user/customer", methodeName);

        // instead of return we can use ...
        // throw new UnsupportedOperationException();
        // But we need here a return because of the testCreateCustomer
        return customerService.findAllCustomers();
    }


    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){

        throw new UnsupportedOperationException();
    }


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

        // method save (CrudRepository) needs an entity like Employee.
        // EmployeeDTO is not an entity
        Employee employee = new Employee(
                employeeDTO.getName(),
                employeeDTO.getSkills(),
                employeeDTO.getDaysAvailable()
        );

        Long idDTO = employeeService.save(employee);
        employeeDTO.setId(idDTO);

        return employeeDTO;
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

        throw new UnsupportedOperationException();
    }


    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] GET /user/employee/availability", methodeName);

        throw new UnsupportedOperationException();
    }

}

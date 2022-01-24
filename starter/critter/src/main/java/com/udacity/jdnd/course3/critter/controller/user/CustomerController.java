package com.udacity.jdnd.course3.critter.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.udacity.jdnd.course3.critter.dao.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.service.user.CustomerService;

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
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

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

        // instead of return we can use ...
        // throw new UnsupportedOperationException();
        // But we need here a return because of the testCreateCustomer
        return customerService.saveDTO(customerDTO);
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
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] GET /user/customer/pet/{}", methodeName, petId);

        /**
         * TO-DO
         */

        throw new UnsupportedOperationException();
    }

}

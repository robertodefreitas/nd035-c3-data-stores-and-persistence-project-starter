package com.udacity.jdnd.course3.critter.repository.user;

import org.springframework.data.repository.CrudRepository;
import com.udacity.jdnd.course3.critter.model.user.Customer;

/**
 * I use the know-how from lesson 3 in project 3 (Part 14 / Spring Data JPA)
 * Now we can simply inject this interface into our service classes and use it:
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    // here is not necessary content, because we use this direct by CustomerService
    // Object.save(Customer) or Object.findById(Long)
}
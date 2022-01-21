package com.udacity.jdnd.course3.critter.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.jdnd.course3.critter.dao.PetDTO;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.user.Customer;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.user.CustomerService;

@Service
public class PetService {

    private static final Logger logger = LoggerFactory.getLogger(PetService.class);

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerService customerService;


    public PetDTO saveDTO(PetDTO petDTO) {
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();

        // because of the object Customer as OwnerId on the model (entity)
        // we need to convert petDTO.getOwnerId() on a Customer Object
        Optional<Customer> customerOptional = customerService.findCustomerById(petDTO.getOwnerId());

        // if customer null then new Customer() -> orElse works here!
        //Customer customer = customerOptional.orElse(new Customer());
        Customer customer;

        if ( customerOptional.isPresent() ) {
            logger.info("[{}] Optional<Customer> is present. [CUSTOMER_ID: {}]", methodeName, petDTO.getOwnerId());
            customer = customerOptional.get();
        } else {
            logger.info("[{}] customer is empty. [CUSTOMER_ID: {}]", methodeName, petDTO.getOwnerId());
            //customer = new Customer();

            // do something if the customerId/ownerId is not available.
            // maybe create a new Customer with a default ID e.g. 1
            logger.info("[{}] default customer is used. [CUSTOMER_ID: {}]", methodeName, 1);
            customerOptional = customerService.findCustomerById(1L);
            customer = customerOptional.get();
        }

        // method save (CrudRepository) needs an entity like Pet.
        // PetDTO is not an entity
        Pet pet = new Pet(
                petDTO.getType(),
                petDTO.getName(),
                customer,
                petDTO.getBirthDate(),
                petDTO.getNotes()
        );
        petRepository.save(pet);

        // we need to give the id to PetDTO
        petDTO.setId(pet.getId());

        return petDTO;
    }

}

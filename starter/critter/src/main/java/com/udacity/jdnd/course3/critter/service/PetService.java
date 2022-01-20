package com.udacity.jdnd.course3.critter.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.udacity.jdnd.course3.critter.dao.PetDTO;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.user.Customer;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.user.CustomerService;

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
        Customer customer = customerOptional.orElse(new Customer());
        logger.info("[{}] Customer is founded or new created.", methodeName);

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


        /*
        try {
            //Customer customer = customerOptional.get();
            // https://www.geeksforgeeks.org/optional-orelse-method-in-java-with-examples/
            // orElse method accepts value as a parameter of type T to return
            // if there is no value present in this Optional instance.
            Customer customer = customerOptional.orElse(new Customer());
            logger.info("[{}] Customer is founded or new created.", methodeName);

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
        catch (Exception e) {
            logger.error("[{}] exception by finding of the customer.", methodeName, e);
            return null;
        }
        */
    }

}

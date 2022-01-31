package com.udacity.jdnd.course3.critter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
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

        // Part 2 from 3 to resolve the issue
        // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        // In saveDTO check if the getOwnerId of the petDTO is not null,
        // if not we will get the Customer through the id
        Optional<Customer> customerOptional = Optional.empty();
        if (petDTO.getOwnerId() != null){
            customerOptional = customerService.findCustomerById(petDTO.getOwnerId());
        }


        // because of the object Customer as OwnerId on the model (entity)
        // we need to convert petDTO.getOwnerId() on a Customer Object
        //Optional<Customer> customerOptional = customerService.findCustomerById(petDTO.getOwnerId());

        // if customer null then select the default Customer ID=1 because of orElse -> this works!
        //Customer customer = customerOptional.orElse(customerService.findCustomerById(1L).get());
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
//        petRepository.save(pet);
//        // we need to give the id to PetDTO
//        petDTO.setId(pet.getId());
//        return petDTO;

        // Part 2 from 3 to resolve the issue by CritterFunctionalTest.java
        // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        // In saveDTO ensure that Pet and Customer are in-sync and for that,
        // after saving Pet we will store it in the savedPet variable and
        // if Customer is not null we add Pet to Customer through addPet method
        Pet savedPet = petRepository.save(pet);
        customer.addPet(savedPet);
        return convertPet2PetDTO(savedPet);
    }


    public PetDTO findPetById(Long petId){
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] search pet by ID: {}", methodeName, petId);

        Pet petResult = petRepository.findById(petId).get();

        return convertPet2PetDTO(petResult);
    }


    public PetDTO convertPet2PetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();

        // Part 2 from 3 to resolve the issue by CritterFunctionalTest.java
        // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        // In convertPet2PetDTO we put a check in which if the Pet id is not null we put it into PetDTO
        if (pet.getOwnerId() != null){
            petDTO.setOwnerId(pet.getOwnerId().getId());
        }

        petDTO.setId(pet.getId());
        petDTO.setType(pet.getType());
        petDTO.setName(pet.getName());
        petDTO.setOwnerId(pet.getOwnerId().getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(petDTO.getNotes());

        return petDTO;
    }

    /**
     * A possibility is to get the pets through the model/entity Customer
     * the object Pet is by Customer available
     */
    public List<PetDTO> findPetsByCustomer(Long ownerId){
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] get list of Pets from Customer ID: {}", methodeName, ownerId);

        Optional<Customer> optionalCustomer = customerService.findCustomerById(ownerId);


//        if ( optionalCustomer.isPresent() ) {
//            logger.info("[{}] Optional<Customer> is avalable ID: {}", methodeName, ownerId);
//        } else {
//            logger.info("[{}] Optional<Customer> is empty ID: {}", methodeName, ownerId);
//        }
//        Customer customer = optionalCustomer.get();
//        List<Pet> listPet = customer.getPetIds();


        // Part 2 from 3 to resolve the issue by CritterFunctionalTest.java
        // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        // In the findPetsByCustomer more specifically in the if, we get the Customer and
        // store in the listPet the Pets referring to that Customer if the Customer is not null,
        // if it is null we store in the listPet an empty ArrayList
        List<Pet> listPet;
        if ( optionalCustomer.isPresent() ) {
            logger.info("[{}] Optional<Customer> is avalable ID: {}", methodeName, ownerId);
            Customer customer = optionalCustomer.get();
            listPet = customer.getPetIds();
        } else {
            logger.info("[{}] Optional<Customer> is empty ID: {}", methodeName, ownerId);
            listPet = new ArrayList<>();
        }


        return convertListPet2PetDTOelegant(listPet);
    }


    // in the old days
    // elegant methode see below: convertPet2PetDTOelegant
    public List<PetDTO> convertListPet2PetDTO(List<Pet> listPet){
        List<PetDTO> listPetDTO =  new ArrayList<>();

        for (Pet pet: listPet) {
            PetDTO petDTO = new PetDTO();

            petDTO.setId(pet.getId());
            petDTO.setType(pet.getType());
            petDTO.setName(pet.getName());
            petDTO.setOwnerId(pet.getOwnerId().getId());
            petDTO.setBirthDate(pet.getBirthDate());
            petDTO.setNotes(pet.getNotes());

            listPetDTO.add(petDTO);
        }

        return listPetDTO;
    }


    // Nice way / elegant
    public List<PetDTO> convertListPet2PetDTOelegant(List<Pet> listPet){

        // https://stackoverflow.com/questions/40035102/how-to-convert-a-list-with-propetties-to-a-another-list-the-java-8-way
        List<PetDTO> listPetDTO =  listPet
                .stream()
                .map(pet -> new PetDTO(
                        pet.getId(),
                        pet.getType(),
                        pet.getName(),
                        pet.getOwnerId().getId(),
                        pet.getBirthDate(),
                        pet.getNotes()))
                .collect(Collectors.toList());

        return listPetDTO;
    }
}

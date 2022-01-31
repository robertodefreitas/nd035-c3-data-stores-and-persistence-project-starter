package com.udacity.jdnd.course3.critter.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.user.Customer;
import com.udacity.jdnd.course3.critter.repository.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.service.PetService;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetService petService;


    public CustomerDTO saveDTO(CustomerDTO customerDTO) {

        // method save (CrudRepository) needs an entity like Customer.
        // CustomerDTO is not an entity
        Customer customer = new Customer(
                customerDTO.getName(),
                customerDTO.getPhoneNumber(),
                customerDTO.getNotes()
        );
        customerRepository.save(customer);

        // we need to give the id to CustomerDTO
        customerDTO.setId(customer.getId());

        return customerDTO;
    }


    // methode findAll() return needs: Iterable<T> findAll(); and not List<T>
    public List<CustomerDTO> findAllCustomers(){
        /**
         * TO-DO 1: we need to convert an Iterator to a List
         * because UserController needs a List<CustomerDTO>
         * SOLUTION: use <artifactId>guava</artifactId>
         * https://www.baeldung.com/java-iterable-to-collection
         *
         * TO-DO 2: we need to convert Customer to CustomerDTO
         * SOLUTION: create methode convertCustomer2CustomerDTO()
         */
        List<Customer> listResult = Lists.newArrayList(customerRepository.findAll());

        //return convertListCustomer2CustomerDTO(listResult);
        return convertListCustomer2CustomerDTOelegant(listResult);
    }


//    // in the old days
//    // elegant methode see below: convertListCustomer2CustomerDTOelegant
//    public List<CustomerDTO> convertCustomer2CustomerDTO(List<Customer> listCustomer){
//        List<CustomerDTO> listCustomerDTO =  new ArrayList<>();
//
//        for (Customer customer: listCustomer) {
//            CustomerDTO customerDTO = new CustomerDTO();
//
//            customerDTO.setId(customer.getId());
//            customerDTO.setName(customer.getName());
//            customerDTO.setPhoneNumber(customer.getPhoneNumber());
//
//            listCustomerDTO.add(customerDTO);
//        }
//
//        return listCustomerDTO;
//    }
//
//    // Nice way / elegant
//    public List<CustomerDTO> convertListCustomer2CustomerDTOelegant(List<Customer> listCustomer){
//
//        // https://stackoverflow.com/questions/40035102/how-to-convert-a-list-with-properties-to-a-another-list-the-java-8-way
//        List<CustomerDTO> listCustomerDTO =  listCustomer
//                .stream()
//                .map(customer -> new CustomerDTO(
//                        customer.getId(),
//                        customer.getName(),
//                        customer.getPhoneNumber(),
//                        customer.getNotes()))
//                .collect(Collectors.toList());
//
//        return listCustomerDTO;
//    }


    // Part 3 from 3 to resolve the issue by CritterFunctionalTest.java
    // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
    // Change the convertCustomer2CustomerDTO to convert a Customer instead of a List<Customer>,
    // in which we will map the data from the Customer to the CustomerDTO,
    // considering that in case the list of Pets obtained through the Customer is not null,
    // we will get the id of each Pet and we will add it to the petIds list and
    // we will add the petIds list to the CustomerDTO returning it
    public CustomerDTO convertCustomer2CustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());

        List<Pet> listPet = customer.getPetIds();

        if(listPet != null){
            List<Long> petIds = new ArrayList<>();
            for (Pet pet : listPet){
                petIds.add(pet.getId());
            }
            customerDTO.setPetIds(petIds);
        }

        return customerDTO;
    }


    // Part 3 from 3 to resolve the issue by CritterFunctionalTest.java
    // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
    public List<CustomerDTO> convertListCustomer2CustomerDTOelegant(List<Customer> listCustomer){
        return listCustomer
                .stream()
                .map(this::convertCustomer2CustomerDTO)
                .collect(Collectors.toList());
    }


    // I need this method because of PetService, to get/generate a Customer/Owner from id
    // FYI: Optional<T> findById(ID id); -> also return Optional<Customer>
    public Optional<Customer> findCustomerById(Long ownerId){
        return customerRepository.findById(ownerId);
    }



    /**
     * A possibility is to get the customers through the model/entity Pet
     * the object customer is by Pet available
     */
    public CustomerDTO findCustomerByPet(Long petId){
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] get list of Customers from Pet ID: {}", methodeName, petId);

        PetDTO petFounded = petService.findPetById(petId);
        Optional<Customer> optionalCustomer = findCustomerById(petFounded.getOwnerId());

        logger.info("[{}] founded Customer ID: {}", methodeName, optionalCustomer.get().getId());

        return convertCustomer2CustomerDTO(optionalCustomer.get());
    }

}

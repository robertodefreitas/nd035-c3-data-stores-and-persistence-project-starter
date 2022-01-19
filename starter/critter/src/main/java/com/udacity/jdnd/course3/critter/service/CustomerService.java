package com.udacity.jdnd.course3.critter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.jdnd.course3.critter.dao.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.model.user.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;


    //public void save(Customer customer) {
    public Long save(Customer customer) {
        // save (CrudRepository) needs an entity like Customer, CustomerDTO is not an entity
        customerRepository.save(customer);

        // we need to give the id to CustomerDTO
        // also change this methode from void to Long
        return customer.getId();
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

        //return convertCustomer2CustomerDTO(listResult);
        return convertCustomer2CustomerDTOelegant(listResult);
    }


    // in the old days
    // elegant methode see below: convertCustomer2CustomerDTOelegant
    public List<CustomerDTO> convertCustomer2CustomerDTO(List<Customer> listCustomer){
        List<CustomerDTO> listCustomerDTO =  new ArrayList<>();

        for (Customer customer: listCustomer) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setPhoneNumber(customer.getPhoneNumber());
            listCustomerDTO.add(customerDTO);
        }

        return listCustomerDTO;
    }

    // Nice way / elegant
    public List<CustomerDTO> convertCustomer2CustomerDTOelegant(List<Customer> listCustomer){

        // https://stackoverflow.com/questions/40035102/how-to-convert-a-list-with-properties-to-a-another-list-the-java-8-way
        List<CustomerDTO> listCustomerDTO =  listCustomer
                .stream()
                .map(customer -> new CustomerDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhoneNumber(),
                        customer.getNotes()))
                .collect(Collectors.toList());

        return listCustomerDTO;
    }

}

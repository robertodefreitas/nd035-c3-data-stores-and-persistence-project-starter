package com.udacity.jdnd.course3.critter.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Nationalized;
import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Views;

@Entity
public class Customer {

    @Id
    // https://stackoverflow.com/questions/10041938/how-to-choose-the-id-generation-strategy-when-using-jpa-and-hibernate
    // https://blog.eyallupu.com/2011/01/hibernatejpa-identity-generators.html
    // https://docs.oracle.com/javaee/5/api/javax/persistence/SequenceGenerator.html
    @SequenceGenerator(name="seq-gen-customer", sequenceName="SEQ_GEN_CUSTOMER", initialValue=1, allocationSize=10)
    @GeneratedValue(strategy=GenerationType.TABLE, generator="seq-gen-customer")
    private Long id;

    @JsonView(Views.Public.class)
    @Nationalized
    private String name;

    // https://stackoverflow.com/questions/10631960/validate-mobile-number-using-hibernate-annotation
    @Column(name = "phone_number", length = 16)
    private String phoneNumber;

    @Nationalized
    private String notes;

    // used reference: Project 3, Lesson 4 (Part 16)
    // added CascadeType.REMOVE to automatically clear any associated Pet when removed
    // see: private Customer ownerId; in Class Pet
    // this doesn't create a column on the table customer
    @OneToMany(fetch = FetchType.LAZY,
               cascade = CascadeType.ALL,
               mappedBy = "ownerId")
    private List<Pet> petIds;


    /* constructor */

    /**
     * ISSUE
     *   c.u.j.c.c.controller.UserController      : [getAllCustomers] GET /user/customer
     *   org.hibernate.InstantiationException: No default constructor for entity:  : com.udacity.jdnd.course3.critter.model.user.Customer
     * SOLUTION
     *   empty constructor s. below
     * FYI
     *   This Error was not available by the UnitTest: testCreateCustomer
     */
    public Customer(){}

    /**
     * constructor to push the data from the file CustomerDTO.java, used by UserController
     */
    public Customer(String name, String phoneNumber, String notes) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        // we need to create a ArrayList because of error with unitTest testAddPetsToCustomer
        // java.lang.NullPointerException: Cannot invoke "java.util.List.stream()" because "listPet" is null (PetService)
        this.petIds = new ArrayList<Pet>();
    }


    /* getters and setters */

    // java.lang.NullPointerException: Cannot invoke "java.lang.Long.longValue()" because "this.id" is null
    // intellijIDEA created the methode with long instead of Long
    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Pet> petIds) {
        this.petIds = petIds;
    }
}

package com.udacity.jdnd.course3.critter.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Nationalized;
import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.list.PetType;
import com.udacity.jdnd.course3.critter.model.user.Customer;

@Entity
public class Pet {

    @Id
    // https://stackoverflow.com/questions/10041938/how-to-choose-the-id-generation-strategy-when-using-jpa-and-hibernate
    // https://blog.eyallupu.com/2011/01/hibernatejpa-identity-generators.html
    // https://docs.oracle.com/javaee/5/api/javax/persistence/SequenceGenerator.html
    @SequenceGenerator(name="seq-gen-pet", sequenceName="SEQ_GEN_PET", initialValue=1, allocationSize=10)
    @GeneratedValue(strategy= GenerationType.TABLE, generator="seq-gen-pet")
    private Long id;

    @JsonView(Views.Public.class)
    @Nationalized
    private String name;

    // only one value, also only a column is necessary
    // https://www.baeldung.com/jpa-persisting-enums-in-jpa
    @Enumerated(EnumType.STRING)
    private PetType type;

    private LocalDate birthDate;

    @Nationalized
    private String notes;

    // used reference: Project 3, Lesson 4 (Part 16)
    //don't retrieve customer if we don't need it
    // see: private List<Pet> petIds; in Class Customer
    // this create a column on the table pet with the name id_customer
    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer ownerId;


    /* constructor */

    public Pet(){}

    public Pet(
            PetType type,
            String name,
            Customer ownerId,
            LocalDate birthDate,
            String notes) {
        this.type = type;
        this.name = name;
        this.ownerId = ownerId;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    /* getters and setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Customer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Customer ownerId) {
        this.ownerId = ownerId;
    }
}

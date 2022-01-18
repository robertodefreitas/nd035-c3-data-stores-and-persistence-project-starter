package com.udacity.jdnd.course3.critter.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Nationalized;
import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.list.PetType;
import com.udacity.jdnd.course3.critter.model.user.Customer;

@Entity
public class Pet {

    @Id
    @GeneratedValue
    private Long id;

    @JsonView(Views.Public.class)
    @Nationalized
    private String name;

    @JsonView(Views.Public.class)
    @Nationalized
    private PetType type;

    private LocalDate birthDate;

    @Nationalized
    private String notes;

    //don't retrieve customer if we don't need it
    // see: private List<Pet> petIds; in Class Customer
    // this create a column on the table pet with the name id_customer
    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer ownerId;


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

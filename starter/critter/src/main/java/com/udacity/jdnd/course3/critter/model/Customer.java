package com.udacity.jdnd.course3.critter.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Nationalized;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private long id;

    @Nationalized
    private String name;
    private String phoneNumber;
    private String notes;
    private List<Long> petIds;

}

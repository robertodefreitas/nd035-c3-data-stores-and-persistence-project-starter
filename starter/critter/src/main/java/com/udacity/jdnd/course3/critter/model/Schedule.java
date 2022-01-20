package com.udacity.jdnd.course3.critter.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.udacity.jdnd.course3.critter.list.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.user.Employee;

@Entity
public class Schedule {

    @Id
    // https://stackoverflow.com/questions/10041938/how-to-choose-the-id-generation-strategy-when-using-jpa-and-hibernate
    // https://blog.eyallupu.com/2011/01/hibernatejpa-identity-generators.html
    // https://docs.oracle.com/javaee/5/api/javax/persistence/SequenceGenerator.html
    @SequenceGenerator(name="seq-gen-schedule", sequenceName="SEQ_GEN_SCHEDULE", initialValue=1, allocationSize=10)
    @GeneratedValue(strategy= GenerationType.TABLE, generator="seq-gen-schedule")
    private long id;

    // used reference: Project 3, Lesson 4 (Part 16)
    // added CascadeType.REMOVE to automatically clear any associated Employee when removed
    // see: private Schedule scheduleId; in Class Employee
    // this doesn't create a column on the table schedule
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "scheduleId")
    private List<Employee> employeeIds;

    // used reference: Project 3, Lesson 4 (Part 16)
    // added CascadeType.REMOVE to automatically clear any associated Pet when removed
    // see: private Schedule scheduleId; in Class Pet
    // this doesn't create a column on the table schedule
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "scheduleId")
    private List<Pet> petIds;

    private LocalDate date;

    // https://www.baeldung.com/jpa-persisting-enums-in-jpa
    @Enumerated(EnumType.STRING)
    // Attribute [com.udacity.jdnd.course3.critter.model.Schedule.activities] was annotated as enumerated, but its java type is not an enum [java.util.Set]
    //private Set<EmployeeSkill> activities;
    private EmployeeSkill activities;


    /* getters and setters */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Employee> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Employee> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Pet> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Pet> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public EmployeeSkill getActivities() {
        return activities;
    }

    public void setActivities(EmployeeSkill activities) {
        this.activities = activities;
    }
}

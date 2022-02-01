package com.udacity.jdnd.course3.critter.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
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
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    //@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "schedule_employee", joinColumns = @JoinColumn(name = "id_schedule"), inverseJoinColumns = @JoinColumn(name = "ids_employee"))
    private List<Employee> employeeIds;

//    // org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing
//    // https://stackoverflow.com/questions/2302802/how-to-fix-the-hibernate-object-references-an-unsaved-transient-instance-save
//    @ManyToMany(fetch = FetchType.LAZY) // doesn'T work -> save the transient instance before flushing
//    // Solution -> cascade = CascadeType.ALL , s. below
//    @JoinTable(name = "schedule_pet", joinColumns = @JoinColumn(name = "id_schedule"), inverseJoinColumns = @JoinColumn(name = "ids_pet"))
//    private List<Pet> petIds;

    // This doesn't work like employeeIds, by employeeIds sprimg-boot save the ids into a new table schedule_employee
    // but it doesn't create new rows by the entity Employee -> OK/GOOD/RIGHT
    // spring-boot adds new rows to the entity Pet -> this is not the expected result -> NOK/BAD/WRONG
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "schedule_pet", joinColumns = @JoinColumn(name = "id_schedule"), inverseJoinColumns = @JoinColumn(name = "ids_pet"))
    private List<Pet> petIds;

    private LocalDate date;

    // extra table is nescessary, because of more skills value for the same employee
    // know-how from project 3 lesson 2 (Part 11, Entity Relationships)
    // https://stackoverflow.com/questions/15998824/mapping-setenum-using-elementcollection
    @ElementCollection(targetClass = EmployeeSkill.class)
    @JoinTable(name = "schedule_skills", joinColumns = @JoinColumn(name = "id_schedule"))
    @Column(name ="skills_activities", nullable = false)
    // https://www.baeldung.com/jpa-persisting-enums-in-jpa
    @Enumerated(EnumType.STRING)
    // Attribute [com.udacity.jdnd.course3.critter.model.Schedule.activities] was annotated as enumerated, but its java type is not an enum [java.util.Set]
    private Set<EmployeeSkill> activities;
    //private EmployeeSkill activities;


    /* constructor */

    public Schedule() {
    }

    public Schedule(
            LocalDate date,
            Set<EmployeeSkill> activities) {
        this.employeeIds = new ArrayList<Employee>();
        this.petIds = new ArrayList<Pet>();
        this.date = date;
        this.activities = activities;
    }

    public Schedule(
            List<Employee> employeeIds,
            List<Pet> petIds,
            LocalDate date,
            Set<EmployeeSkill> activities) {
        this.employeeIds = employeeIds;
        this.petIds = petIds;
        //this.petIds = new ArrayList<Pet>();
        this.date = date;
        this.activities = activities;
    }

    /* getters and setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}

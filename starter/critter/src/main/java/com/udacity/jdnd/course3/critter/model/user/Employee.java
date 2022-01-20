package com.udacity.jdnd.course3.critter.model.user;

import java.time.DayOfWeek;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Nationalized;
import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.list.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.model.Views;

@Entity
public class Employee {

    @Id
    // https://stackoverflow.com/questions/10041938/how-to-choose-the-id-generation-strategy-when-using-jpa-and-hibernate
    // https://blog.eyallupu.com/2011/01/hibernatejpa-identity-generators.html
    // https://docs.oracle.com/javaee/5/api/javax/persistence/SequenceGenerator.html
    @SequenceGenerator(name="seq-gen", sequenceName="SEQ_GEN_EMPLOYEE", initialValue=1, allocationSize=10)
    @GeneratedValue(strategy=GenerationType.TABLE, generator="seq-gen")
    private long id;

    @JsonView(Views.Public.class)
    @Nationalized
    private String name;


    // extra table is nescessary, because of more skills value for the same employee
    // know-how from project 3 lesson 2 (Part 11, Entity Relationships)
    // https://stackoverflow.com/questions/15998824/mapping-setenum-using-elementcollection
    @ElementCollection(targetClass = EmployeeSkill.class)
    @JoinTable(name = "employee_skills", joinColumns = @JoinColumn(name = "id_employee"))
    @Column(name ="employeeSkill", nullable = false)
    // https://www.baeldung.com/jpa-persisting-enums-in-jpa
    @Enumerated(EnumType.STRING)
    // https://stackoverflow.com/questions/44258541/illegal-attempt-to-map-a-non-collection-as-a-onetomany-manytomany-or-collec
    private Set<EmployeeSkill> skills;
    //private EmployeeSkill skills;


    // extra table is nescessary, because of more day of week value for the same employee
    // know-how from project 3 lesson 2 (Part 11, Entity Relationships)
    // https://stackoverflow.com/questions/15998824/mapping-setenum-using-elementcollection
    @ElementCollection(targetClass = DayOfWeek.class)
    @JoinTable(name = "employee_dayofweek", joinColumns = @JoinColumn(name = "id_employee"))
    @Column(name ="DayOfWeek", nullable = false)
    // https://www.baeldung.com/jpa-persisting-enums-in-jpa
    @Enumerated(EnumType.STRING)
    // https://stackoverflow.com/questions/44258541/illegal-attempt-to-map-a-non-collection-as-a-onetomany-manytomany-or-collec
    private Set<DayOfWeek> daysAvailable;
    //private DayOfWeek daysAvailable;


    // used reference: Project 3, Lesson 4 (Part 16)
    //don't retrieve schedule if we don't need it
    // see: private List<Employee> employeeIds; in Class Schedule
    // this create a column on the table pet with the name id_schedule
    @ManyToOne
    @JoinColumn(name = "id_schedule")
    private Schedule scheduleId;


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
    public Employee(){}

    /**
     * constructor to push the data from the file CustomerDTO.java, used by UserController
     */
    public Employee(String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysOfWeek) {
        this.name = name;
        this.skills = skills;
        this.daysAvailable = daysOfWeek;
    }


    /* getters and setters */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public Schedule getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Schedule scheduleId) {
        this.scheduleId = scheduleId;
    }

}

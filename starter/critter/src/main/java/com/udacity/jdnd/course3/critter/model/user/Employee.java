package com.udacity.jdnd.course3.critter.model.user;

import java.time.DayOfWeek;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Nationalized;
import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.list.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.model.Views;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private long id;

    @JsonView(Views.Public.class)
    @Nationalized
    private String name;

    // https://www.baeldung.com/jpa-persisting-enums-in-jpa
    @Enumerated(EnumType.STRING)
    //private Set<EmployeeSkill> skills;
    private EmployeeSkill skills;

    // https://www.baeldung.com/jpa-persisting-enums-in-jpa
    @Enumerated(EnumType.STRING)
    // Attribute [com.udacity.jdnd.course3.critter.model.user.Employee.daysAvailable] was annotated as enumerated, but its java type is not an enum [java.util.Set]
    //private Set<DayOfWeek> daysAvailable;
    private DayOfWeek daysAvailable;

    // used reference: Project 3, Lesson 4 (Part 16)
    //don't retrieve schedule if we don't need it
    // see: private List<Employee> employeeIds; in Class Schedule
    // this create a column on the table pet with the name id_schedule
    @ManyToOne
    @JoinColumn(name = "id_schedule")
    private Schedule scheduleId;


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

    public EmployeeSkill getSkills() {
        return skills;
    }

    public void setSkills(EmployeeSkill skills) {
        this.skills = skills;
    }

    public DayOfWeek getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(DayOfWeek daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public Schedule getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Schedule scheduleId) {
        this.scheduleId = scheduleId;
    }
}

package com.udacity.jdnd.course3.critter.dto.user;

import java.time.LocalDate;
import java.util.Set;

import com.udacity.jdnd.course3.critter.list.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.user.Employee;

/**
 * Represents a request to find available employees by skills. Does not map
 * to the database directly.
 */
public class EmployeeRequestDTO {
    private LocalDate date;
    private Set<EmployeeSkill> skills;

    /* constructor */


    /* getters and setters */

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }


}

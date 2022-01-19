package com.udacity.jdnd.course3.critter.dao.user;

import java.time.DayOfWeek;
import java.util.Set;

import com.udacity.jdnd.course3.critter.list.EmployeeSkill;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
/* POST BODY:raw JSON
{
  "name": "Hannah",
  "skills": ["PETTING", "FEEDING"],
  "daysAvailable": ["MONDAY", "FRIDAY", "SATURDAY"]
}
*/

public class EmployeeDTO {
    private long id;
    private String name;
    // https://docs.oracle.com/javase/8/docs/api/java/util/Set.html
    // https://www.tutorialspoint.com/java/java_set_interface.htm
    private Set<EmployeeSkill> skills;
    private Set<DayOfWeek> daysAvailable;


    /* constructor */

    public EmployeeDTO(){}

    public EmployeeDTO(String name, Set<EmployeeSkill> skills) {
        this.name = name;
        this.skills = skills;
    }

    public EmployeeDTO(long id, String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.daysAvailable = daysAvailable;
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
}

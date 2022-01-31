package com.udacity.jdnd.course3.critter.dto;

import java.time.LocalDate;

import com.udacity.jdnd.course3.critter.list.PetType;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
/* POST BODY raw:JSON
{
  "type": "CAT",
  "name": "Kilo",
  "birthDate": "2019-12-16T04:43:57.995Z",
  "notes": "HI KILO"
, "ownerId": "1"
}
*/

public class PetDTO {
    private long id;
    private PetType type;
    private String name;
    private Long ownerId;
    private LocalDate birthDate;
    private String notes;


    /* constructor */

    public PetDTO(){}

    public PetDTO(long id, PetType type, String name, Long ownerId, LocalDate birthDate, String notes) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.ownerId = ownerId;
        this.birthDate = birthDate;
        this.notes = notes;
    }


    /* getters and setters */

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

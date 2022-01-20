package com.udacity.jdnd.course3.critter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.udacity.jdnd.course3.critter.dao.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetService;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private static final Logger logger = LoggerFactory.getLogger(PetController.class);

    PetService petService;

    /**
     * POST /pet
     */
    /* BODY raw:JSON
    {
      "type": "CAT",
      "name": "Kilo",
      "birthDate": "2019-12-16T04:43:57.995Z",
      "notes": "HI KILO"
    , "ownerId": "1"
    }
    */
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] POST /pet", methodeName);

        //PetDTO resultPetDTO = petService.saveDTO(petDTO);
        //return petDTO;

        return petService.saveDTO(petDTO);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        throw new UnsupportedOperationException();
    }
}

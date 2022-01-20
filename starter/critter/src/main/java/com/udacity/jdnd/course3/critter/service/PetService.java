package com.udacity.jdnd.course3.critter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.udacity.jdnd.course3.critter.repository.PetRepository;

public class PetService {

    private static final Logger logger = LoggerFactory.getLogger(PetService.class);

    @Autowired
    private PetRepository petRepository;


}

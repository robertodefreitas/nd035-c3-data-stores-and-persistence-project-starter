package com.udacity.jdnd.course3.critter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dummy controller class to verify installation success. Do not use for
 * your project work.
 */
@RestController
public class CritterTestController {

    private static final Logger logger = LoggerFactory.getLogger(CritterTestController.class);

    @GetMapping("/test")
    public String test(){
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] GET /test", methodeName);

        return "Critter Starter installed successfully";
    }
}

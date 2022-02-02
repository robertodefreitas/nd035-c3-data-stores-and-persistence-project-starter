package com.udacity.jdnd.course3.critter.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.model.user.Employee;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.user.CustomerService;
import com.udacity.jdnd.course3.critter.service.user.EmployeeService;

@Service
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    public ScheduleDTO saveDTO(ScheduleDTO scheduleDTO){
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();

        List<Long> listEmployeeIds = scheduleDTO.getEmployeeIds();
        List<Long> listPetIds = scheduleDTO.getPetIds();

        //DTO:List<Long> -> Entity:List<Employee>
        List<Employee> employeeList = new ArrayList<>();
        List<Pet> petList = new ArrayList<>();


        if (!listEmployeeIds.isEmpty()){
            logger.info("[{}] List<Long>: Number of Employee IDs into Body (employeeIds): {}",
                    methodeName,
                    listEmployeeIds.size());

            /**
             * https://stackoverflow.com/questions/62340699/fetch-all-objects-from-list-by-list-of-ids-using-java-8
             * List<A> list2;
             * Set<String> set = new HashSet<String>(codeList);
             * List<A> res = list2.stream()
             *         .filter(e -> set.contains(e.getCode())
             *                 .collect(Collectors.toList());
             */

            // check all ids into the list
            while( !listEmployeeIds.isEmpty() ) {
                Long id = listEmployeeIds.get(0) ;
                listEmployeeIds = listEmployeeIds.subList(1,listEmployeeIds.size());
                logger.info("[{}] List<Long>: Employee ID: {}", methodeName, id);

                EmployeeDTO employeeDTO = employeeService.findEmployeeById(id);
                logger.info("[{}] findEmployeeById(id) ID: {}, NAME: {}", methodeName, id, employeeDTO.getName());
                if (employeeDTO != null) {
                    logger.info("[{}] NOT NULL ID: {}, {}", methodeName, id, employeeDTO.getName());
                    employeeList.add(employeeService.convertEmployeeDTO2Employee(employeeDTO));
                }
            }

        }


        if (!listPetIds.isEmpty()){
            logger.info("[{}] List<Long>: Number of Pet IDs into Body (petIds): {}",
                    methodeName,
                    listPetIds.size());

            // check all ids into the list
            while( !listPetIds.isEmpty() ) {
                Long id = listPetIds.get(0) ;
                listPetIds = listPetIds.subList(1,listPetIds.size());
                logger.info("[{}] List<Long>: Pet ID: {}", methodeName, id);

                PetDTO petDTO = petService.findPetById(id);
                logger.info("[{}] findPetById(id) ID: {}, NAME: {}, NOTES: {}", methodeName, id, petDTO.getName(), petDTO.getNotes());
                if (petDTO != null) {
                    logger.info("[{}] NOT NULL ID: {}, NAME: {}", methodeName, id, petDTO.getName());
                    petList.add(petService.convertPetDTO2Pet(petDTO));
                }
            }
        }


        Schedule schedule = new Schedule(
                employeeList,
                petList,
                scheduleDTO.getDate(),
                scheduleDTO.getActivities()
                );

        scheduleRepository.save(schedule);
        scheduleDTO.setId(schedule.getId());

        return scheduleDTO;
    }

    // methode findAll() return needs: Iterable<T> findAll(); and not List<T>
    public List<ScheduleDTO> findAllSchedules(){
        /**
         * TO-DO 1: we need to convert an Iterator to a List
         * because ScheduleController needs a List<ScheduleDTO>
         * SOLUTION: use <artifactId>guava</artifactId>
         * Lists.newArrayList(...)
         * https://www.baeldung.com/java-iterable-to-collection
         */
        List<Schedule> listResult = Lists.newArrayList(scheduleRepository.findAll());

        /**
         * TO-DO 2: we need to convert Customer to CustomerDTO
         * SOLUTION: create methode convertCustomer2CustomerDTO()
         */
        return convertListSchedule2ScheduleDTO(listResult);
    }

    /**
     * WIP
     */
    public List<ScheduleDTO> findScheduleForEmployee(Long employeeId) {
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();

        EmployeeDTO employeeDTO = employeeService.findEmployeeById(employeeId);
        List<ScheduleDTO> listScheduleDTO = findAllSchedules();
        logger.info("[{}] listScheduleDTO SIZE: {}",methodeName, listScheduleDTO.size());

        /**
         * to see the object value from ScheduleDTO
         * in JSON format
         * https://stackoverflow.com/questions/14228912/how-to-convert-list-to-json-in-java
         * <groupId>com.fasterxml.jackson.core</groupId>
         * <artifactId>jackson-databind</artifactId>
         */
        try {
            // https://stackoverflow.com/questions/27952472/serialize-deserialize-java-8-java-time-with-jackson-json-mapper
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules(); // because of java.time.LocalDate AND <artifactId>jackson-datatype-jsr310</artifactId>
            String jsonString = objectMapper.writeValueAsString(listScheduleDTO.get(0));

            logger.info("[{}] ScheduleDTO[0] JSON {}",methodeName, jsonString);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // list stream / lambda
        // https://www.baeldung.com/find-list-element-java
        // https://stackoverflow.com/questions/33992479/java-8-stream-api-to-find-unique-object-matching-a-property-value
//        List<ScheduleDTO> listScheduleDTO = findAllSchedules()
//                .stream()
//                .filter(scheduleDTO -> scheduleDTO.getEmployeeIds().equals(employeeId))
//                .collect(Collectors.toList());

        return listScheduleDTO;
    }

    public List<ScheduleDTO> findScheduleForPet(Long petId){
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] ",methodeName);

        List<ScheduleDTO> listScheduleDTO = new ArrayList<ScheduleDTO>();

        /**
         * TO-DO
         */

        return listScheduleDTO;
    }

    public List<ScheduleDTO> findScheduleForCustomer(Long customerId) {
        String methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info("[{}] ",methodeName);

        List<ScheduleDTO> listScheduleDTO = new ArrayList<ScheduleDTO>();

        /**
         * TO-DO
         */

        return listScheduleDTO;
    }


    public ScheduleDTO convertSchedule2ScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());

        /**
         * WRONG: scheduleDTO.setEmployeeIds(schedule.getEmployeeIds());
         * We need to convert a List<Object> to a List<Long>
         * with the ids of the Objects
         * SOLUTION BELOW:
         * ## Entity/Repository -> DTO ##
         */
        List<Employee> listEmployee = schedule.getEmployeeIds();
        if(listEmployee != null){
            List<Long> listLongEmployeeIds = new ArrayList<>();
            for (Employee employee : listEmployee){
                listLongEmployeeIds.add(employee.getId());
            }
            scheduleDTO.setEmployeeIds(listLongEmployeeIds);
        }

        /**
         * WRONG: scheduleDTO.setPetIds(schedule.getPetIds());;
         * We need to convert a List<Object> to a List<Long>
         * with the ids of the Objects
         * SOLUTION BELOW:
         * ## Entity/Repository -> DTO ##
         */
        List<Pet> listPet = schedule.getPetIds();
        if(listPet != null){
            List<Long> listLongPetIds = new ArrayList<>();
            for (Pet pet : listPet){
                listLongPetIds.add(pet.getId());
            }
            scheduleDTO.setPetIds(listLongPetIds);
        }

        return scheduleDTO;
    }


    // list stream (elegant) + lambda expressions:
    // https://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
    public List<ScheduleDTO> convertListSchedule2ScheduleDTO(List<Schedule> listSchedule){
        return listSchedule
                .stream()
                .map(this::convertSchedule2ScheduleDTO)
                .collect(Collectors.toList());
    }

}

package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        //since Pet and Customer have a bidirectional association the savePet() method will not only save the pet with the given onwerId
        // it will also save the new pet in the customer DB. Chech the service layer for details
        Pet savedPet = petService.savePet(convertDTOToPet(petDTO, customerService));

        return convertPetToDTO(savedPet);

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetToDTO(petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream()
                .map(pet -> convertPetToDTO(pet))
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetByOwner(ownerId);
        return pets.stream()
                .map(pet -> convertPetToDTO(pet))
                .collect(Collectors.toList());
    }

    private static PetDTO convertPetToDTO (Pet pet){
        PetDTO petDTO = new PetDTO();
        //The ownerId has to be set manually since the copyProperties won't find the field
        //because there is no field displayed named ownerId in Pet --> it's owner.ownerId
        petDTO.setOwnerId(pet.getOwner().getId());
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;
    }

    private static Pet convertDTOToPet (PetDTO petDTO, CustomerService customerService){
        Pet pet = new Pet();
        //same as above the copyProperties will not create a customer to instantiate the owner and set their id
        BeanUtils.copyProperties(petDTO, pet);

        //in bidirectional association it has to make sure that both entities are updated. This is the first step to it
        Customer owner = customerService.getCustomerById(petDTO.getOwnerId());

        pet.setOwner(owner);

        return pet;
    }

}

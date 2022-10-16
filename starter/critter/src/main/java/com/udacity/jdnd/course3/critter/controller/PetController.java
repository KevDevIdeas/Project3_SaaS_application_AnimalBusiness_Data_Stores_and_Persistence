package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
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

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet savedPet = petService.savePet(convertDTOToPet(petDTO));
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

    private static Pet convertDTOToPet (PetDTO petDTO){
        Pet pet = new Pet();
        //same as above the copyProperties will not create a customer to instantiate the owner and set their id
        Customer owner = new Customer();
        owner.setId(petDTO.getOwnerId());
        pet.setOwner(owner);
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

}

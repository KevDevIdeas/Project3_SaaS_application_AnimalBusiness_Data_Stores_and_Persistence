package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository repository;

    public Pet savePet(Pet pet){ return repository.save(pet); }

    public Pet getPetById(Long id){
        Optional<Pet> optionalPet = repository.findById(id);
        Pet pet = optionalPet.get();
        return pet;
    }

    public List<Pet> getAllPets(){ return repository.findAll(); }

    public List<Pet> getPetByOwner(Long ownerId){ return repository.findByOwnerId(ownerId); }

}

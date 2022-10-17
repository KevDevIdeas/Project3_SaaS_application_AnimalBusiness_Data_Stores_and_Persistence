package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository repository;
    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet(Pet pet){

        Pet savedPet =  repository.save(pet);

        // in bidirectional association it has to be make sure that both sides (=both entities are updated)
        // first step was when converting the DTO to the pet entity retrieving the customer from the DB with the provided ownerId from the DTO
        // so the savedPet already has the owner from the Customer DB included
        //Next step is it so also add the pet to the Owner
        Customer owner = savedPet.getOwner();
        List<Pet> ownersPets = owner.getPets();
        if(ownersPets == null){
            ownersPets = new ArrayList<>();
        }
        ownersPets.add(savedPet);
        owner.setPets(ownersPets);
        //save will update the existing owner in the DB since an id is provided
        customerRepository.save(owner);


        return savedPet; }

    public Pet getPetById(Long id){
        Optional<Pet> optionalPet = repository.findById(id);
        Pet pet = optionalPet.get();
        return pet;
    }

    public List<Pet> getAllPets(){ return repository.findAll(); }

    public List<Pet> getPetByOwner(Long ownerId){ return repository.findByOwnerId(ownerId); }

}

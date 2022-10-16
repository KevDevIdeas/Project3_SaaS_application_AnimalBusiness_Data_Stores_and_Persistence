package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    @Autowired
    CustomerRepository repository;

    public Customer saveCustomer(Customer customer){
        return repository.save(customer);
    }

    public Customer getCustomerById(Long id){
        Optional<Customer> optionalCustomer = repository.findById(id);
        Customer customer = optionalCustomer.get();
        return customer;
    }

    public List<Customer> getAllCustomers(){ return repository.findAll(); }

    public Customer getOwnerOfPetById (Long petId){return repository.findByPetId(petId);}

    
}

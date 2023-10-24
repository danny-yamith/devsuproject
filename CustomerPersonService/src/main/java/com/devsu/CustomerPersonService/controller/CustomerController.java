package com.devsu.CustomerPersonService.controller;

import com.devsu.CustomerPersonService.dto.CustomerDTO;
import com.devsu.CustomerPersonService.service.ICustomerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Log4j2
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        iCustomerService.saveCustomer(customerDTO);
        log.info("Saving customer.");
        return ResponseEntity.ok(customerDTO);
    }

    @GetMapping
    public List<CustomerDTO> getAll() {
        log.info("List all customers.");
        return iCustomerService.findAll();
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerDTO> updateEmployee(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        log.info("Update customer.");
        return ResponseEntity.ok(iCustomerService.updateCustomer(id, customerDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id) {
        iCustomerService.deleteCustomerById(id);
        log.info("Delete customer.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        log.info("Customer by Id.");
        return ResponseEntity.ok(iCustomerService.findCustomerById(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = iCustomerService.patchCustomer(id, customerDTO);
        log.info("Patch customer.");
        return ResponseEntity.ok(updatedCustomer);
    }
}

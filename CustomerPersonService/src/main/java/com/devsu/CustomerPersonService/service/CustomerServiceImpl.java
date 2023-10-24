package com.devsu.CustomerPersonService.service;

import com.devsu.CustomerPersonService.dto.CustomerDTO;
import com.devsu.CustomerPersonService.entity.Customer;
import com.devsu.CustomerPersonService.entity.Person;
import com.devsu.CustomerPersonService.exception.CustomException;
import com.devsu.CustomerPersonService.external.client.AccountService;
import com.devsu.CustomerPersonService.repository.CustomerRepository;
import com.devsu.CustomerPersonService.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    AccountService accountService;

    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        Customer customer = ObjectMapperUtils.map(customerDTO, Customer.class);
        customerRepository.save(customer);
    }

    @Override
    public List<CustomerDTO> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return ObjectMapperUtils.mapAll(customers, CustomerDTO.class);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomException("Customer by Id " + id + " was not found.", "CUSTOMER_NOT_FOUND", 404));
        customerRepository.save(updateCustomerFields(existingCustomer,customerDTO));
        return customerDTO;
    }

    @Override
    public void deleteCustomerById(Long id) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomException("Customer by Id " + id + " was not found.", "CUSTOMER_NOT_FOUND", 404));

        if(accountService.getAccountsByCustomerId(id)) {
            throw new CustomException("Customer Id " + id + " has many Account.", "CUSTOMER_CAN_NOT_DELETE", 500);
        }
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDTO findCustomerById(Long id) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomException("Customer by Id " + id + " was not found.", "CUSTOMER_NOT_FOUND", 404));

        return ObjectMapperUtils.map(existingCustomer, CustomerDTO.class);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomException("Customer by Id " + id + " was not found.", "CUSTOMER_NOT_FOUND", 404));

        customerRepository.save(updateCustomerFields(existingCustomer, customerDTO));
        return ObjectMapperUtils.map(existingCustomer, CustomerDTO.class);
    }
    private Customer updateCustomerFields(Customer existingCustomer, CustomerDTO customerDTO) {
        if (customerDTO.getAddress() != null) {
            existingCustomer.setAddress(customerDTO.getAddress());
        }
        if (customerDTO.getAge() != 0) {
            existingCustomer.setAge(customerDTO.getAge());
        }
        if (customerDTO.getName() != null) {
            existingCustomer.setName(customerDTO.getName());
        }
        if (customerDTO.getGender() != null) {
            existingCustomer.setGender(Person.GenderEnum.valueOf(customerDTO.getGender()));
        }
        if (customerDTO.getPhone() != null) {
            existingCustomer.setPhone(customerDTO.getPhone());
        }
        if (customerDTO.getIdentification() != null) {
            existingCustomer.setIdentification(customerDTO.getIdentification());
        }
        if (customerDTO.getPassword() != null) {
            existingCustomer.setPassword(customerDTO.getPassword());
        }
        if (customerDTO.getStatus() != null) {
            existingCustomer.setStatus(Customer.StatusEnum.valueOf(customerDTO.getStatus()));
        }
        return existingCustomer;
    }
}

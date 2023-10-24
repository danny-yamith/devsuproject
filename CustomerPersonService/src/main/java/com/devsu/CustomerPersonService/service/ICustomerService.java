package com.devsu.CustomerPersonService.service;

import com.devsu.CustomerPersonService.dto.CustomerDTO;

import java.util.List;

public interface ICustomerService {

    void saveCustomer(CustomerDTO customer);

    public List<CustomerDTO> findAll();

    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);

    CustomerDTO findCustomerById(Long id);

    CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);
}

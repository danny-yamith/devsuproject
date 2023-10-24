package com.devsu.CustomerPersonService.controller;

import com.devsu.CustomerPersonService.dto.CustomerDTO;
import com.devsu.CustomerPersonService.service.ICustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private ICustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();

        // Mock the void method using doNothing
        doNothing().when(customerService).saveCustomer(customerDTO);

        ResponseEntity<CustomerDTO> response = customerController.saveCustomer(customerDTO);

        verify(customerService, times(1)).saveCustomer(customerDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllCustomers() {
        List<CustomerDTO> customerList = new ArrayList<>();
        when(customerService.findAll()).thenReturn(customerList);

        List<CustomerDTO> result = customerController.getAll();

        verify(customerService, times(1)).findAll();
        assertSame(customerList, result);
    }

    @Test
    public void testUpdateEmployee() {
        Long customerId = 1L;
        CustomerDTO customerDTO = new CustomerDTO();
        // Set other fields in customerDTO as needed

        Mockito.when(customerService.updateCustomer(customerId, customerDTO))
                .thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> responseEntity = customerController.updateEmployee(customerId, customerDTO);

        Mockito.verify(customerService, Mockito.times(1)).updateCustomer(customerId, customerDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerDTO, responseEntity.getBody());
    }

    @Test
    public void testDeleteEmployee() {
        Long customerId = 1L;

        ResponseEntity<HttpStatus> responseEntity = customerController.deleteEmployee(customerId);

        Mockito.verify(customerService, Mockito.times(1)).deleteCustomerById(customerId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testGetCustomerById() {
        Long customerId = 1L;
        CustomerDTO customerDTO = new CustomerDTO();
        // Set other fields in customerDTO as needed

        Mockito.when(customerService.findCustomerById(customerId))
                .thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> responseEntity = customerController.getCustomerById(customerId);

        Mockito.verify(customerService, Mockito.times(1)).findCustomerById(customerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerDTO, responseEntity.getBody());
    }

    @Test
    public void testPatchCustomer() {
        Long customerId = 1L;
        CustomerDTO customerDTO = new CustomerDTO();
        // Set other fields in customerDTO as needed

        Mockito.when(customerService.patchCustomer(customerId, customerDTO))
                .thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> responseEntity = customerController.patchCustomer(customerId, customerDTO);

        Mockito.verify(customerService, Mockito.times(1)).patchCustomer(customerId, customerDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerDTO, responseEntity.getBody());
    }

}

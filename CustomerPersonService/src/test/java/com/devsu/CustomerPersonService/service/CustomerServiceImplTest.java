package com.devsu.CustomerPersonService.service;

import com.devsu.CustomerPersonService.dto.CustomerDTO;
import com.devsu.CustomerPersonService.entity.Customer;
import com.devsu.CustomerPersonService.exception.CustomException;
import com.devsu.CustomerPersonService.external.client.AccountService;
import com.devsu.CustomerPersonService.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        Customer customer = new Customer();
        when(customerRepository.save(any())).thenReturn(customer);

        customerService.saveCustomer(customerDTO);

        verify(customerRepository, times(1)).save(any());
    }

    @Test
    public void testFindAll() {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), customerService.findAll());
    }

    @Test
    public void testUpdateCustomer() {
        Long customerId = 1L;
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Updated Name");
        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any())).thenReturn(existingCustomer);

        CustomerDTO updatedCustomer = customerService.updateCustomer(customerId, customerDTO);

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).save(existingCustomer);

        assertEquals(customerDTO.getName(), updatedCustomer.getName());
    }

    @Test
    public void testDeleteCustomerById() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(new Customer()));
        when(accountService.getAccountsByCustomerId(customerId)).thenReturn(false);

        assertDoesNotThrow(() -> customerService.deleteCustomerById(customerId));

        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    public void testDeleteCustomerByIdWithAccounts() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(new Customer()));
        when(accountService.getAccountsByCustomerId(customerId)).thenReturn(true);

        assertThrows(CustomException.class, () -> customerService.deleteCustomerById(customerId));

        verify(customerRepository, never()).deleteById(customerId);
    }

    @Test
    public void testFindCustomerById() {
        Long customerId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);
        existingCustomer.setName("John");
        // Set other fields as needed

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

        CustomerDTO foundCustomer = customerService.findCustomerById(customerId);

        verify(customerRepository, times(1)).findById(customerId);

        assertNotNull(foundCustomer); // Assert that the foundCustomer is not null
        assertEquals("John", foundCustomer.getName()); // Assert other fields as needed
        // Assert other fields as needed
    }

    @Test
    public void testFindCustomerByIdNotFound() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> customerService.findCustomerById(customerId));

        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    public void testPatchCustomer() {
        Long customerId = 1L;
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Updated Name");
        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any())).thenReturn(existingCustomer);

        CustomerDTO updatedCustomer = customerService.patchCustomer(customerId, customerDTO);

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).save(existingCustomer);

        assertEquals(customerDTO.getName(), updatedCustomer.getName());
    }

    @Test
    public void testPatchCustomerNotFound() {
        Long customerId = 1L;
        CustomerDTO customerDTO = new CustomerDTO();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> customerService.patchCustomer(customerId, customerDTO));

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).save(any());
    }

}

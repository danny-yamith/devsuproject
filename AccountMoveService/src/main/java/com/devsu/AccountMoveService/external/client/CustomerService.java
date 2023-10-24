package com.devsu.AccountMoveService.external.client;

import com.devsu.AccountMoveService.exception.CustomException;
import com.devsu.AccountMoveService.external.dto.CustomerDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "CUSTOMER-PERSON-SERVICE/clientes")
public interface CustomerService {

    @GetMapping("/customer/{customerId}")
    public @ResponseBody ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("customerId") Long customerId);

    default ResponseEntity<Long> fallback(Exception e) {
        throw new CustomException("Customer Service is not available",
                "UNAVAILABLE",
                500);
    }
}
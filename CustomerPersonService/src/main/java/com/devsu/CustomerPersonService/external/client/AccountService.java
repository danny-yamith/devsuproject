package com.devsu.CustomerPersonService.external.client;

import com.devsu.CustomerPersonService.exception.CustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "ACCOUNT-MOVE-SERVICE/cuentas")
public interface AccountService {

    @GetMapping("/account/{customerId}")
    public boolean getAccountsByCustomerId(@PathVariable Long customerId);

    default ResponseEntity<Long> fallback(Exception e) {
        throw new CustomException("Customer Service is not available",
                "UNAVAILABLE",
                500);
    }
}
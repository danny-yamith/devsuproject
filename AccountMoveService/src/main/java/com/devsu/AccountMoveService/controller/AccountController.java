package com.devsu.AccountMoveService.controller;

import com.devsu.AccountMoveService.dto.AccountDTO;
import com.devsu.AccountMoveService.service.IAccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@Log4j2
public class AccountController {
    @Autowired
    private IAccountService iAccountService;

    @PostMapping
    public ResponseEntity<AccountDTO> saveCustomer(@RequestBody AccountDTO accountDTO) {
        AccountDTO accDtoResp = iAccountService.saveAccount(accountDTO);
        log.info("Saving Account.");
        return ResponseEntity.ok(accDtoResp);
    }

    @GetMapping
    public List<AccountDTO> getAll() {
        log.info("List all accounts.");
        return iAccountService.findAll();
    }

    @GetMapping("/account/{customerId}")
    public ResponseEntity<Boolean> getAccountsByCustomerId(@PathVariable Long customerId) {
        log.info("Accounts by customerId.");
        return ResponseEntity.ok(iAccountService.findAccountsByCustomerId(customerId));
    }

   @PutMapping("{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        log.info("Update account.");
        return ResponseEntity.ok(iAccountService.updateAccount(id, accountDTO));
    }

    @PatchMapping("{id}")
    public ResponseEntity<AccountDTO> patchCustomer(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        AccountDTO updatedAccount = iAccountService.patchAccount(id, accountDTO);
        log.info("Patch account.");
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable Long id) {
        iAccountService.deleteAccountById(id);
        log.info("Delete account.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.devsu.AccountMoveService.service;


import com.devsu.AccountMoveService.dto.AccountDTO;
import com.devsu.AccountMoveService.entity.Account;
import com.devsu.AccountMoveService.exception.CustomException;
import com.devsu.AccountMoveService.external.client.CustomerService;
import com.devsu.AccountMoveService.external.dto.CustomerDTO;
import com.devsu.AccountMoveService.repository.AccountRepository;
import com.devsu.AccountMoveService.util.ObjectMapperUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class AccountServiceImpl implements IAccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerService customerService;

    @Override
    public AccountDTO saveAccount(AccountDTO accountDTO) throws CustomException {
        Account account = ObjectMapperUtils.map(accountDTO, Account.class);
        return getAccountDTO(account);


    }

    private AccountDTO getAccountDTO(Account account) {
        try {
            ResponseEntity<CustomerDTO> responseEntity = customerService.getCustomerById(account.getCustomerId());
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                account.setName(responseEntity.getBody().getName());
                accountRepository.save(account);
                return ObjectMapperUtils.map(account, AccountDTO.class);
            } else {
                throw new CustomException("Customer by Id " + account.getCustomerId() + " was not found.", "CUSTOMER_NOT_FOUND", 404);
            }
        } catch (Exception ex) {
            throw new CustomException("Customer by Id " + account.getCustomerId() + " was not found.", "CUSTOMER_NOT_FOUND", 404);
        }
    }


    @Override
    public List<AccountDTO> findAll() {
        List<Account> accounts = accountRepository.findAll();
        return ObjectMapperUtils.mapAll(accounts, AccountDTO.class);
    }

    @Override
    public Boolean findAccountsByCustomerId(Long customerId) {
        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        return !accounts.isEmpty();
    }

    @Override
    public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new CustomException("Account by Id " + id + " was not found.", "ACCOUNT_NOT_FOUND", 404));
        return getAccountDTO(updateCustomerFields(existingAccount,accountDTO));
    }

    @Override
    public AccountDTO patchAccount(Long id, AccountDTO accountDTO) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new CustomException("Account by Id " + id + " was not found.", "ACCOUNT_NOT_FOUND", 404));

        accountRepository.save(updateCustomerFields(existingAccount, accountDTO));
        return getAccountDTO(updateCustomerFields(existingAccount,accountDTO));
    }

    @Override
    public void deleteAccountById(Long id) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new CustomException("Account by Id " + id + " was not found.", "ACCOUNT_NOT_FOUND", 404));

        accountRepository.deleteById(id);
    }


    private Account updateCustomerFields(Account existingAccount, AccountDTO accountDTO) {
        if (accountDTO.getCustomerId() != null) {
            existingAccount.setCustomerId(accountDTO.getCustomerId());
        }
        if (accountDTO.getInitial() != 0) {
            existingAccount.setInitial(accountDTO.getInitial());
        }
        if (accountDTO.getStatus() != null) {
            existingAccount.setStatus(Account.StatusEnum.valueOf(accountDTO.getStatus()));
        }
        if (accountDTO.getName() != null) {
            existingAccount.setName(accountDTO.getName());
        }
        if (accountDTO.getType() != null) {
            existingAccount.setType(Account.AccType.valueOf(accountDTO.getType()));
        }
        if (accountDTO.getNumacc() != null) {
            existingAccount.setNumacc(accountDTO.getNumacc());
        }
        return existingAccount;
    }
}

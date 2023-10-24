package com.devsu.AccountMoveService.service;

import com.devsu.AccountMoveService.dto.AccountDTO;
import com.devsu.AccountMoveService.exception.CustomException;

import java.util.List;

public interface IAccountService {

    AccountDTO saveAccount(AccountDTO accountDTO) throws CustomException;

    List<AccountDTO> findAll();

    Boolean findAccountsByCustomerId(Long customerId);

    AccountDTO updateAccount(Long id, AccountDTO accountDTO);

    AccountDTO patchAccount(Long id, AccountDTO accountDTO);

    void deleteAccountById(Long id);

}

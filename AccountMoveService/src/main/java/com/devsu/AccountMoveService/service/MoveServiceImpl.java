package com.devsu.AccountMoveService.service;


import com.devsu.AccountMoveService.dto.MoveDTO;
import com.devsu.AccountMoveService.entity.Account;
import com.devsu.AccountMoveService.entity.Move;
import com.devsu.AccountMoveService.exception.CustomException;
import com.devsu.AccountMoveService.repository.AccountRepository;
import com.devsu.AccountMoveService.repository.MoveRepository;
import com.devsu.AccountMoveService.util.ObjectMapperUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class MoveServiceImpl implements IMoveService {

    @Autowired
    MoveRepository moveRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public MoveDTO saveMove(MoveDTO moveDTO) {
        // Fetch the corresponding Account entity based on accountId (you may need to implement this method)
        Account account = accountRepository.findById(moveDTO.getAccountId())
                .orElseThrow(() -> new CustomException("Account by Id " + moveDTO.getAccountId() + " was not found.", "ACCOUNT_NOT_FOUND", 404));

        Move move = ObjectMapperUtils.map(moveDTO, Move.class);
        account.setTransactions(account.getTransactions()+1);
        move.setAccount(account); // Set the Account for the Move

        List<Move> movements = moveRepository.findByAccountIdOrderByIdAsc(moveDTO.getAccountId());
        int size = movements.size();

        if (size > 0) {
            Move lastMove = movements.get(size - 1);
            if (move.getType() == Move.MoveType.Deposito) {
                move.setBalance(lastMove.getBalance() + move.getValor());
            } else {

                long balance = lastMove.getBalance() - move.getValor();
                if(balance <0){
                    throw new CustomException("Unavailable Balance", "BALANCE_NEGATIVE", 400);
                }
                move.setBalance(lastMove.getBalance() - move.getValor());
            }
        } else {
            if (move.getType() == Move.MoveType.Deposito) {
                move.setBalance(account.getInitial() + move.getValor());
            } else {
                long balance = account.getInitial() - move.getValor();
                if(balance <0){
                    throw new CustomException("Unavailable Balance", "BALANCE_NEGATIVE", 400);
                }
                move.setBalance(account.getInitial() - move.getValor());
            }
        }


        moveRepository.save(move);

        // Update the MoveDTO with the account_id
        moveDTO.setAccountId(account.getId()); // Set the account_id in the MoveDTO
        moveDTO.setBalance(move.getBalance());
        return moveDTO;
    }


    @Override
    public List<MoveDTO> findAll() {
        List<Move> movements = moveRepository.findAll();

        // Create a list to store the mapped MoveDTO objects
        return getMoveDTOS(movements);
    }

    private static List<MoveDTO> getMoveDTOS(List<Move> movements) {
        List<MoveDTO> moveDTOs = new ArrayList<>();

        // Map each Move entity to a MoveDTO and set the accountId
        for (Move move : movements) {
            MoveDTO moveDTO = ObjectMapperUtils.map(move, MoveDTO.class);
            moveDTO.setAccountId(move.getAccount().getId());
            moveDTOs.add(moveDTO);
        }

        return moveDTOs;
    }

    @Override
    public List<MoveDTO> findMovementsByAccountId(Long accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new CustomException("Account by Id " + accountId + " was not found.", "ACCOUNT_NOT_FOUND", 404));
        List<Move> movements = moveRepository.findByAccountIdOrderByIdAsc(accountId);
        return  getMoveDTOS(movements);

    }

}




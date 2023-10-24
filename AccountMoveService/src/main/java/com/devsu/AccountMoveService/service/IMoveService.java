package com.devsu.AccountMoveService.service;

import com.devsu.AccountMoveService.dto.MoveDTO;
import com.devsu.AccountMoveService.entity.Move;
import com.devsu.AccountMoveService.exception.CustomException;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface IMoveService {

    MoveDTO saveMove(MoveDTO moveDTO) throws CustomException;

    List<MoveDTO> findAll();

    List<MoveDTO> findMovementsByAccountId(Long accountId);



}

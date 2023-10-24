package com.devsu.AccountMoveService.controller;

import com.devsu.AccountMoveService.dto.MoveDTO;
import com.devsu.AccountMoveService.service.IMoveService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@Log4j2
public class MoveController {
    @Autowired
    private IMoveService iMoveService;

    @PostMapping
    public ResponseEntity<MoveDTO> saveMove(@RequestBody MoveDTO moveDTO) {
        MoveDTO movDtoResp = iMoveService.saveMove(moveDTO);
        log.info("Saving movement.");
        return ResponseEntity.ok(movDtoResp);
    }

   @GetMapping
    public List<MoveDTO> getAll() {
        log.info("List all movements.");
        return iMoveService.findAll();
    }

    @GetMapping("/movements/{accountId}")
    public ResponseEntity<List<MoveDTO>> getMovementsByAccountId(@PathVariable Long accountId) {
        log.info("Movements by AccountId.");
        return ResponseEntity.ok(iMoveService.findMovementsByAccountId(accountId));
    }

}

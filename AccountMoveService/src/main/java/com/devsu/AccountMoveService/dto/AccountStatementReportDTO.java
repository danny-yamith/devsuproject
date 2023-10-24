package com.devsu.AccountMoveService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountStatementReportDTO {
    private LocalDate moveDate;
    private String accountName;
    private String accountNumber;
    private String accountType;
    private long accountInitial;
    private String accountStatus;
    private long moveValor;
    private long moveBalance;

}

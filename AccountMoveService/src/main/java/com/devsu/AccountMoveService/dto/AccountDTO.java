package com.devsu.AccountMoveService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
    private String numacc;
    private String type;
    private long initial;
    private String status;
    private String name;
    private Long customerId;
}

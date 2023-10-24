package com.devsu.AccountMoveService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoveDTO {
    private LocalDate date;
    private String type;
    private long valor;
    private long balance;

    @JsonProperty("account_id")
    private Long accountId;
}

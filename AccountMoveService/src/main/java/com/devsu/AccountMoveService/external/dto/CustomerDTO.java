package com.devsu.AccountMoveService.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
    private String password;
    private String status;

    // Fields from Person
    private String name;
    private String gender;
    private int age;
    private String identification;
    private String address;
    private String phone;

}

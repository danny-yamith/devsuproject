package com.devsu.CustomerPersonService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true, builderMethodName = "customerBuilder")
public class Customer extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING) // Use EnumType.STRING to store ENUM values as strings
    @Column(name = "STATUS")
    private StatusEnum status;

    public enum StatusEnum {
        TRUE,
        FALSE
    }
}

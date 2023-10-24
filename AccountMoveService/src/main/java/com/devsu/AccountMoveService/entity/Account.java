package com.devsu.AccountMoveService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ACCOUNT_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "NUM_ACC", unique = true) // Set unique to true
    private String numacc;

    @Column(name = "AMOUNT_INITIAL")
    private long initial;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "TRANSACTIONS", columnDefinition = "INT DEFAULT 0")
    private int transactions;

    @Column(name = "CUSTOMER_NAME")
    private String name;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Move> moves;

    @Enumerated(EnumType.STRING) // Use EnumType.STRING to store ENUM values as strings
    @Column(name = "STATUS")
    private StatusEnum status;

    public enum StatusEnum {
        TRUE,
        FALSE
    }

    @Enumerated(EnumType.STRING) // Use EnumType.STRING to store ENUM values as strings
    @Column(name = "TYPE_ACC")
    private AccType type;


    public enum AccType {
        Corriente,
        Ahorros
    }
}

package com.devsu.AccountMoveService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "MOVE_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "DATE_REG")
    private LocalDate date;

    @Column(name = "VALUE")
    private long valor;

    @Column(name = "BALANCE")
    private long balance;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING) // Use EnumType.STRING to store ENUM values as strings
    @Column(name = "TYPE_MOVE")
    private MoveType type;


    public enum MoveType {
        Retiro,
        Deposito
    }

}

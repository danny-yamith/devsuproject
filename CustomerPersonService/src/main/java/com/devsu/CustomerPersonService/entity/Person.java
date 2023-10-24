package com.devsu.CustomerPersonService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "PERSON_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

    @Column(name = "IDENTIFICATION")
    private String identification;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE")
    private String phone;


    @Enumerated(EnumType.STRING) // Use EnumType.STRING to store ENUM values as strings
    @Column(name = "GENDER")
    private GenderEnum gender;

    public enum GenderEnum {
        Female,
        Male
    }

}

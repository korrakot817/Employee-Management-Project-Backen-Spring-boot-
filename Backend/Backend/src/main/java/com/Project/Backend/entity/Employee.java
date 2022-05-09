package com.Project.Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "m_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 120)
    private String firstName;

    @Column(nullable = false, length = 120)
    private String lastName;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private Integer phoneNumber;

    @Column(nullable = false)
    private Integer salary;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private Integer zipcode;

    @Column
    private String picture;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "m_position_id")
    private Position position;

}

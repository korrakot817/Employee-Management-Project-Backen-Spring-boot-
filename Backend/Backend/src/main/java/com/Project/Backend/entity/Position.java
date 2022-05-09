package com.Project.Backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity(name = "m_position")
@Data
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String position;


    @OneToMany(mappedBy = "position", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Employee> employeeList;


}

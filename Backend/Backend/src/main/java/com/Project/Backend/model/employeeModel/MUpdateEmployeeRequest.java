package com.Project.Backend.model.employeeModel;

import lombok.Data;

@Data
public class MUpdateEmployeeRequest {

    private String firstName;

    private String lastName;

    private String gender;

    private String email;

    private Integer phoneNumber;

    private Integer salary;

    private String street;

    private String city;

    private String state;

    private Integer zipcode;

}

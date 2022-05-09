package com.Project.Backend.model;

import lombok.Data;

@Data
public class MRegisterRequest {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

}

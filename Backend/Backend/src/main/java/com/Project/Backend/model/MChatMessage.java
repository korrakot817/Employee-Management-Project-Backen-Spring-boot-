package com.Project.Backend.model;

import lombok.Data;

import java.util.Date;

@Data
public class MChatMessage {

    private String from;

    private String message;

    private Date create;

    public MChatMessage(){
        create = new Date();
    }
}

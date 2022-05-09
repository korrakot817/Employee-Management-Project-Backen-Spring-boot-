package com.Project.Backend.model;

import lombok.Data;

@Data
public class MFileUploadResponse {

    private String fileName;

    private String downloadUri;

    private long size;

}

package com.nikolay_netology.diplom.util.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

@Data
public class EditNameFileRequest {

    private String filename;

    @JsonCreator
    public EditNameFileRequest(String filename) {
        this.filename = filename;
    }

}

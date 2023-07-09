package com.nikolay_netology.diplom.util.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseAuth {
    @JsonProperty("auth-token")
    private String authToken;
}

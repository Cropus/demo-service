package com.itmo.microservices.shop.user.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationRequest {
    @JsonProperty("name")
    private String username;

    @JsonProperty("password")
    private String password;

    public RegistrationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public RegistrationRequest() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

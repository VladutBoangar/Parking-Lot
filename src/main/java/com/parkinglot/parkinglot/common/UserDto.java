package com.parkinglot.parkinglot.common;

public class UserDto {
    String username;
    String email;

    public UserDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}

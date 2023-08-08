package com.usersapp.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

public class UserDTO {

    private String uuid;
    private String name;
    private String surname;
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private String created_at;
    private String updated_at;

    public UserDTO uuid(UUID uuid) {
        this.uuid = uuid.toString();
        return this;
    }

    public UserDTO name(String name) {
        this.name = name;
        return this;
    }

    public UserDTO surname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserDTO email(String email) {
        this.email = email;
        return this;
    }

    public UserDTO password(String password) {
        this.password = password;
        return this;
    }

    public UserDTO created_at(String createdAt) {
        this.created_at = createdAt;
        return this;
    }

    public UserDTO updated_at(String updatedAt) {
        this.updated_at = updatedAt;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

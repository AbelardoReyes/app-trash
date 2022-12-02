package com.example.trash.clases;

public class Usuario {
    String name, email, phone_number, password,email_verified_at,remember_token,created_at,updated_at;
    int id;
    String username, active_key;

    public Usuario(String name, String email, String phone_number, String password, String email_verified_at, String remember_token, String created_at, String updated_at, int id) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
        this.email_verified_at = email_verified_at;
        this.remember_token = remember_token;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.id = id;
    }

    public Usuario(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone_number = phone;
        this.password = password;
    }

    public Usuario(String name, String email, String phone_number, String username, String active_key) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getActive_key() {
        return active_key;
    }

    public void setActive_key(String active_key) {
        this.active_key = active_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getRemember_token() {
        return remember_token;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

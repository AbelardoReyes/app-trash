package com.example.trash.clases;

public class Respuesta {
    String status,token,role,url;

    public Respuesta(String status, String token, String role) {
        this.status = status;
        this.token = token;
        this.role = role;
    }

    public Respuesta(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

package com.example.trash.clases;

import java.util.List;

public class ResponseCars {
    String status, message;
    List<Carrito> cars;

    public ResponseCars(String status, String message, List<Carrito> cars) {
        this.status = status;
        this.message = message;
        this.cars = cars;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Carrito> getCars() {
        return cars;
    }

    public void setCars(List<Carrito> cars) {
        this.cars = cars;
    }
}

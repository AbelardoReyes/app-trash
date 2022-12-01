package com.example.trash.clases;

import java.util.List;

public class DataUsuario {
    List<Usuario> data;

    public DataUsuario(List<Usuario> data) {
        this.data = data;
    }

    public List<Usuario> getData() {
        return data;
    }

    public void setData(List<Usuario> data) {
        this.data = data;
    }
}

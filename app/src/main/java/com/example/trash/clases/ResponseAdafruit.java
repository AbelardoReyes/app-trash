package com.example.trash.clases;

public class ResponseAdafruit {
    String tapa,humedad,temperatura;

    public ResponseAdafruit(String tapa, String humedad, String temperatura) {
        this.tapa = tapa;
        this.humedad = humedad;
        this.temperatura = temperatura;
    }

    public String getTapa() {
        return tapa;
    }

    public void setTapa(String tapa) {
        this.tapa = tapa;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }
}

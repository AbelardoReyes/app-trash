package com.example.trash.clases;

public class ResponseAdafruit {
    String tapa,humedad,temperatura,distancia;

    public ResponseAdafruit(String tapa, String humedad, String temperatura, String distancia) {
        this.tapa = tapa;
        this.humedad = humedad;
        this.temperatura = temperatura;
        this.distancia = distancia;
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

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }
}

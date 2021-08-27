package com.example.imblue.model;

public class Comando {
    public Historial historial;

    public void aplicar() {}
    public void revertir() {}

    public Historial getHistorial() {
        return historial;
    }

    public void setHistorial(Historial historial) {
        this.historial = historial;
    }
}

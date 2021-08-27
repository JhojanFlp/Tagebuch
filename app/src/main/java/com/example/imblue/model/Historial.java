package com.example.imblue.model;

import java.util.ArrayList;
import java.util.List;

public class Historial {
    private List<Comando> registrosRehechos;
    private List<Comando> registrosDesechos;
    private static Historial historial;

    public Historial(){
        this.registrosRehechos = new ArrayList<>();
        this.registrosDesechos = new ArrayList<>();
    }

    public static Historial getHistorial() {
        if(historial == null) {
            historial = new Historial();
        }
        return historial;
    }

    public List<Comando> getRegistrosRehechos() {
        return registrosRehechos;
    }

    public void setRegistrosRehechos(List<Comando> registrosRehechos) {
        this.registrosRehechos = registrosRehechos;
    }

    public List<Comando> getRegistrosDesechos() {
        return registrosDesechos;
    }

    public void setRegistrosDesechos(List<Comando> registrosDesechos) {
        this.registrosDesechos = registrosDesechos;
    }
}

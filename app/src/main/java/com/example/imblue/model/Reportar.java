package com.example.imblue.model;

import com.example.imblue.model.dao.PensamientoRoomDAO;
import com.example.imblue.model.pojo.Pensamiento;

public class Reportar extends Comando {
    private PensamientoRoomDAO pensamientoRoomDAO;
    private Pensamiento pensamiento;

    public Reportar(PensamientoRoomDAO pensamientoRoomDAO, Pensamiento pensamiento) {
        this.pensamientoRoomDAO = pensamientoRoomDAO;
        this.pensamiento = pensamiento;
    }

    @Override
    public void aplicar() {
        pensamientoRoomDAO.insertPensamiento(this.pensamiento);
    }

    @Override
    public void revertir() {
        pensamientoRoomDAO.deletePensamiento(this.pensamiento);
    }

}

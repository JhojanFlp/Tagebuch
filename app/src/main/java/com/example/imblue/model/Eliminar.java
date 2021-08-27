package com.example.imblue.model;

import com.example.imblue.model.dao.PensamientoRoomDAO;
import com.example.imblue.model.pojo.Pensamiento;

public class Eliminar extends Comando {
    private PensamientoRoomDAO pensamientoRoomDAO;
    private Pensamiento pensamiento;

    public Eliminar(PensamientoRoomDAO pensamientoRoomDAO, Pensamiento pensamiento) {
        this.pensamientoRoomDAO = pensamientoRoomDAO;
        this.pensamiento = pensamiento;
    }

    @Override
    public void aplicar() {
        pensamientoRoomDAO.deletePensamiento(this.pensamiento);
    }

    @Override
    public void revertir() {
        pensamientoRoomDAO.insertPensamiento(this.pensamiento);
    }
}
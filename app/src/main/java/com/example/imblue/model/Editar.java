package com.example.imblue.model;

import com.example.imblue.model.dao.PensamientoRoomDAO;
import com.example.imblue.model.pojo.Pensamiento;

public class Editar extends Comando {
    private PensamientoRoomDAO pensamientoRoomDAO;
    private Pensamiento pensamientoOld;
    private Pensamiento pensamiento;

    public Editar(PensamientoRoomDAO pensamientoRoomDAO, Pensamiento pensamientoOld, Pensamiento pensamiento) {
        this.pensamientoRoomDAO = pensamientoRoomDAO;
        this.pensamientoOld = pensamientoOld;
        this.pensamiento = pensamiento;
    }

    @Override
    public void aplicar() {
        pensamientoRoomDAO.updatePensamiento(this.pensamiento);
    }

    @Override
    public void revertir() {
        pensamientoRoomDAO.updatePensamiento(this.pensamientoOld);
    }
}
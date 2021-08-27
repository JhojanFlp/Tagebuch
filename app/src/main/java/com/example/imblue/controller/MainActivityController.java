package com.example.imblue.controller;


import android.widget.EditText;

import com.example.imblue.model.Comando;
import com.example.imblue.model.Editar;
import com.example.imblue.model.Eliminar;
import com.example.imblue.model.Historial;
import com.example.imblue.model.Reportar;
import com.example.imblue.view.MainActivity;
import com.example.imblue.model.LocalStorage;
import com.example.imblue.model.dao.CategoriaRoomDAO;
import com.example.imblue.model.dao.PensamientoRoomDAO;
import com.example.imblue.model.pojo.Categoria;
import com.example.imblue.model.pojo.Pensamiento;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivityController {
    private CategoriaRoomDAO categoriaRoomDAO;
    private PensamientoRoomDAO pensamientoRoomDAO;
    private Historial historial;

    // Función para crear las categorías en BD (si no existen)
    public void checkOrCreateCategorias(MainActivity mainActivity) {
        this.categoriaRoomDAO = LocalStorage
                .getLocalStorage(mainActivity.getApplicationContext()).categoriaRoomDAO();

        Categoria categoria = this.categoriaRoomDAO.getCategoria();
        if(categoria == null){
            // Create categories
            String[] nombres = {"Creativo", "Analítico", "Crítico", "Estratégico", "Personal"};
            // TODO: Set descriptions
            String[] descripciones = {
                    "Creación, idea o innovación",
                    "Análisis de la situación",
                    "Crítico ante el presente",
                    "Estrategias para ganar y alcanzar el éxito",
                    "Pensamiento temporal que necesito recordar"
            };
            String[] colores = {"9EF01A", "FFD300", "EF476F", "118AB2", "A5A58D"};

            for(int i = 0; i < 5; i++) {
                categoria = new Categoria();
                categoria.setNombre(nombres[i]);
                categoria.setDescripcion(descripciones[i]);
                categoria.setColor(colores[i]);
                this.categoriaRoomDAO.insertCategoria(categoria);
            }
        }
    }

    //Funciones para acceder a elementos de los modelos
    public Categoria getCategoriaById(MainActivity mainActivity, String categoriaId) {
        this.categoriaRoomDAO = LocalStorage
                .getLocalStorage(mainActivity.getApplicationContext()).categoriaRoomDAO();

        Categoria categoria = this.categoriaRoomDAO.getCategoriaById(categoriaId);
        return categoria;
    }

    public String[] getCategoriasByNombre(MainActivity mainActivity) {
        this.categoriaRoomDAO = LocalStorage
                .getLocalStorage(mainActivity.getApplicationContext()).categoriaRoomDAO();

        String[] names = this.categoriaRoomDAO.getCategoriasByName();
        return names;
    }

    public List<Pensamiento> getPensamientos(MainActivity mainActivity) {
        this.pensamientoRoomDAO = LocalStorage
                .getLocalStorage(mainActivity.getApplicationContext()).pensamientoRoomDAO();

        List<Pensamiento> pensamientos = this.pensamientoRoomDAO.getPensamientos();
        return pensamientos;
    }

    public Pensamiento getPensamientoById(MainActivity mainActivity, String id) {
        this.pensamientoRoomDAO = LocalStorage
                .getLocalStorage(mainActivity.getApplicationContext()).pensamientoRoomDAO();

        Pensamiento pensamiento = this.pensamientoRoomDAO.getPensamientoById(id);
        return pensamiento;
    }

    // Controlador para la función reportar
    public void reportar(MainActivity mainActivity, String titulo, String descripcion, String categoriaName) {
        // Validaciones
        if (titulo == null || titulo.compareTo("") == 0) {
            mainActivity.error("El título es obligatorio");
            return;
        }
        if (descripcion == null || descripcion.compareTo("") == 0) {
            mainActivity.error("La descripción es obligatoria");
            return;
        }
        if (titulo.length() > 100) {
            mainActivity.error("El título tiene un límite de 100 caracteres");
            return;
        }

        this.pensamientoRoomDAO = LocalStorage
                .getLocalStorage(mainActivity.getApplicationContext()).pensamientoRoomDAO();
        this.categoriaRoomDAO = LocalStorage
                .getLocalStorage(mainActivity.getApplicationContext()).categoriaRoomDAO();

        Categoria categoria = this.categoriaRoomDAO.getCategoriaByName(categoriaName);

        Pensamiento pensamiento = new Pensamiento();
        pensamiento.setTitulo(titulo);
        pensamiento.setDescripcion(descripcion);
        pensamiento.setCategoriaId(categoria.getId());
        pensamiento.setFecha(pensamiento.GET_DATE());

        Reportar cmdReportar = new Reportar(this.pensamientoRoomDAO, pensamiento);
        cmdReportar.aplicar();
        this.addRegistrosRehechos(cmdReportar);

        mainActivity.reporteSucceed();
    }

    // Controlador para la función eliminar
    public void eliminar(MainActivity mainActivity, String id){
        this.pensamientoRoomDAO = LocalStorage
                .getLocalStorage(mainActivity.getApplicationContext()).pensamientoRoomDAO();

        Pensamiento pensamiento = this.pensamientoRoomDAO.getPensamientoById(id);
        Eliminar cmdEliminar = new Eliminar(this.pensamientoRoomDAO, pensamiento);
        cmdEliminar.aplicar();
        this.addRegistrosRehechos(cmdEliminar);

        mainActivity.deleteSucceed();
    }

    // Controlador para la función editar
    public void editar(MainActivity mainActivity, String titulo, String descripcion, String id) {
        if (titulo == null || titulo.compareTo("") == 0) {
            mainActivity.error("El título es obligatorio");
            return;
        }

        if (descripcion == null || descripcion.compareTo("") == 0) {
            mainActivity.error("La descripción es obligatoria");
            return;
        }

        if (titulo.length() > 100) {
            mainActivity.error("El título tiene un límite de 100 caracteres");
            return;
        }

        this.pensamientoRoomDAO = LocalStorage
                .getLocalStorage(mainActivity.getApplicationContext()).pensamientoRoomDAO();

        Pensamiento pensamientoOld = this.pensamientoRoomDAO.getPensamientoById(id);
        Pensamiento pensamiento = this.pensamientoRoomDAO.getPensamientoById(id);
        pensamiento.setTitulo(titulo);
        pensamiento.setDescripcion(descripcion);

        Editar cmdEditar = new Editar(this.pensamientoRoomDAO, pensamientoOld, pensamiento);
        cmdEditar.aplicar();
        this.addRegistrosRehechos(cmdEditar);

        mainActivity.editSucceed();
    }


    // Historial
    public void addRegistrosRehechos(Comando cmd) {
        this.historial = Historial.getHistorial();
        List<Comando> c = historial.getRegistrosRehechos();

        if (c.size() >= 10) {
            c.remove(0);
        }

        c.add(cmd);
        historial.setRegistrosRehechos(c);
    }

    // Actions
    public void deshacer() {
        this.historial = Historial.getHistorial();
        List<Comando> c = historial.getRegistrosRehechos();

        if (c.size() > 0) {
            Comando cmd = c.remove(c.size() - 1);
            cmd.revertir();
            List<Comando> _c = historial.getRegistrosDesechos();
            if(_c.size() > 10) {
                _c.remove(0);
            }
            _c.add(cmd);
            historial.setRegistrosDesechos(_c);
            historial.setRegistrosRehechos(c);
        }
    }

    public void rehacer() {
        this.historial = Historial.getHistorial();
        List<Comando> c = historial.getRegistrosDesechos();

        if (c.size() > 0) {
            Comando cmd = c.remove(c.size() - 1);
            cmd.aplicar();
            List<Comando> _c = historial.getRegistrosRehechos();
            if(_c.size() > 10) {
                _c.remove(0);
            }
            _c.add(cmd);
            historial.setRegistrosRehechos(_c);
            historial.setRegistrosDesechos(c);
        }
    }
}

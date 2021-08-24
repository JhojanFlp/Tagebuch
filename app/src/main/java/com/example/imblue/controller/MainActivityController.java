package com.example.imblue.controller;


import com.example.imblue.view.MainActivity;
import com.example.imblue.model.LocalStorage;
import com.example.imblue.model.dao.CategoriaRoomDAO;
import com.example.imblue.model.dao.PensamientoRoomDAO;
import com.example.imblue.model.pojo.Categoria;
import com.example.imblue.model.pojo.Pensamiento;

import java.util.Date;
import java.util.List;

public class MainActivityController {
    private CategoriaRoomDAO categoriaRoomDAO;
    private PensamientoRoomDAO pensamientoRoomDAO;

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

    public void reportar(MainActivity mainActivity, String titulo, String descripcion, String categoriaName) {
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
        pensamiento.setFecha(new Date());
        this.pensamientoRoomDAO.insertPensamiento(pensamiento);

        mainActivity.reporteSucceed();
    }

    public void eliminar(MainActivity mainActivity, String id){
        this.pensamientoRoomDAO = LocalStorage
                .getLocalStorage(mainActivity.getApplicationContext()).pensamientoRoomDAO();

        Pensamiento pensamiento = this.pensamientoRoomDAO.getPensamientoById(id);
        this.pensamientoRoomDAO.deletePensamiento(pensamiento);

        mainActivity.deleteSucceed();
    }

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

        Pensamiento pensamiento = this.pensamientoRoomDAO.getPensamientoById(id);
        pensamiento.setTitulo(titulo);
        pensamiento.setDescripcion(descripcion);
        this.pensamientoRoomDAO.updatePensamiento(pensamiento);

        mainActivity.editSucceed();
    }

}

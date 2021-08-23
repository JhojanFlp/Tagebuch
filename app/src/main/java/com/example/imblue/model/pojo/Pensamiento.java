package com.example.imblue.model.pojo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "pensamientos")
public class Pensamiento {
    @PrimaryKey
    @NonNull
    private String id;
    @NonNull
    private String titulo;
    @NonNull
    private String descripcion;
    @NonNull
    private Date fecha;
    @NonNull
    private String categoriaId;

    public Pensamiento() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }
}

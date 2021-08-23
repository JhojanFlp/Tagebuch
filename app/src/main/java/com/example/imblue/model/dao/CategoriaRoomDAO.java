package com.example.imblue.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.imblue.model.pojo.Categoria;

import java.util.List;

@Dao
public interface CategoriaRoomDAO {
    @Query("SELECT * FROM categorias")
    List<Categoria> getCategorias();

    @Query("SELECT * FROM categorias LIMIT 1")
    Categoria getCategoria();

    @Query("SELECT * FROM categorias WHERE id = :id LIMIT 1")
    Categoria getCategoriaById(String id);

    @Query("SELECT * FROM categorias WHERE nombre = :nombre LIMIT 1")
    Categoria getCategoriaByName(String nombre);

    @Query("SELECT nombre FROM categorias")
    String[] getCategoriasByName();

    @Insert
    void insertCategoria(Categoria categoria);
}

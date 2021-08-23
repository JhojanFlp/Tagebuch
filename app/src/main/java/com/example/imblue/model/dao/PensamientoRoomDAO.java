package com.example.imblue.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.imblue.model.pojo.Pensamiento;

import java.util.List;

@Dao
public interface PensamientoRoomDAO {
    @Query("SELECT * FROM pensamientos")
    List<Pensamiento> getPensamientos();

    @Query("SELECT * FROM pensamientos WHERE id = :id")
    Pensamiento getPensamientoById(String id);

    @Insert
    void insertPensamiento(Pensamiento pensamiento);

    @Update
    void updatePensamiento(Pensamiento pensamiento);

    @Delete
    void deletePensamiento(Pensamiento pensamiento);
}

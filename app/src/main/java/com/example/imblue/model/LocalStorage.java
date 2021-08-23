package com.example.imblue.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.imblue.model.pojo.Categoria;
import com.example.imblue.model.pojo.Pensamiento;
import com.example.imblue.model.dao.CategoriaRoomDAO;
import com.example.imblue.model.dao.PensamientoRoomDAO;
import com.example.imblue.model.converter.Converter;

@Database(entities = {Categoria.class, Pensamiento.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class LocalStorage extends RoomDatabase {
    // Singleton pattern design
    public abstract PensamientoRoomDAO pensamientoRoomDAO();
    public abstract CategoriaRoomDAO categoriaRoomDAO();
    private static LocalStorage localStorage;

    public static LocalStorage getLocalStorage(final Context context) {
        if(localStorage == null) {
            localStorage = Room.databaseBuilder(context, LocalStorage.class,"imBlue_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return localStorage;
    }

    // private LocalStorage(){}
}

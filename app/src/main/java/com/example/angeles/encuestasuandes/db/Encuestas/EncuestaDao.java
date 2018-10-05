package com.example.angeles.encuestasuandes.db.Encuestas;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Angeles on 10/2/2018.
 */
@Dao
public interface EncuestaDao {
    @Query("SELECT * FROM encuesta")
    List<Encuesta> getAllEncuesta();
    @Query("SELECT * FROM encuesta WHERE enid=:enId LIMIT 1")
    Encuesta getOneEncuesta(int enId);
    @Query("SELECT * FROM encuesta WHERE name=:name LIMIT 1")
    Encuesta getOneEncuestabyname(String name);

    @Insert
    void insertAll(Encuesta... encuestas);


    @Query("DELETE FROM encuesta")
    void deleteAll();

    @Update
    void update(Encuesta encuesta);

    @Query("SELECT * FROM encuesta WHERE enid=:encuesta_id")
    Encuesta getEncuestaById(int encuesta_id);

}

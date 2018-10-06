package com.example.angeles.encuestasuandes.db.Usuario;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Angeles on 10/6/2018.
 */
@Dao
public interface CareerDao {
    @Query("SELECT * FROM career")
    List<Career> getAllProfile();
    @Query("SELECT * FROM career WHERE career_id=:careerID LIMIT 1")
    Career getOneCareer(int careerID);

    @Insert
    void insertAll(Career... careers);


    @Query("DELETE FROM career")
    void deleteAll();

    @Update
    void update(Career career);
    @Query("SELECT * FROM career WHERE name=:name LIMIT 1")
    Career getOneCareerbyName(String name);


}

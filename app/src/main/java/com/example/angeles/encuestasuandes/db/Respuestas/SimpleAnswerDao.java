package com.example.angeles.encuestasuandes.db.Respuestas;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.angeles.encuestasuandes.db.Preguntas.OpenQuestion;

import java.util.List;

/**
 * Created by Angeles on 10/6/2018.
 */
@Dao
public interface SimpleAnswerDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(SimpleAnswer... simpleAnswers);


    @Query("DELETE FROM SimpleAnswer")
    void deleteAll();
}

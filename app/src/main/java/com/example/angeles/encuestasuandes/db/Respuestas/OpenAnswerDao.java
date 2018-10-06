package com.example.angeles.encuestasuandes.db.Respuestas;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

/**
 * Created by Angeles on 10/6/2018.
 */
@Dao
public interface OpenAnswerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(OpenAnswer... openAnswers);


    @Query("DELETE FROM openanswer")
    void deleteAll();
}

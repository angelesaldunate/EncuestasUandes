package com.example.angeles.encuestasuandes.db.Preguntas;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Angeles on 10/2/2018.
 */
@Dao
public interface MultipleQuestionDao {
    @Query("SELECT * FROM multiplequestion")
    List<MultipleQuestion> getAllMultipleQ();
    @Query("SELECT * FROM multiplequestion WHERE multiple_q_id=:ml_id LIMIT 1")
    MultipleQuestion getOneMultipleQuestion(int ml_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(MultipleQuestion... multipleQuestions);


    @Query("DELETE FROM multiplequestion")
    void deleteAll();
}

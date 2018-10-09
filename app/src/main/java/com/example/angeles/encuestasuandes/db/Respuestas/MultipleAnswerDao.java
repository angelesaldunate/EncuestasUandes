package com.example.angeles.encuestasuandes.db.Respuestas;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.angeles.encuestasuandes.db.Preguntas.OpenQuestion;

import java.util.List;

/**
 * Created by Angeles on 10/3/2018.
 */
@Dao
public interface MultipleAnswerDao {
    @Query("SELECT * FROM multipleanswer")
    List<MultipleAnswer> getAllMultipleAnswer();

    @Query("SELECT * FROM MultipleAnswer WHERE multiple_answer_id=:ans_id LIMIT 1")
    MultipleAnswer getOneMultipleAnswer(int ans_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(MultipleAnswer... multipleAnswers);


    @Query("DELETE FROM MultipleAnswer")
    void deleteAll();
}

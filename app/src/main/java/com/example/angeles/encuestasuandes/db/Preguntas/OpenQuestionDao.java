package com.example.angeles.encuestasuandes.db.Preguntas;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.graphics.Path;

import java.util.List;

/**
 * Created by Angeles on 10/2/2018.
 */
@Dao
public interface OpenQuestionDao {
    @Query("SELECT * FROM openquestion")
    List<OpenQuestion> getAllOpenQ();

    @Query("SELECT * FROM openquestion WHERE open_q_id=:op_id LIMIT 1")
    OpenQuestion getOneOpenQuestion(int op_id);

    @Query("SELECT * FROM openquestion WHERE eId=:eId ")
    List<OpenQuestion> getAllOpenbyEncuestaid(int eId);

    @Query("SELECT open_q_id FROM openquestion WHERE eId=:eId ")
    List<Integer> getAllIdOpenbyEncuestaid(int eId);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(OpenQuestion... openQuestions);
    @Insert
    void insert(OpenQuestion openQuestion);

    @Query("DELETE FROM openquestion")
    void deleteAll();
}

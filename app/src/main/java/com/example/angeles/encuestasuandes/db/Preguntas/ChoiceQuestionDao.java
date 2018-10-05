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
public interface ChoiceQuestionDao {
    @Query("SELECT * FROM choicequestion")
    List<ChoiceQuestion> getAllChoiceQ();
    @Query("SELECT * FROM choicequestion WHERE choice_q_id=:ch_id LIMIT 1")
    ChoiceQuestion getOneChoiceQuestion(int ch_id);

    @Query("SELECT * FROM choicequestion WHERE eId=:eId ")
    List<ChoiceQuestion> getAllChoicebyEncuestaid(int eId);
    @Query("SELECT choice_q_id FROM choicequestion WHERE eId=:eId ")
    List<Integer> getAllIdChoicebyEncuestaid(int eId);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(ChoiceQuestion... choiceQuestions);


    @Query("DELETE FROM choicequestion")
    void deleteAll();
}

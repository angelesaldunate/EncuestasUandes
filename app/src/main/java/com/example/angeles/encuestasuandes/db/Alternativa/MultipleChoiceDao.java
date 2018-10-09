package com.example.angeles.encuestasuandes.db.Alternativa;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.angeles.encuestasuandes.db.Preguntas.MultipleQuestion;
import com.example.angeles.encuestasuandes.db.Usuario.User;

import java.util.List;

/**
 * Created by Angeles on 10/2/2018.
 */
@Dao
public interface MultipleChoiceDao {
    @Query("SELECT * FROM multiplechoice")
    List<MultipleChoice> getAllMultipleChoice();

    @Query("SELECT * FROM multiplechoice WHERE multiple_choice_id=:m_id LIMIT 1")
    MultipleChoice getOneMultipleChoice(int m_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(MultipleChoice... multipleChoices);


    @Query("DELETE FROM MultipleChoice")
    void deleteAll();

    @Query("SELECT multiple_choice_id FROM multiplechoice WHERE multipleQId=:multipleqId ")
    List<Integer> getAllIdMuChoicebyQuestion(int multipleqId);

    @Query("SELECT content FROM multiplechoice WHERE multipleQId=:choiceqId ")
    List<String> getAllcontentMChoicebyQuestion(int choiceqId);

}

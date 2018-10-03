package com.example.angeles.encuestasuandes.db.Alternativa;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.angeles.encuestasuandes.db.Usuario.User;

import java.util.List;

/**
 * Created by Angeles on 10/2/2018.
 */
@Dao
public interface SimpleChoiceDao {
    @Query("SELECT * FROM simplechoice")
    List<SimpleChoice> getAllSimpleChoices();
    @Query("SELECT * FROM simplechoice WHERE simple_choice_id=:s_id LIMIT 1")
    User getOneSimpleChoice(int s_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(SimpleChoice... simpleChoices);


    @Query("DELETE FROM SimpleChoice")
    void deleteAll();
}

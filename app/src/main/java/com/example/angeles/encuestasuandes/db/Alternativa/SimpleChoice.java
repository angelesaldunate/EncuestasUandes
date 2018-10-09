package com.example.angeles.encuestasuandes.db.Alternativa;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.angeles.encuestasuandes.db.Preguntas.ChoiceQuestion;
import com.example.angeles.encuestasuandes.db.Preguntas.MultipleQuestion;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Angeles on 10/2/2018.
 */
@Entity
        (foreignKeys = {@ForeignKey(entity = ChoiceQuestion.class,
                parentColumns = "choice_q_id",
                childColumns = "choiceQId",
                onDelete = CASCADE)})
public class SimpleChoice {
    @PrimaryKey(autoGenerate = true)
    private int simple_choice_id;
    @ColumnInfo(name = "content")
    private String content;
    private int choiceQId;


    public int getSimple_choice_id() {
        return simple_choice_id;
    }

    public void setSimple_choice_id(int simple_choice_id) {
        this.simple_choice_id = simple_choice_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public int getChoiceQId() {
        return choiceQId;
    }

    public void setChoiceQId(int choiceQId) {
        this.choiceQId = choiceQId;
    }

}

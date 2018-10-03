package com.example.angeles.encuestasuandes.db.Alternativa;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.angeles.encuestasuandes.db.Preguntas.MultipleQuestion;
import com.example.angeles.encuestasuandes.db.Usuario.User;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Angeles on 10/2/2018.
 */
@Entity
        (foreignKeys = {@ForeignKey(entity = MultipleQuestion.class,
                parentColumns = "multiple_q_id",
                childColumns = "multipleQId",
                onDelete = CASCADE)})
public class MultipleChoice {
    @PrimaryKey(autoGenerate = true)
    private int multiple_choice_id;
    @ColumnInfo(name = "content")
    private String content;
    private int multipleQId;


    public int getMultiple_choice_id() {
        return multiple_choice_id;
    }

    public void setMultiple_choice_id(int multiple_choice_id) {
        this.multiple_choice_id = multiple_choice_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public int getMultipleQId() {
        return multipleQId;
    }

    public void setMultipleQId(int multipleQId) {
        this.multipleQId = multipleQId;
    }


}

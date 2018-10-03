package com.example.angeles.encuestasuandes.db.Respuestas;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.angeles.encuestasuandes.db.Preguntas.OpenQuestion;
import com.example.angeles.encuestasuandes.db.Usuario.User;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Angeles on 10/2/2018.
 */
@Entity
        (foreignKeys = {@ForeignKey(entity = User.class,
                parentColumns = "uid",
                childColumns = "userId",
                onDelete = CASCADE), @ForeignKey(entity = OpenQuestion.class,
                parentColumns = "multiple_choice_id",
                childColumns = "multiple_choice_id",
                onDelete = CASCADE)})
public class MultipleAnswer {

    public int getMultiple_answer_id() {
        return multiple_answer_id;
    }

    public void setMultiple_answer_id(int multiple_answer_id) {
        this.multiple_answer_id = multiple_answer_id;
    }

    @PrimaryKey(autoGenerate = true)
    private int multiple_answer_id;



}

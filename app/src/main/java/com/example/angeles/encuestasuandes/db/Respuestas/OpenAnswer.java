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
                parentColumns = "open_q_id",
                childColumns = "openQId",
                onDelete = CASCADE)})
public class OpenAnswer {
    @PrimaryKey(autoGenerate = true)
    private int open_answer_id;

    @ColumnInfo(name = "answer")
    private String answer;
    private int openQId;
    private int userId;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int getOpenQId() {
        return openQId;
    }

    public void setOpenQId(int openQId) {
        this.openQId = openQId;
    }



    public int getOpen_answer_id() {
        return open_answer_id;
    }

    public void setOpen_answer_id(int open_answer_id) {
        this.open_answer_id = open_answer_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }



}

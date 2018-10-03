package com.example.angeles.encuestasuandes.db.Preguntas;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.angeles.encuestasuandes.db.Encuestas.Encuesta;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Angeles on 10/2/2018.
 */
@Entity
        (foreignKeys = {@ForeignKey(entity = Encuesta.class,
                parentColumns = "enid",
                childColumns = "enid",
                onDelete = CASCADE)})
public class ChoiceQuestion {

    public int getChoice_q_id() {
        return choice_q_id;
    }

    public void setChoice_q_id(int choice_q_id) {
        this.choice_q_id = choice_q_id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    @PrimaryKey(autoGenerate = true)
    private int choice_q_id;

    @ColumnInfo(name = "enunciado")
    private String enunciado;
}

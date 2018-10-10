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
                childColumns = "eId",
                onDelete = CASCADE)})
public class OpenQuestion {
    @PrimaryKey(autoGenerate = true)
    private int open_q_id;
    @ColumnInfo(name = "enunciado")
    private String enunciado;
    private int eId;

    public OpenQuestion(String enunciado, int eId) {
        this.enunciado = enunciado;
        this.eId = eId;
    }

    public OpenQuestion() {
    }

    public int getEId() {
        return eId;
    }

    public void setEId(int eId) {
        this.eId = eId;
    }


    public int getOpen_q_id() {
        return open_q_id;
    }

    public void setOpen_q_id(int open_q_id) {
        this.open_q_id = open_q_id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }


}

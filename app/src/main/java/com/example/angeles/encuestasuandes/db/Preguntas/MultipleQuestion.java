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
public class MultipleQuestion {
    public int getMultiple_q_id() {
        return multiple_q_id;
    }

    public void setMultiple_q_id(int multiple_q_id) {
        this.multiple_q_id = multiple_q_id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    @PrimaryKey(autoGenerate = true)
    private int multiple_q_id;

    @ColumnInfo(name = "enunciado")
    private String enunciado;
}

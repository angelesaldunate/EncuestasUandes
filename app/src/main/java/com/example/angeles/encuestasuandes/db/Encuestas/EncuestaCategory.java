package com.example.angeles.encuestasuandes.db.Encuestas;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.angeles.encuestasuandes.db.GrupoUsuario.Category;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity
        (foreignKeys = {@ForeignKey(entity = Encuesta.class,
                parentColumns = "enid",
                childColumns = "enId",
                onDelete = CASCADE), @ForeignKey(entity = Category.class,
                parentColumns = "category_id",
                childColumns = "categoryId",
                onDelete = CASCADE)})
public class EncuestaCategory {
    @PrimaryKey(autoGenerate = true)
    private int encuesta_category_id;
    private int enId;
    private int categoryId;

    public int getEncuesta_category_id() {
        return encuesta_category_id;
    }

    public void setEncuesta_category_id(int encuesta_category_id) {
        this.encuesta_category_id = encuesta_category_id;
    }

    public int getEnId() {
        return enId;
    }

    public void setEnId(int enId) {
        this.enId = enId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}

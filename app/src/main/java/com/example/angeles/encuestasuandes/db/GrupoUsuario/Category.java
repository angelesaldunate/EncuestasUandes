package com.example.angeles.encuestasuandes.db.GrupoUsuario;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Angeles on 10/2/2018.
 */
@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int category_id;

    @ColumnInfo(name= "name")
    private String name;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @ColumnInfo(name= "selected")
    private boolean selected;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

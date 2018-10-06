package com.example.angeles.encuestasuandes.db.Usuario;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Angeles on 10/5/2018.
 */
@Entity
public class Career {


    @PrimaryKey(autoGenerate = true)
    private int career_id;

    @ColumnInfo(name = "name")
    private String name;

    public int getCareer_id() {
        return career_id;
    }

    public void setCareer_id(int career_id) {
        this.career_id = career_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

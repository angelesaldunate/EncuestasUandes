package com.example.angeles.encuestasuandes.db.Usuario;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Angeles on 5/30/2018.
 */


@Entity
        (foreignKeys = {@ForeignKey(entity = User.class,
                parentColumns = "uid",
                childColumns = "userId",
                onDelete = CASCADE), @ForeignKey(entity = Career.class,
                parentColumns = "career_id",
                childColumns = "career_id",
                onDelete = CASCADE)})
public class Profile {

    @PrimaryKey(autoGenerate = true)
    private int prid;

    @ColumnInfo(name = "name")
    private String name;
    private int career_id;
    @ColumnInfo(name = "last_name")
    private String last_name;
    @ColumnInfo(name = "rut")
    private String rut;
    @ColumnInfo(name = "gender")
    private String gender;
    @ColumnInfo(name = "birthdate")
    private String birthdate;
    private int userId;

    public Profile(String name, int career_id, String last_name, String rut,
                   String gender, String birthdate, int userId) {
        this.name = name;
        this.career_id = career_id;
        this.last_name = last_name;
        this.rut = rut;
        this.gender = gender;
        this.birthdate = birthdate;
        this.userId = userId;
    }

    public Profile() {
    }

    public int getCareer_id() {
        return career_id;
    }

    public void setCareer_id(int career_id) {
        this.career_id = career_id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPrid() {
        return prid;
    }

    public void setPrid(int prid) {
        this.prid = prid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

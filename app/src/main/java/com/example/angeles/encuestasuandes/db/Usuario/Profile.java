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
                onDelete = CASCADE)})
public class Profile {

    @PrimaryKey (autoGenerate = true)
    private int prid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "phone")
    private String phone;

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

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    @ColumnInfo(name = "last_name")
    private String last_name;

    @ColumnInfo(name = "rut")
    private String rut;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "birthdate")
    private String birthdate;

    @ColumnInfo(name = "career")
    private String career;




    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;





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
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}

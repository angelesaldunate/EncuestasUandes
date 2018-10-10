package com.example.angeles.encuestasuandes.Classes;

import android.content.SharedPreferences;

import com.example.angeles.encuestasuandes.db.AppDatabase;
import com.example.angeles.encuestasuandes.db.Usuario.Profile;

import java.util.ArrayList;

/**
 * Created by Angeles on 6/29/2018.
 */

public interface iComunicator {
    public SharedPreferences getSharedPreferences();

    public CredentialManage getCredentialManage();

    public AppDatabase getDb();

    public String getDate(Long time);

    public void updateProfile(Profile profile);

    public void finishEncuesta(int ide_encuesta);
    public  void addMultiple( ArrayList<Integer> list);
    public void addSimple(int ide_simple);
    public void addOpen(int ide_open, String respuesta);
    public void getallencuestas();




    }

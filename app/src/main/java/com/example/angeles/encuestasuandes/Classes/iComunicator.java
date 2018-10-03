package com.example.angeles.encuestasuandes.Classes;

import android.content.SharedPreferences;

import com.example.angeles.encuestasuandes.db.AppDatabase;

/**
 * Created by Angeles on 6/29/2018.
 */

public interface iComunicator {
    public SharedPreferences getSharedPreferences();
    public CredentialManage getCredentialManage();
    public AppDatabase getDb();
    public String getDate(Long time);

}
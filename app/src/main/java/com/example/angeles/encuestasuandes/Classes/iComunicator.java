package com.example.angeles.encuestasuandes.Classes;

import android.content.SharedPreferences;

import com.example.angeles.encuestasuandes.db.AppDatabase;
import com.example.angeles.encuestasuandes.db.Usuario.Profile;

/**
 * Created by Angeles on 6/29/2018.
 */

public interface iComunicator {
    public SharedPreferences getSharedPreferences();
    public CredentialManage getCredentialManage();
    public AppDatabase getDb();
    public String getDate(Long time);
    public void updateProfile(Profile profile);


}

package com.example.angeles.encuestasuandes.Classes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Angeles on 5/23/2018.
 */

public class CredentialManage {

    static String preferencesFileName = "Credentials";
    private static CredentialManage mInstance;
    private static Context mCtx;
    private Activity activity;
    private SharedPreferences preferences;


    CredentialManage(Context context) {
        this.mCtx = context;
        preferences = context.getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE);
    }

    public static synchronized CredentialManage getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CredentialManage(context.getApplicationContext());
        }
        return mInstance;

    }

    public void guardarCredenciales(String email, String password, String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email_guardado", email);
        editor.putString("password_guardada", password);

        editor.putString("auth_token", token);
        editor.apply();
    }

    public boolean verificarCredenciales() {
        String value1 = preferences.getString("email_guardado", null);
        String value2 = preferences.getString("password_guardada", null);
        String value3 = preferences.getString("auth_token", null);
        return value1 != null || value2 != null || value3 != null;
    }


    public String getAuthToken() {
        String token = preferences.getString("auth_token", null);

        return token;
    }

    public void setAuthToken(String authToken) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("auth_token", authToken);
        editor.apply();

    }

    public void saveFirebaseToken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("firebase_token", token);
        editor.apply();
    }

    public String getFirebaseToken() {
        String token = preferences.getString("firebase_token", null);
        return token;
    }

    public void borrarCredenciales() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getEmail() {
        return preferences.getString("email_guardado", null);
    }

    public String getName() {
        return preferences.getString("nombre_guardado", null);
    }


}
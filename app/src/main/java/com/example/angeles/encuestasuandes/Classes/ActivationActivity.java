package com.example.angeles.encuestasuandes.Classes;

import android.content.Context;
import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.angeles.encuestasuandes.ParaHacerRequest.NetworkManager;
import com.example.angeles.encuestasuandes.R;

import org.json.JSONObject;

public class ActivationActivity extends AppCompatActivity {

    NetworkManager networkManager;
    TextView email_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        networkManager = NetworkManager.getInstance(this);
        Button sendActivation = (Button) findViewById(R.id.activate_button);

        email_text_view = (TextView) findViewById(R.id.email_activation);
        sendActivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email_text_view.getText().toString();
                Context context = getApplicationContext();

                int duration = Toast.LENGTH_SHORT;

                networkManager.activateEmail(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        int status = response.optInt("status");
                        if (status == 200) {
                            String enviado = "Se ha enviado un mail con tu contraseña a: ";
                            Toast toast = Toast.makeText(getApplicationContext(), enviado + mail, duration);
                            toast.show();
                            finish();
                        } else {
                            String message = response.optString("message");
                            Toast toast = Toast.makeText(getApplicationContext(), message, duration);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Error de red", duration);
                        toast.show();
                    }
                }, mail);

            }
        });

    }

}

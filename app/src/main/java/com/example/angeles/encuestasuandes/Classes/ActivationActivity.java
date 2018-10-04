package com.example.angeles.encuestasuandes.Classes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angeles.encuestasuandes.R;

public class ActivationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        Button sendActivation = (Button) findViewById(R.id.activate_button);
        sendActivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) findViewById(R.id.email_activation);
                String mail = textView.getText().toString();
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                String enviado = "Se ha enviado un mail con tu contraseña a: ";

                Toast toast = Toast.makeText(context, enviado+mail, duration);
                toast.show();

                //se termina la actividad
                finish();
            }
        });

    }

}

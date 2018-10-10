package com.example.angeles.encuestasuandes.Classes;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.angeles.encuestasuandes.R;
import com.example.angeles.encuestasuandes.db.AppDatabase;
import com.example.angeles.encuestasuandes.db.Usuario.Profile;
import com.example.angeles.encuestasuandes.db.Usuario.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class EditProfileFragment extends Fragment  implements DatePickerDialog.OnDateSetListener  {
    private static AppDatabase appDatabase;
    private iComunicator mListener;
    private CredentialManage credentialManager;


    Handler handler = new Handler();
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


    }

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        credentialManager = mListener.getCredentialManage();
        appDatabase = mListener.getDb();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }
    ProgressDialog progress;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final EditText name = (EditText) view.findViewById(R.id.user_profile_name_edit);
        final EditText rut = (EditText) view.findViewById(R.id.rut_ingresado_edit);
        final RadioButton femenino = (RadioButton) view.findViewById(R.id.radioButton_female);
        final RadioButton masculino = (RadioButton) view.findViewById(R.id.radioButton_male);
        Button edit_birth_button = view.findViewById(R.id.edit_birth_button);
        final EditText fecha = (EditText) view.findViewById(R.id.fecha_nacimiento_ingresada_edit);
        final TextView mail = (TextView) view.findViewById(R.id.user_mail_edit);
        Button aceptar = (Button) view.findViewById(R.id.ok_changes_button);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final User current_user = appDatabase.userDao().getOneUser(credentialManager.getEmail());
                final Profile current_profile = appDatabase.profileDao().getOneProfile(current_user.getUid());
                edit_birth_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int start_year = 2018;
                        int start_month = 10;
                        int start_day =1;

                        if(current_profile.getBirthdate()!=null){
                   //         try {
                           //     Date t = Date.parse(current_profile.getBirthdate());
                             //   fecha.setText(current_profile.getBirthdate());

                    //        }
                      //      catch(ParseException e){
                      //          e.printStackTrace();
                     //       }

                        }
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String new_birth_date =year+"-"+month+"-"+dayOfMonth;
                                fecha.setText(new_birth_date);
                            }
                        }, start_year, start_month,start_day);
                        datePickerDialog.show();
                    }
                });
                if (current_profile != null) {
                    Handler mainHandler = new Handler(getActivity().getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            current_profile.setGender(null);
                            current_profile.setBirthdate(null);
                            current_profile.setName(null);
                            current_profile.setLast_name(null);
                            current_profile.setRut(null);

                            String without_field = "Sin asignar";

                            if (current_profile.getName()!=null && current_profile.getName()!=null){
                                name.setText(current_profile.getName().toString() + " " + current_profile.getLast_name().toString());


                            }
                            else{

                                name.setText(without_field);

                            }

                            mail.setText(credentialManager.getEmail());

                            if( rut !=null){


                                rut.setText(current_profile.getRut());
                            }
                            else{

                                rut.setText(without_field);
                            }

                            if(current_profile.getBirthdate()!=null){

                                    fecha.setText(current_profile.getBirthdate());

                            }
                            else{

                                fecha.setText(without_field);

                            }

                            if(current_profile.getGender()!=null){
                                if (current_profile.getGender().equals("Femenino")) {
                                    femenino.setChecked(true);
                                } else if (current_profile.getGender().equals("Masculino")){
                                    masculino.setChecked(true);
                                }
                            }

                        }
                    });
                }

            }
        }).start();

        aceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                progress = ProgressDialog.show(getContext(), "Actualizando","Por favor, espere", true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        User current = appDatabase.userDao().getOneUser(credentialManager.getEmail());
                        Profile perfil = appDatabase.profileDao().getOneProfile(current.getUid());
                        if (perfil != null) {
                            perfil.setRut(rut.getText().toString());

                            String[] parts = name.getText().toString().split("\\s");

                            if (parts.length > 1) {
                                perfil.setName(parts[0]);
                                String last_name = parts[1];
                                perfil.setLast_name(last_name);

                            } else {
                                perfil.setName(parts[0]);
                                perfil.setLast_name(" ");
                            }
                            perfil.setBirthdate(fecha.getText().toString());
                            boolean checked2 = ((RadioButton) getView().findViewById(R.id.radioButton_female)).isChecked();
                            boolean checked1 =  ((RadioButton) getView().findViewById(R.id.radioButton_male)).isChecked();
                            if (checked2) {
                                perfil.setGender("Femenino");
                            } else if (checked1) {
                                perfil.setGender("Masculino");
                            }else{
                                perfil.setGender("Sin asignar");

                            }

                            mListener.updateProfile(perfil);
                        }

                    }
                }).start();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof iComunicator) {
            mListener = (iComunicator) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progress!=null){
            progress.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

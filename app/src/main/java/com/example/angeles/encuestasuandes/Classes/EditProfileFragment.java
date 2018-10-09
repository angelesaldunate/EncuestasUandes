package com.example.angeles.encuestasuandes.Classes;


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

import java.util.Calendar;


public class EditProfileFragment extends Fragment {
    private static AppDatabase appDatabase;
    private iComunicator mListener;
    private CredentialManage credentialManager;


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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final EditText name = (EditText) view.findViewById(R.id.user_profile_name_edit);
        final EditText rut = (EditText) view.findViewById(R.id.rut_ingresado_edit);
        final RadioButton femenino = (RadioButton) view.findViewById(R.id.radioButton_female);
        final RadioButton masculino = (RadioButton) view.findViewById(R.id.radioButton_male);

        final EditText fecha = (EditText) view.findViewById(R.id.fecha_nacimiento_ingresada_edit);
        final TextView mail = (TextView) view.findViewById(R.id.user_mail_edit);
        Button aceptar = (Button) view.findViewById(R.id.ok_changes_button);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final User current_user = appDatabase.userDao().getOneUser(credentialManager.getEmail());
                final Profile current_profile = appDatabase.profileDao().getOneProfile(current_user.getUid());
                if (current_profile != null) {
                    Handler mainHandler = new Handler(getActivity().getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            name.setText(current_profile.getName().toString() + " " + current_profile.getLast_name().toString());
                            mail.setText(credentialManager.getEmail());
                            rut.setText(current_profile.getRut());
                            fecha.setText(current_profile.getBirthdate());
                            if (current_profile.getGender().equals("Femenino")) {
                                femenino.setChecked(true);
                            } else {
                                masculino.setChecked(true);
                            }

                        }
                    });
                }

            }
        }).start();

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            if (checked2) {
                                perfil.setGender("Femenino");
                            } else {
                                perfil.setGender("Masculino");
                            }


                            mListener.updateProfile(perfil);
                        }
                        Fragment fr = new ProfileFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fr).addToBackStack("null").commit();

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


}

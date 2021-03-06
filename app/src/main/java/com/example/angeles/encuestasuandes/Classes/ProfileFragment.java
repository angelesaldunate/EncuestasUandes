package com.example.angeles.encuestasuandes.Classes;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.angeles.encuestasuandes.R;
import com.example.angeles.encuestasuandes.db.AppDatabase;
import com.example.angeles.encuestasuandes.db.Usuario.Profile;
import com.example.angeles.encuestasuandes.db.Usuario.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProfileFragment extends Fragment {
    private static AppDatabase appDatabase;
    private iComunicator mListener;
    private CredentialManage credentialManager;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        credentialManager = mListener.getCredentialManage();
        appDatabase = mListener.getDb();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final TextView name = (TextView) view.findViewById(R.id.user_profile_name);
        final TextView mail = (TextView) view.findViewById(R.id.user_mail);
        final TextView rut = (TextView) view.findViewById(R.id.rut_ingresado);
        final TextView genero = (TextView) view.findViewById(R.id.genero_ingresado);
        final TextView fecha = (TextView) view.findViewById(R.id.fecha_nacimiento_ingresada);
        final TextView carrera = (TextView) view.findViewById(R.id.career_ingresado);

        Button editar = (Button) view.findViewById(R.id.edit_profile);

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


                            String without_field = "Sin asignar";

                            if(current_profile.getLast_name()==null){

                                name.setText(without_field);
                            }else{
                                name.setText(current_profile.getName().toString() + " " + current_profile.getLast_name().toString());
                            }

                            mail.setText(credentialManager.getEmail().toString());

                            if(current_profile.getRut() == null){
                                rut.setText(without_field);
                            }
                            else{
                                rut.setText(current_profile.getRut().toString());
                            }
                            if (current_profile.getGender() == null){

                                genero.setText(without_field);
                            }else{
                                genero.setText(current_profile.getGender().toString());
                            }
                            if (current_profile.getBirthdate() == null){

                                fecha.setText(without_field);

                            }else{
                                fecha.setText(current_profile.getBirthdate().toString());

                            }
                            carrera.setText(current_profile.getCareer_id());

                        }
                    });
                }

            }
        }).start();

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fragment).addToBackStack("null").commit();

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

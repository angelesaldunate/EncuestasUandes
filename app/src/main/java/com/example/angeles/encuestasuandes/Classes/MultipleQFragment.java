package com.example.angeles.encuestasuandes.Classes;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angeles.encuestasuandes.R;
import com.example.angeles.encuestasuandes.db.AppDatabase;
import com.example.angeles.encuestasuandes.db.Encuestas.Encuesta;
import com.example.angeles.encuestasuandes.db.Respuestas.MultipleAnswer;
import com.example.angeles.encuestasuandes.db.Respuestas.SimpleAnswer;
import com.example.angeles.encuestasuandes.db.Usuario.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MultipleQFragment extends Fragment {
    private static AppDatabase appDatabase;
    ArrayList<Integer> cantidad_p_multiple;
    ArrayList<Integer> cantidad_p_abierta;
    ArrayList<Integer> cantidad_p_alternativa;
    int id_actual;
    int encuesta_id;
    private iComunicator mListener;
    private CredentialManage credentialManager;


    public MultipleQFragment() {
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
        return inflater.inflate(R.layout.fragment_multiple_q, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            encuesta_id = bundle.getInt("encuesta_id");
            id_actual = bundle.getInt("id_actual");
            cantidad_p_multiple = bundle.getIntegerArrayList("cantidad_Pmultiple");
            cantidad_p_abierta = bundle.getIntegerArrayList("cantidad_Pabierta");
            cantidad_p_alternativa = bundle.getIntegerArrayList("cantidad_Palternativa");
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Integer> indices = appDatabase.multipleChoiceDao().getAllIdMuChoicebyQuestion(id_actual);
                final List<String> contents = appDatabase.multipleChoiceDao().getAllcontentMChoicebyQuestion(id_actual);
                final String enunciado_str = appDatabase.multipleQuestionDao().getOneMultipleQuestion(id_actual).getEnunciado();
                Handler mainHandler = new Handler(getActivity().getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView enunciado = (TextView) view.findViewById(R.id.enunciado_pregunta_m);
                        enunciado.setText(enunciado_str);
                        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.linear_preguntas_m);
                        for (int i = 0; i < indices.size(); i++) {

                            generateAlternative(indices.get(i), contents.get(i));

                        }


                    }
                });
            }
        }).start();


        Button button_ok = (Button) view.findViewById(R.id.button_ok_multiple);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final List<Integer> indices = appDatabase.multipleChoiceDao().getAllIdMuChoicebyQuestion(id_actual);
                        User current = appDatabase.userDao().getOneUser(credentialManager.getEmail());
                        int count = 0;
                        for (int i = 0; i < indices.size(); i++) {
                            CheckBox cb = (CheckBox) view.findViewById(indices.get(i));
                            if (cb.isChecked()) {
                                count += 1;
                                MultipleAnswer ma = new MultipleAnswer();
                                ma.setMultipleChoiceId(indices.get(i));
                                ma.setUserId(current.getUid());

                                appDatabase.multipleAnswerDao().insertAll(ma);
                            }

                        }
                        final int qt = count;
                        Handler mainHandler = new Handler(getActivity().getMainLooper());
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (qt == 0) {
                                    CheckBox cb = (CheckBox) view.findViewById(indices.get(0));
                                    cb.setError("Necesitas seleccionar al menos uno");
                                    cb.requestFocus();
                                } else {
                                    GetOtherQ(cantidad_p_abierta, cantidad_p_multiple, cantidad_p_alternativa, encuesta_id);

                                }
                            }
                        });


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

    public void GetOtherQ(ArrayList<Integer> all_index_open, ArrayList<Integer> all_index_multiple, ArrayList<Integer> all_index_choice, int id_encuesta) {


        if (cantidad_p_multiple.size() == 0 && cantidad_p_abierta.size() == 0 && cantidad_p_alternativa.size() == 0) {
            Toast toast = Toast.makeText(getContext(), "Se ha respondido toda la Encuesta", Toast.LENGTH_SHORT);
            toast.show();
            Fragment fr = new AllEncuestasFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fr).addToBackStack("null").commit();
        } else {
            Bundle bund = new Bundle();
            Fragment fragment;
            if (all_index_open.size() > 0) {

                fragment = new OpenQFragment();
                bund.putInt("encuesta_id", id_encuesta);
                int primer_index = all_index_open.get(0);
                bund.putInt("id_actual", primer_index);
                all_index_open.remove(0);
                bund.putIntegerArrayList("cantidad_Pmultiple", all_index_multiple);
                bund.putIntegerArrayList("cantidad_Pabierta", all_index_open);
                bund.putIntegerArrayList("cantidad_Palternativa", all_index_choice);
                fragment.setArguments(bund);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fragment).addToBackStack("null").commit();

            } else if (all_index_choice.size() > 0) {

                fragment = new SimpleChQFragment();
                bund.putInt("encuesta_id", id_encuesta);
                int primer_index = all_index_choice.get(0);
                bund.putInt("id_actual", primer_index);
                all_index_choice.remove(0);
                bund.putIntegerArrayList("cantidad_Pmultiple", all_index_multiple);
                bund.putIntegerArrayList("cantidad_Pabierta", all_index_open);
                bund.putIntegerArrayList("cantidad_Palternativa", all_index_choice);
                fragment.setArguments(bund);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fragment).addToBackStack("null").commit();


            } else if (all_index_multiple.size() > 0) {
                fragment = new MultipleQFragment();
                bund.putInt("encuesta_id", id_encuesta);
                int primer_index = all_index_multiple.get(0);
                bund.putInt("id_actual", primer_index);
                all_index_multiple.remove(0);
                bund.putIntegerArrayList("cantidad_Pmultiple", all_index_multiple);
                bund.putIntegerArrayList("cantidad_Pabierta", all_index_open);
                bund.putIntegerArrayList("cantidad_Palternativa", all_index_choice);
                fragment.setArguments(bund);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fragment).addToBackStack("null").commit();


            }
        }

    }

    public void generateAlternative(int index, String content) {
        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.linear_preguntas_m);


        CheckBox alternativa = new CheckBox(getContext());
        int ide_question = index;
        alternativa.setId(ide_question);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                5,
                getResources().getDisplayMetrics()
        );
        layoutParams.setMargins(px, px, px, px);
        int padding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                20,
                getResources().getDisplayMetrics()
        );
        int elevation = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                4,
                getResources().getDisplayMetrics()
        );

        alternativa.setLayoutParams(layoutParams);
        alternativa.setBackgroundColor(getResources().getColor(R.color.colorwhite));
        alternativa.setClickable(true);
        alternativa.setElevation(elevation);
        alternativa.setPadding(padding, padding, padding, padding);
        alternativa.setText(content);

        linearLayout.addView(alternativa);

    }


}

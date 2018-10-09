package com.example.angeles.encuestasuandes.Classes;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angeles.encuestasuandes.R;
import com.example.angeles.encuestasuandes.db.AppDatabase;
import com.example.angeles.encuestasuandes.db.Respuestas.OpenAnswer;
import com.example.angeles.encuestasuandes.db.Usuario.User;

import java.util.ArrayList;
import java.util.List;

public class OpenQFragment extends Fragment {
    private static AppDatabase appDatabase;
    ArrayList<Integer> cantidad_p_multiple;
    ArrayList<Integer> cantidad_p_abierta;
    ArrayList<Integer> cantidad_p_alternativa;
    int encuesta_id;
    int id_actual;
    private iComunicator mListener;
    private CredentialManage credentialManager;


    public OpenQFragment() {
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
        return inflater.inflate(R.layout.fragment_open_q, container, false);
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
                final String enunciado_str = appDatabase.openQuestionDao().getOneOpenQuestion(id_actual).getEnunciado();
                Handler mainHandler = new Handler(getActivity().getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView enunciado = (TextView) view.findViewById(R.id.enunciado_pregunta_o);
                        enunciado.setText(enunciado_str);


                    }
                });
            }
        }).start();


        Button button_ok = (Button) view.findViewById(R.id.button_ok_open);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText respuesta = (EditText) view.findViewById(R.id.open_answer);

                if (TextUtils.isEmpty(respuesta.getText())) {
                    Handler mainHandler = new Handler(getActivity().getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            respuesta.setError(getString(R.string.error_field_required));
                            respuesta.requestFocus();
                        }
                    });
                } else {

                    String texto = respuesta.getText().toString();
                    GetOtherQ(cantidad_p_abierta, cantidad_p_multiple, cantidad_p_alternativa, encuesta_id, texto);
                }


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

    public void GetOtherQ(ArrayList<Integer> all_index_open, ArrayList<Integer> all_index_multiple, ArrayList<Integer> all_index_choice, int id_encuesta, final String respuesta) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User current = appDatabase.userDao().getOneUser(credentialManager.getEmail());
                OpenAnswer oa = new OpenAnswer();
                oa.setUserId(current.getUid());
                oa.setOpenQId(id_actual);
                oa.setAnswer(respuesta);
                appDatabase.openAnswerDao().insertAll(oa);
            }
        }).start();


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
}

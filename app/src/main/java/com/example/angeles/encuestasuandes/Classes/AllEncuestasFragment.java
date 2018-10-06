package com.example.angeles.encuestasuandes.Classes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.angeles.encuestasuandes.R;
import com.example.angeles.encuestasuandes.db.Alternativa.MultipleChoice;
import com.example.angeles.encuestasuandes.db.AppDatabase;
import com.example.angeles.encuestasuandes.db.Encuestas.Encuesta;
import com.example.angeles.encuestasuandes.db.Preguntas.ChoiceQuestion;
import com.example.angeles.encuestasuandes.db.Preguntas.MultipleQuestion;
import com.example.angeles.encuestasuandes.db.Preguntas.OpenQuestion;

import java.util.ArrayList;
import java.util.List;


public class AllEncuestasFragment extends Fragment {
    private static final String DATABASE_NAME = "encuestas_db";
    private static AppDatabase appDatabase;
    private List<Encuesta> all;
    private iComunicator mListener;
    private CredentialManage credentialManager;
    private List<Encuesta> filtered_encuestas;

    public AllEncuestasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        credentialManager = mListener.getCredentialManage();
        appDatabase = mListener.getDb();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){



        final View final_view = view;
        final ListView lv = (ListView) view.findViewById(R.id.list_allEncuestas);


        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                all = appDatabase.encuestaDao().getAllEncuesta();


                Handler mainHandler = new Handler(getActivity().getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //llenando la lista
                        final EncuestasAdapter adapter = new EncuestasAdapter(getContext(), all);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                final Encuesta encuesta_seleccionada = adapter.getItem(i);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        List<Integer> all_choice = appDatabase.choiceQuestionDao().getAllIdChoicebyEncuestaid(encuesta_seleccionada.getEnid());
                                        ArrayList<Integer> all_index_choice = new ArrayList<>(all_choice);
                                        List<Integer> all_multiple = appDatabase.multipleQuestionDao().getAllIdMChoicebyEncuestaid(encuesta_seleccionada.getEnid());
                                        ArrayList<Integer> all_index_multiple = new ArrayList<>(all_multiple);
                                        List<Integer> all_open = appDatabase.openQuestionDao().getAllIdOpenbyEncuestaid(encuesta_seleccionada.getEnid());
                                        ArrayList<Integer> all_index_open = new ArrayList<>(all_open);


                                        Bundle bund = new Bundle();
                                        Fragment fragment;
                                        if (all_index_open.size()>0){

                                          fragment = new OpenQFragment();
                                            bund.putInt("encuesta_id", encuesta_seleccionada.getEnid());
                                            int primer_index = all_index_open.get(0);
                                            bund.putInt("id_actual", primer_index);
                                            all_index_open.remove(0);
                                            bund.putIntegerArrayList("cantidad_Pmultiple",all_index_multiple);
                                            bund.putIntegerArrayList("cantidad_Pabierta", all_index_open);
                                            bund.putIntegerArrayList("cantidad_Palternativa",all_index_choice );
                                            fragment.setArguments(bund);
                                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fragment).addToBackStack("null").commit();

                                        }else if(all_index_choice.size()>0){

                                            fragment = new SimpleChQFragment();
                                            bund.putInt("encuesta_id", encuesta_seleccionada.getEnid());
                                            int primer_index = all_index_choice.get(0);
                                            bund.putInt("id_actual", primer_index);
                                            all_index_choice.remove(0);
                                            bund.putIntegerArrayList("cantidad_Pmultiple",all_index_multiple);
                                            bund.putIntegerArrayList("cantidad_Pabierta", all_index_open);
                                            bund.putIntegerArrayList("cantidad_Palternativa",all_index_choice );
                                            fragment.setArguments(bund);
                                           getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fragment).addToBackStack("null").commit();


                                        }
                                        else if(all_index_multiple.size()>0){
                                            fragment = new MultipleQFragment();
                                            bund.putInt("encuesta_id", encuesta_seleccionada.getEnid());
                                            int primer_index = all_index_multiple.get(0);
                                            bund.putInt("id_actual", primer_index);
                                            all_index_multiple.remove(0);
                                            bund.putIntegerArrayList("cantidad_Pmultiple",all_index_multiple);
                                            bund.putIntegerArrayList("cantidad_Pabierta", all_index_open);
                                            bund.putIntegerArrayList("cantidad_Palternativa",all_index_choice );
                                            fragment.setArguments(bund);
                                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fragment).addToBackStack("null").commit();


                                        }




                                    }
                                }).start();

                            }
                        });

                    }
                });

            }
        }) .start();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_encuestas, container, false);
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

package com.example.angeles.encuestasuandes.Classes;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.angeles.encuestasuandes.R;
import com.example.angeles.encuestasuandes.db.AppDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleChQFragment extends Fragment {
    private static AppDatabase appDatabase;
    private iComunicator mListener;
    private CredentialManage credentialManager;
    ArrayList<Integer> cantidad_p_multiple;
    ArrayList<Integer> cantidad_p_abierta;
    ArrayList<Integer> cantidad_p_alternativa;
    int encuesta_id;


    public SimpleChQFragment() {
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
        return inflater.inflate(R.layout.fragment_simple_ch_q, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            encuesta_id = bundle.getInt("encuesta_id");
            int id_actual = bundle.getInt("id_actual");
            cantidad_p_multiple = bundle.getIntegerArrayList("cantidad_Pmultiple");
            cantidad_p_abierta = bundle.getIntegerArrayList("cantidad_Pabierta");
            cantidad_p_alternativa = bundle.getIntegerArrayList("cantidad_Palternativa");
        }


        Button button_ok = (Button) view.findViewById(R.id.button_ok_simple);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cantidad_p_multiple.size()==0 && cantidad_p_abierta.size()==0 && cantidad_p_alternativa.size()==0){
                    Toast toast = Toast.makeText(getContext(), "Se ha respondido toda la Encuesta",Toast.LENGTH_SHORT );
                    toast.show();
                    Fragment fr = new AllEncuestasFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fr).addToBackStack("null").commit();
                }else{
                    GetOtherQ(cantidad_p_abierta,cantidad_p_multiple,cantidad_p_alternativa,encuesta_id);
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
    public void GetOtherQ(ArrayList<Integer> all_index_open, ArrayList<Integer> all_index_multiple, ArrayList<Integer> all_index_choice, int id_encuesta){
        Bundle bund = new Bundle();
        Fragment fragment;
        if (all_index_open.size()>0){

            fragment = new OpenQFragment();
            bund.putInt("encuesta_id", id_encuesta);
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
            bund.putInt("encuesta_id", id_encuesta);
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
            bund.putInt("encuesta_id", id_encuesta);
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

}

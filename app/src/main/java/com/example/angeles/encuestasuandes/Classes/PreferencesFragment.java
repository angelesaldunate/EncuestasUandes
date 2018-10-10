package com.example.angeles.encuestasuandes.Classes;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.angeles.encuestasuandes.ParaHacerRequest.NetworkManager;
import com.example.angeles.encuestasuandes.R;
import com.example.angeles.encuestasuandes.db.AppDatabase;
import com.example.angeles.encuestasuandes.db.Encuestas.Encuesta;
import com.example.angeles.encuestasuandes.db.GrupoUsuario.Category;
import com.example.angeles.encuestasuandes.db.Usuario.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PreferencesFragment extends Fragment {
    private static AppDatabase appDatabase;
    Handler handler = new Handler();
    private iComunicator mListener;
    private CredentialManage credentialManager;
    private NetworkManager networkManager;
    private View inflatedView;

    public PreferencesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        credentialManager = mListener.getCredentialManage();
        appDatabase = mListener.getDb();
        networkManager = NetworkManager.getInstance(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_preferences, container, false);
        TextView categories_text_view = inflatedView.findViewById(R.id.categorias);
        categories_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configure_categories();
            }
        });
        return inflatedView;
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {


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

    void configure_categories() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // String array for alert dialog multi choice items

        Thread thread = new Thread() {
            public void run() {

                    networkManager.getCategories(new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            JSONArray categories_json_array = response.optJSONArray("categories");
                            Gson gson = new Gson();
                            Category[] categories = gson.fromJson(categories_json_array.toString(), Category[].class);
                            Thread t = new Thread() {

                                @Override
                                public void run() {
                                    for (Category c : categories) {
                                        Category older = appDatabase.categoryDao().getCategorybyId(c.getId());

                                        if (older != null) {
                                            older.setSelected(c.isSelected());
                                            older.setName(c.getName());
                                            appDatabase.categoryDao().updateCategory(older);
                                        } else {
                                            appDatabase.categoryDao().insert(c);

                                        }
                                    }


                                    String[] names = new String[categories.length];

                                    for (int i = 0; i < categories.length; i++) {

                                        names[i] = categories[i].getName();
                                    }
                                    boolean[] checked_cats = new boolean[categories.length];

                                    for (int i = 0; i < categories.length; i++) {

                                        checked_cats[i] = categories[i].isSelected();
                                    }


                                    // Boolean array for initial selected items

                                    // Convert the color array to list
                                    final List<String> namesList = Arrays.asList(names);

                                    // Set multiple choice items for alert dialog
                /*
                    AlertDialog.Builder setMultiChoiceItems(CharSequence[] items, boolean[]
                    checkedItems, DialogInterface.OnMultiChoiceClickListener listener)
                        Set a list of items to be displayed in the dialog as the content,
                        you will be notified of the selected item via the supplied listener.
                 */
                /*
                    DialogInterface.OnMultiChoiceClickListener
                    public abstract void onClick (DialogInterface dialog, int which, boolean isChecked)

                        This method will be invoked when an item in the dialog is clicked.

                        Parameters
                        dialog The dialog where the selection was made.
                        which The position of the item in the list that was clicked.
                        isChecked True if the click checked the item, else false.
                 */
                                    builder.setMultiChoiceItems(names, checked_cats, new DialogInterface.OnMultiChoiceClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                                            // Update the current focused item's checked status
                                            checked_cats[which] = isChecked;

                                            // Get the current focused item
                                            String currentItem = namesList.get(which);

                                            // Notify the current action
                                            Toast.makeText(getContext(),
                                                    currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    // Specify the dialog is not cancelable
                                    builder.setCancelable(false);

                                    // Set a title for alert dialog
                                    builder.setTitle("Seleccione categorías de interés");

                                    // Set the positive/yes button click listener
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do something when click positive button
                                            // tv.setText("Your preferred colors..... \n");
                                            ArrayList<Integer> interest_categories_ids = new ArrayList<>();
                                            JSONObject payload = new JSONObject();
                                            JSONArray interest_categories_ja = new JSONArray();
                                            for (int i = 0; i < checked_cats.length; i++) {
                                                boolean checked = checked_cats[i];
                                                categories[i].setSelected(checked);
                                                if (checked) {
                                                    //          tv.setText(tv.getText() + colorsList.get(i) + "\n");

                                                    interest_categories_ids.add(categories[i].getId());
                                                    interest_categories_ja.put(categories[i].getId());
                                                }
                                            }

                                            try {
                                                payload.put("interest_categories", interest_categories_ja);
                                            } catch (JSONException e) {

                                                e.printStackTrace();
                                            }
                                            networkManager.updateInterestedCategories(new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {


                                                    Thread t = new Thread() {


                                                        @Override
                                                        public void run() {
                                                            appDatabase.categoryDao().updateCategories(categories);
                                                        }
                                                    };
                                                    t.start();

                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                }
                                            }, payload);


                                        }
                                    });

                                    // Set the negative/no button click listener
                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do something when click the negative button
                                        }
                                    });

                                    // Set the neutral/cancel button click listener
                                    builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do something when click the neutral button
                                        }
                                    });


                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            AlertDialog dialog = builder.create();
                                            // Display the alert dialog on interface
                                            if(categories.length!=0){
                                            dialog.show();}
                                            else
                                                {

                                                    Toast.makeText(getContext(),"No hay categorías para mostrar",Toast.LENGTH_LONG).show();
                                            }
                                        }

                                    });


                                }
                            };
                            t.start();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });

            }
        };

        thread.start();


    }
}

package com.example.angeles.encuestasuandes.Classes;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.angeles.encuestasuandes.R;
import com.example.angeles.encuestasuandes.db.AppDatabase;
import com.example.angeles.encuestasuandes.db.Encuestas.Encuesta;
import com.example.angeles.encuestasuandes.db.Premio.Price;
import com.example.angeles.encuestasuandes.db.Premio.UserPrice;
import com.example.angeles.encuestasuandes.db.Usuario.User;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class SorteoFragment extends Fragment {
    private static AppDatabase appDatabase;
    private List<Price> all;
    private iComunicator mListener;
    private CredentialManage credentialManager;
    private List<Encuesta> filtered_encuestas;


    public SorteoFragment() {
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
        return inflater.inflate(R.layout.fragment_sorteo, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {


        final View final_view = view;
        final ListView lv = (ListView) view.findViewById(R.id.list_allPremios);
        final TextView price_name = (TextView) view.findViewById(R.id.nombre_premio);
        final TextView days = (TextView) view.findViewById(R.id.dias);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                boolean is = true;
                final User current = appDatabase.userDao().getOneUser(credentialManager.getEmail());

                all = appDatabase.priceDao().getAllPrice(is);
                UserPrice selected = appDatabase.userPriceDao().getOneUserPrice(current.getUid());
                if (selected != null) {

                    final Price last_selected = appDatabase.priceDao().getPricebyId(selected.getPriceId());
                    String secs = new Timestamp(System.currentTimeMillis()).toString();
                    final int timeDiff = difference(last_selected.getEnd_date(), secs);
                    Handler mainHandler = new Handler(getActivity().getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            price_name.setText(last_selected.getName());
                            days.setText(Integer.toString(timeDiff));
                        }
                    });
                } else {
                    String secs = new Timestamp(System.currentTimeMillis()).toString();
                    final int timeDiff = difference(all.get(0).getEnd_date(), secs);
                    Handler mainHandler = new Handler(getActivity().getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            days.setText(Integer.toString(timeDiff));
                        }
                    });

                }

                Handler mainHandler = new Handler(getActivity().getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        //llenando la lista
                        final PriceAdapter adapter = new PriceAdapter(getContext(), all);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                final Price price = adapter.getItem(i);

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {


                                        UserPrice newprice = new UserPrice();
                                        UserPrice oldprice = appDatabase.userPriceDao().getUserPriceByUserId(current.getUid());
                                        if (oldprice != null) {
                                            oldprice.setPriceId(price.getPrice_id());
                                            appDatabase.userPriceDao().update(oldprice);
                                            Fragment fm = new SorteoFragment();
                                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fm).addToBackStack("null").commit();

                                        } else {

                                            newprice.setPriceId(price.getPrice_id());
                                            newprice.setUserId(current.getUid());
                                            appDatabase.userPriceDao().insertAll(newprice);

                                            Fragment fm = new SorteoFragment();
                                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fm).addToBackStack("null").commit();
                                        }

                                    }
                                }).start();

                            }
                        });

                    }
                });

            }
        }).start();


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

    public int difference(String date2, String current) {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date date = sdf.parse(current);
            cal1.setTime(date);
            date = sdf.parse(date2);
            cal2.setTime(date);
        } catch (ParseException e) {              // Insert this block.
            e.printStackTrace();
        }


        Date d1, d2;
        d1 = cal1.getTime();
        d2 = cal2.getTime();
        int frequency = (int) (TimeUnit.DAYS.convert(d2.getTime() - d1.getTime(), TimeUnit.MILLISECONDS));
        return frequency;
    }
}

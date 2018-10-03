package com.example.angeles.encuestasuandes.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.angeles.encuestasuandes.R;
import com.example.angeles.encuestasuandes.db.AppDatabase;
import com.example.angeles.encuestasuandes.db.Encuestas.Encuesta;
import com.example.angeles.encuestasuandes.db.Premio.Price;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by Angeles on 4/3/2018.
 */

public class PriceAdapter extends ArrayAdapter<Price> {
    private iComunicator mListener;
    private AppDatabase appDatabase;
    private LayoutInflater lf;
    private Handler mHandler = new Handler();


    public PriceAdapter(Context context, List<Price> forms) {
        super(context, 0, forms);
        lf = LayoutInflater.from(context);
        mListener = (iComunicator) context;
        appDatabase = mListener.getDb();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)  {
        // Get the data item for this position
        final Price price = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        convertView = lf.inflate(R.layout.list_price_adapter, parent, false);



        // Lookup view for data population
        final TextView namePrice = (TextView) convertView.findViewById(R.id.priceName);
        final TextView descriptionPrice = convertView.findViewById(R.id.price_description);
        new Thread(new Runnable() {
            @Override
            public void run() {

                Handler mainHandler = new Handler(getContext().getMainLooper());
                mainHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {

                        namePrice.setText(price.getName());
                        descriptionPrice.setText(price.getDescription());



                    }
                });
            }}).start();

        // Return the completed view to render on screen
        return convertView;
    }



}
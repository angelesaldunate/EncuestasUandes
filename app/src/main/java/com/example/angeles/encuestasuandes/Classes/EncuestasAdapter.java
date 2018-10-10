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

public class EncuestasAdapter extends ArrayAdapter<Encuesta> {
    private iComunicator mListener;
    private AppDatabase appDatabase;
    private LayoutInflater lf;
    private List<ViewHolder> lstHolders;
    private Handler mHandler = new Handler();
    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (lstHolders) {
                Calendar cal = Calendar.getInstance();
                long offset = cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
                long currentTime = System.currentTimeMillis() + offset;
                for (ViewHolder holder : lstHolders) {
                    holder.updateTimer(currentTime);
                }
            }
        }
    };

    public EncuestasAdapter(Context context, List<Encuesta> forms) {
        super(context, 0, forms);
        lf = LayoutInflater.from(context);
        lstHolders = new ArrayList<>();
        mListener = (iComunicator) context;
        appDatabase = mListener.getDb();
        startUpdateTimer();
    }

    private void startUpdateTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(updateRemainingTimeRunnable);
            }
        }, 1000, 1000);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ViewHolder holder = null;
        final Encuesta encuesta = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = lf.inflate(R.layout.list_encuesta_adapter, parent, false);
            holder.tvTimer = (TextView) convertView.findViewById(R.id.timerTextView);
            convertView.setTag(holder);
            synchronized (lstHolders) {
                lstHolders.add(holder);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.setData(encuesta);

        // Lookup view for data population
        final TextView nameEncuesta = (TextView) convertView.findViewById(R.id.driverTextView);
        final TextView descriptionEncuesta = convertView.findViewById(R.id.originTextView);
        final TextView scoreEncuesta = convertView.findViewById(R.id.destinationTextView);
        new Thread(new Runnable() {
            @Override
            public void run() {

                Handler mainHandler = new Handler(getContext().getMainLooper());
                mainHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {

                        nameEncuesta.setText(encuesta.getName());
                        descriptionEncuesta.setText(encuesta.getDescription());

                        scoreEncuesta.setText(Integer.toString(encuesta.getScore()));


                    }
                });
            }
        }).start();

        // Return the completed view to render on screen
        return convertView;
    }

    private class ViewHolder {
        TextView tvTimer;
        Encuesta encuesta;

        public void setData(Encuesta encuesta) {
            this.encuesta = encuesta;
            updateTimer(System.currentTimeMillis());

        }

        public void updateTimer(long currentTime) {
            String secs = new Timestamp(System.currentTimeMillis()).toString();


            int timeDiff = difference(encuesta.getEnd_date(), secs);


            if (timeDiff > 0) {

                tvTimer.setText(Integer.toString(timeDiff));
            } else {

                tvTimer.setText("Expirado!!");
            }
        }

        public int difference(String date2, String current) {
            Calendar cal1 = new GregorianCalendar();
            Calendar cal2 = new GregorianCalendar();


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                Date date = sdf.parse(current);
                cal1.setTime(date);
                date = sdf2.parse(date2);
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
}
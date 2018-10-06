package com.example.angeles.encuestasuandes.Classes;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import com.example.angeles.encuestasuandes.R;
import com.example.angeles.encuestasuandes.db.Alternativa.MultipleChoice;
import com.example.angeles.encuestasuandes.db.Alternativa.SimpleChoice;
import com.example.angeles.encuestasuandes.db.AppDatabase;
import com.example.angeles.encuestasuandes.db.Encuestas.Encuesta;
import com.example.angeles.encuestasuandes.db.Preguntas.ChoiceQuestion;
import com.example.angeles.encuestasuandes.db.Preguntas.MultipleQuestion;
import com.example.angeles.encuestasuandes.db.Premio.Price;
import com.example.angeles.encuestasuandes.db.Usuario.Career;
import com.example.angeles.encuestasuandes.db.Usuario.Profile;
import com.example.angeles.encuestasuandes.db.Usuario.User;

import java.util.Calendar;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, iComunicator  {
    static private AppDatabase appDatabase;
    static private SharedPreferences sharedPreferences;
    static private CredentialManage credentialManager;
    private static final String DATABASE_NAME = "encuestas_db";
    static final int SEND_MESSAGE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        credentialManager = new CredentialManage(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorligthBlue)));


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (!credentialManager.verificarCredenciales()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(EXTRA_MESSAGE, "Sent!");
            startActivityForResult(intent, SEND_MESSAGE);
        } else {
            setCredentialsOnHeader(credentialManager.getEmail());
        }

        // Creo encuestas
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (appDatabase.encuestaDao().getAllEncuesta().size() == 0) {
                    Encuesta nueva_enc = new Encuesta();
                    nueva_enc.setDescription("Esta descripcion trata de ...");
                    nueva_enc.setEnd_date(dateToTimestamp());
                    nueva_enc.setMax_responses(5);
                    nueva_enc.setScore(5);
                    nueva_enc.setName("Burguer");
                    appDatabase.encuestaDao().insertAll(nueva_enc);
                    Price new_price = new Price();
                    new_price.setDescription("Holaaaa descripcion");
                    new_price.setName("Blackberry");
                    new_price.setEnd_date(dateToTimestamp());
                    new_price.setIs_available(true);
                    appDatabase.priceDao().insertAll(new_price);
                    Price new_price2 = new Price();
                    new_price2.setDescription("Holaaaa descripcion");
                    new_price2.setName("Iphone");
                    new_price2.setEnd_date(dateToTimestamp());
                    new_price2.setIs_available(true);
                    appDatabase.priceDao().insertAll(new_price2);

                    Encuesta oth = appDatabase.encuestaDao().getOneEncuestabyname("Burguer");
                    Log.d("NOMBREEEEIDEE", oth.getName());

                    MultipleQuestion mt = new MultipleQuestion();
                    mt.setEId(oth.getEnid());
                    mt.setEnunciado("Enunciadomultiple1");
                    appDatabase.multipleQuestionDao().insertAll(mt);
                    MultipleQuestion mt1 = new MultipleQuestion();
                    mt1.setEnunciado("Enunciado Multiple 2");
                    mt1.setEId(oth.getEnid());
                    appDatabase.multipleQuestionDao().insertAll(mt1);
                    List<Integer> ch3 = appDatabase.multipleQuestionDao().getAllIdMChoicebyEncuestaid(oth.getEnid());
                    MultipleChoice mc = new MultipleChoice();
                    mc.setMultipleQId(ch3.get(0));
                    mc.setContent("M!111111");
                    appDatabase.multipleChoiceDao().insertAll(mc,mc);
                    MultipleChoice mc2 = new MultipleChoice();
                    mc2.setMultipleQId(ch3.get(1));
                    mc2.setContent("M!2222");

                    appDatabase.multipleChoiceDao().insertAll(mc2,mc2);

                    ChoiceQuestion ch = new ChoiceQuestion();
                    ch.setEId(oth.getEnid());
                    ch.setEnunciado("Este es el enunciadoo");
                    appDatabase.choiceQuestionDao().insertAll(ch);
                    List<Integer> ch2 = appDatabase.choiceQuestionDao().getAllIdChoicebyEncuestaid(oth.getEnid());
                    SimpleChoice sm = new SimpleChoice();
                    sm.setChoiceQId(ch2.get(0));
                    sm.setContent("HOlaaaaaaaaaa");
                    appDatabase.simpleChoiceDao().insertAll(sm);
                    SimpleChoice sm2 = new SimpleChoice();
                    sm2.setChoiceQId(ch2.get(0));
                    sm2.setContent("Chaooo");
                    appDatabase.simpleChoiceDao().insertAll(sm2);
                    Career cr = new Career();
                    cr.setName("ING");
                    appDatabase.careerDao().insertAll(cr);






                }
            }
        }).start();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment;

        int id = item.getItemId();

        if (id == R.id.nav_encuesta) {
            fragment = new AllEncuestasFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fragment).addToBackStack("null").commit();

        } else if (id == R.id.nav_sorteo) {
            fragment = new SorteoFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fragment).addToBackStack("null").commit();


        } else if (id == R.id.nav_perfil) {
            fragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fragment).addToBackStack("null").commit();


        } else if (id == R.id.nav_logout) {
            logOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEND_MESSAGE) {
            if (resultCode == RESULT_OK) {

                final String email = data.getStringExtra("email_devuelto");
                final String password = data.getStringExtra("password_devuelto");

                credentialManager.guardarCredenciales(email, password);


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (appDatabase.userDao().getOneUser(email) == null) {
                            appDatabase.userDao().insertAll(new User(email, password));
                            int ide = appDatabase.userDao().getOneUser(email).getUid();

                        } else {
                            User current = appDatabase.userDao().getOneUser(email);
                            Profile actual_profile = appDatabase.profileDao().getOneProfile(current.getUid());
                            setNameOnHeader(actual_profile.getName());
                        }


                    }
                }).start();
                setCredentialsOnHeader(email);


            }
        }
    }
    public void logOut() {
        credentialManager.borrarCredenciales();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Sent!");
        //iniciaractividad solo si no existe anteriormente
        startActivityForResult(intent, SEND_MESSAGE);

    }

    public void setCredentialsOnHeader(String email) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = (navigationView.getHeaderView(0));
        TextView textViewmail = headerView.findViewById(R.id.nav_email);
        textViewmail.setText(email);
        Fragment fragment = new AllEncuestasFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framnew, fragment).addToBackStack("null").commit();
    }
    public void setNameOnHeader( String name){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = (navigationView.getHeaderView(0));
        TextView textviewnombre = headerView.findViewById(R.id.nav_name);
        textviewnombre.setText(name);
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    @Override
    public CredentialManage getCredentialManage() {
        return credentialManager;
    }

    @Override
    public AppDatabase getDb() {
        return appDatabase;
    }

    @Override
    public String getDate(Long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time * 1000);
        cal.add(Calendar.HOUR, -4);
        String date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
        return date;
    }
    public String dateToTimestamp(){
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 23);// I might have the wrong Calendar constant...
        cal.set(Calendar.MONTH, 8);// -1 as month is zero-based
        cal.set(Calendar.YEAR, 2019);
        Timestamp tstamp = new Timestamp(cal.getTimeInMillis());
        return tstamp.toString();
    }
    public void insertProfile(final String name, final String lastLame, final String gender, final String birthdate, final String rut, final int userid){

        new Thread(new Runnable() {
            @Override
            public void run() {

                Profile perfiln =  new Profile();
                perfiln.setName(name);
                perfiln.setLast_name(lastLame);
                perfiln.setGender(gender);
                perfiln.setBirthdate(birthdate);
                perfiln.setRut(rut);
                perfiln.setUserId(userid);
                appDatabase.profileDao().insertAll(perfiln);

            }
        }).start();



    }


}

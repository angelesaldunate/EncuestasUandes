package com.example.angeles.encuestasuandes.Classes;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.angeles.encuestasuandes.ParaHacerRequest.NetworkManager;
import com.example.angeles.encuestasuandes.R;
import com.example.angeles.encuestasuandes.db.Alternativa.MultipleChoice;
import com.example.angeles.encuestasuandes.db.Alternativa.SimpleChoice;
import com.example.angeles.encuestasuandes.db.AppDatabase;
import com.example.angeles.encuestasuandes.db.Encuestas.Encuesta;
import com.example.angeles.encuestasuandes.db.Preguntas.ChoiceQuestion;
import com.example.angeles.encuestasuandes.db.Preguntas.MultipleQuestion;
import com.example.angeles.encuestasuandes.db.Preguntas.OpenQuestion;
import com.example.angeles.encuestasuandes.db.Premio.Price;
import com.example.angeles.encuestasuandes.db.Usuario.Profile;
import com.example.angeles.encuestasuandes.db.Usuario.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, iComunicator {
    static final int SEND_MESSAGE = 1;
    private static final String DATABASE_NAME = "encuestas_db";
    static private AppDatabase appDatabase;
    static private SharedPreferences sharedPreferences;
    static private CredentialManage credentialManager;
    ArrayList<ArrayList<Integer>> respuestas_multiples = new ArrayList<>();
    ArrayList<Integer> ide_respuestas_open = new ArrayList<>();
    ArrayList<String> respuesta_open = new ArrayList<>();
    ArrayList<Integer> respuestas_simple = new ArrayList<>();
    private NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        credentialManager = new CredentialManage(this);
        networkManager = NetworkManager.getInstance(this);

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

                    List<Integer> ch3 = appDatabase.multipleQuestionDao().getAllIdMChoicebyEncuestaid(oth.getEnid());
                    MultipleChoice mc = new MultipleChoice();
                    mc.setMultipleQId(ch3.get(0));
                    mc.setMultiple_choice_id(2);
                    mc.setContent("M!111111");
                    MultipleChoice mc2 = new MultipleChoice();
                    mc2.setMultipleQId(ch3.get(0));
                    mc2.setMultiple_choice_id(3);
                    mc2.setContent("M!2222");
                    appDatabase.multipleChoiceDao().insertAll(mc, mc2);


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
                    sm2.setSimple_choice_id(4);
                    sm2.setChoiceQId(ch2.get(0));
                    sm2.setContent("Chaooo");
                    appDatabase.simpleChoiceDao().insertAll(sm2);


                    OpenQuestion oq = new OpenQuestion();
                    oq.setEId(oth.getEnid());
                    oq.setOpen_q_id(1);
                    oq.setEnunciado("Cuentame como te sientes");
                    appDatabase.openQuestionDao().insertAll(oq);


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


        } else if (id == R.id.nav_settings) {
            fragment = new PreferencesFragment();
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

                //   credentialManager.guardarCredenciales(email, password);


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (appDatabase.userDao().getOneUser(email) == null) {
                            appDatabase.userDao().insertAll(new User(email, password));
                            int ide = appDatabase.userDao().getOneUser(email).getUid();
                            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    JSONObject profile_json_object = response.optJSONObject("profile_info");
                                    String first_name;
                                    String last_name;
                                    String career;
                                    String rut;
                                    String gender;
                                    String birthdate;
                                    int acc_score;
                                    try {
                                        first_name = profile_json_object.getString("first_name");
                                        last_name = profile_json_object.getString("last_name");
                                        career = profile_json_object.getString("career_id");
                                        rut = profile_json_object.getString("rut");
                                        gender = profile_json_object.getString("gender");
                                        birthdate = profile_json_object.getString("birthdate");
                                        acc_score = profile_json_object.getInt("accumulated_score");
                                        Profile profile = new Profile(first_name, career, last_name,
                                                rut, gender, birthdate, ide, acc_score);
                                        Thread t = new Thread() {
                                            @Override
                                            public void run() {
                                                appDatabase.profileDao().insert(profile);
                                            }
                                        };
                                        t.start();
                                        setNameOnHeader(profile.getName());
                                        setScoreOnHeader(profile.getAccumulated_score());

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            networkManager.getProfile(listener, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("asd", error.toString());
                                }
                            });

                        } else {
                            User current = appDatabase.userDao().getOneUser(email);
                            int aux = current.getUid();
                            Profile actual_profile = appDatabase.profileDao().getOneProfile(aux);
                            setNameOnHeader(actual_profile.getName());
                        }
                    }
                }).start();
                setCredentialsOnHeader(email);
            }
        }
    }

    public void getallencuestas() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.encuestaDao().deleteAll();
                appDatabase.multipleChoiceDao().deleteAll();
                appDatabase.multipleQuestionDao().deleteAll();
                appDatabase.simpleChoiceDao().deleteAll();
                appDatabase.choiceQuestionDao().deleteAll();
                appDatabase.openQuestionDao().deleteAll();
                Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray surveys_json_array = response.optJSONArray("surveys");
                        for (int i = 0; i < surveys_json_array.length(); i++) {
                            JSONObject survey;
                            String name;
                            String description;
                            int score;
                            String start_date;
                            String end_date;
                            int max_responses;
                            int min_responses;
                            try {
                                survey = surveys_json_array.getJSONObject(i);
                                name = survey.getString("name");
                                description = survey.getString("description");
                                score = survey.getInt("score");
                                start_date = survey.getString("start_date");
                                end_date = survey.getString("end_date");
                                max_responses = survey.getInt("max_answers");
                                min_responses = survey.getInt("min_answers");
                                Encuesta encuesta = new Encuesta(name, description, score,
                                        start_date, end_date, max_responses, min_responses);
                                Thread t = new Thread() {
                                    @Override
                                    public void run() {
                                        appDatabase.encuestaDao().insert(encuesta);
                                        JSONArray open_questions_array = survey.optJSONArray("OpenQuestions");
                                        for (int j = 0; j < open_questions_array.length(); j++) {
                                            JSONObject open_question;
                                            String statement;
                                            int survey_id;
                                            int oq_id;
                                            try {
                                                open_question = open_questions_array.getJSONObject(j);
                                                statement = open_question.getString("statement");
                                                survey_id = appDatabase.encuestaDao().getOneEncuestabyname(name).getEnid();
                                                oq_id = open_question.getInt("id");
                                                OpenQuestion openQuestion = new OpenQuestion(oq_id, statement, survey_id);
                                                Thread oq = new Thread() {
                                                    @Override
                                                    public void run() {
                                                        appDatabase.openQuestionDao().insert(openQuestion);
                                                    }
                                                };
                                                oq.start();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        JSONArray alt_questions_array = survey.optJSONArray("AlternativeQuestions");
                                        for (int j = 0; j < alt_questions_array.length(); j++) {
                                            JSONObject alt_question;
                                            String statement;
                                            int survey_id;
                                            int aq_id;
                                            try {
                                                alt_question = alt_questions_array.getJSONObject(j);
                                                statement = alt_question.getString("statement");
                                                survey_id = appDatabase.encuestaDao().getOneEncuestabyname(name).getEnid();
                                                aq_id = alt_question.getInt("id");
                                                ChoiceQuestion choiceQuestion = new ChoiceQuestion(aq_id, statement, survey_id);
                                                Thread aq = new Thread() {
                                                    @Override
                                                    public void run() {
                                                        long cid = appDatabase.choiceQuestionDao().insert(choiceQuestion);
                                                        JSONArray simple_choice_array = alt_question.optJSONArray("Alternatives");
                                                        for (int k = 0; k < simple_choice_array.length(); k++){
                                                            JSONObject alt;
                                                            int alt_question_id;
                                                            String content;
                                                            int ac_id;
                                                            try {
                                                                alt = simple_choice_array.getJSONObject(k);
                                                                alt_question_id =(int)cid;
                                                                content = alt.getString("content");
                                                                ac_id = alt.getInt("id");
                                                                SimpleChoice simpleChoice = new SimpleChoice(ac_id, content, alt_question_id);
                                                                Thread sa = new Thread() {
                                                                    @Override
                                                                    public void run() {
                                                                        appDatabase.simpleChoiceDao().insert(simpleChoice);
                                                                    }
                                                                };
                                                                sa.start();
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }

                                                        }
                                                    }
                                                };
                                                aq.start();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        JSONArray mult_questions_array = survey.optJSONArray("MultipleQuestions");
                                        for (int j = 0; j < mult_questions_array.length(); j++) {
                                            JSONObject mult_question;
                                            String statement;
                                            int survey_id;
                                            int mq_id;
                                            try {
                                                mult_question = mult_questions_array.getJSONObject(j);
                                                statement = mult_question.getString("statement");
                                                survey_id = appDatabase.encuestaDao().getOneEncuestabyname(name).getEnid();
                                                mq_id = mult_question.getInt("id");
                                                MultipleQuestion multipleQuestion = new MultipleQuestion(mq_id, statement, survey_id);
                                                Thread mq = new Thread() {
                                                    @Override
                                                    public void run() {
                                                        long mid = appDatabase.multipleQuestionDao().insert(multipleQuestion);
                                                        JSONArray multi_choice_array = mult_question.optJSONArray("MultipleAlternatives");
                                                        for (int k = 0; k < multi_choice_array.length(); k++){
                                                            JSONObject mult = null;
                                                            int mult_question_id;
                                                            String content;
                                                            int mc_id;
                                                            try {
                                                                mult = multi_choice_array.getJSONObject(k);
                                                                mult_question_id = (int)mid;
                                                                content = mult.getString("content");
                                                                mc_id = mult.getInt("id");
                                                                MultipleChoice multipleChoice = new MultipleChoice(mc_id, content, mult_question_id);
                                                                Thread ma = new Thread() {
                                                                    @Override
                                                                    public void run() {
                                                                        appDatabase.multipleChoiceDao().insert(multipleChoice);
                                                                    }
                                                                };
                                                                ma.start();
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }
                                                };
                                                mq.start();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                };
                                t.start();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                networkManager.getSurveys(listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("asd", error.toString());
                    }
                });
            }
        }).start();
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

    public void setNameOnHeader(String name) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = (navigationView.getHeaderView(0));
        TextView textviewnombre = headerView.findViewById(R.id.nav_name);
        textviewnombre.setText(name);
    }

    public void setScoreOnHeader(int score) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = (navigationView.getHeaderView(0));
        TextView textviewnombre = headerView.findViewById(R.id.nav_score);
        textviewnombre.setText(Integer.toString(score));

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

    public String dateToTimestamp() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 23);// I might have the wrong Calendar constant...
        cal.set(Calendar.MONTH, 8);// -1 as month is zero-based
        cal.set(Calendar.YEAR, 2019);
        Timestamp tstamp = new Timestamp(cal.getTimeInMillis());
        return tstamp.toString();
    }

    public void updateProfile(final Profile perfiln) {


        new Thread(new Runnable() {
            @Override
            public void run() {

                appDatabase.profileDao().update(perfiln);

            }
        }).start();
        setNameOnHeader(perfiln.getName());

    }

    public void addMultiple(ArrayList<Integer> list) {
        respuestas_multiples.add(list);
    }

    public void addSimple(int ide_simple) {
        respuestas_simple.add(ide_simple);
    }

    public void addOpen(int ide_open, String respuesta) {
        respuesta_open.add(respuesta);
        ide_respuestas_open.add(ide_open);
    }

    public void finishEncuesta(int ide_encuesta) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                Encuesta en = appDatabase.encuestaDao().getEncuestaById(ide_encuesta);
                User u = appDatabase.userDao().getOneUser(credentialManager.getEmail());
                Profile p = appDatabase.profileDao().getOneProfile(u.getUid());
                int score = p.getAccumulated_score();
                score += en.getScore();
                final int final_score = score;
                p.setAccumulated_score(score);
                appDatabase.profileDao().update(p);
                Handler mainHandler = new Handler(getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        setScoreOnHeader(final_score);
                    }
                });

                JSONObject encuesta = new JSONObject();
                try {
                    JSONArray open_questions = new JSONArray();
                    JSONArray simple_questions = new JSONArray();
                    JSONArray multiple_questions = new JSONArray();

                    for (int i = 0; i < respuesta_open.size(); i++) {
                        JSONObject open_q = new JSONObject();
                        open_q.put("id", ide_respuestas_open.get(i));
                        open_q.put("content", respuesta_open.get(i));
                        open_questions.put(open_q);
                    }

                    for (int i = 0; i < respuestas_multiples.size(); i++) {

                        multiple_questions.put(new JSONArray(respuestas_multiples.get(i)));

                    }
                    for (int i = 0; i < respuestas_simple.size(); i++) {
                        simple_questions.put(respuestas_simple.get(i));
                    }


                    encuesta.put("open_responses", open_questions);
                    encuesta.put("alternative_responses", simple_questions);
                    encuesta.put("multiple_responses", multiple_questions);


                    respuestas_multiples = new ArrayList<>();
                    ide_respuestas_open = new ArrayList<>();
                    respuesta_open = new ArrayList<>();
                    respuestas_simple = new ArrayList<>();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                networkManager.updateAnswers(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Response", response.getString("status"));
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, encuesta);
            }
        }).start();
    }
}

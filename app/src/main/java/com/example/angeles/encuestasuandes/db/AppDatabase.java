package com.example.angeles.encuestasuandes.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.angeles.encuestasuandes.db.Alternativa.MultipleChoice;
import com.example.angeles.encuestasuandes.db.Alternativa.MultipleChoiceDao;
import com.example.angeles.encuestasuandes.db.Alternativa.SimpleChoice;
import com.example.angeles.encuestasuandes.db.Alternativa.SimpleChoiceDao;
import com.example.angeles.encuestasuandes.db.Encuestas.Encuesta;
import com.example.angeles.encuestasuandes.db.Encuestas.EncuestaDao;
import com.example.angeles.encuestasuandes.db.GrupoUsuario.Category;
import com.example.angeles.encuestasuandes.db.Preguntas.ChoiceQuestion;
import com.example.angeles.encuestasuandes.db.Preguntas.ChoiceQuestionDao;
import com.example.angeles.encuestasuandes.db.Preguntas.MultipleQuestion;
import com.example.angeles.encuestasuandes.db.Preguntas.MultipleQuestionDao;
import com.example.angeles.encuestasuandes.db.Preguntas.OpenQuestion;
import com.example.angeles.encuestasuandes.db.Preguntas.OpenQuestionDao;
import com.example.angeles.encuestasuandes.db.Premio.Price;
import com.example.angeles.encuestasuandes.db.Premio.PriceDao;
import com.example.angeles.encuestasuandes.db.Premio.UserPrice;
import com.example.angeles.encuestasuandes.db.Premio.UserPriceDao;
import com.example.angeles.encuestasuandes.db.Usuario.Profile;
import com.example.angeles.encuestasuandes.db.Usuario.ProfileDao;
import com.example.angeles.encuestasuandes.db.Usuario.User;
import com.example.angeles.encuestasuandes.db.Usuario.UserDao;


/**
 * Created by Angeles on 5/30/2018.
 */


@Database(entities = {User.class, Profile.class, Encuesta.class, OpenQuestion.class, MultipleQuestion.class, ChoiceQuestion.class, MultipleChoice.class, SimpleChoice.class
, Price.class, UserPrice.class, Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract EncuestaDao encuestaDao();
    public abstract ProfileDao profileDao();
    public abstract PriceDao priceDao();
    public abstract UserPriceDao userPriceDao();
    public abstract OpenQuestionDao openQuestionDao();
    public abstract MultipleQuestionDao multipleQuestionDao();
    public abstract ChoiceQuestionDao choiceQuestionDao();
    public abstract MultipleChoiceDao multipleChoiceDao();
    public abstract SimpleChoiceDao simpleChoiceDao();



    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

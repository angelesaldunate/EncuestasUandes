package com.example.angeles.encuestasuandes.db.Premio;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.angeles.encuestasuandes.db.Usuario.Profile;

import java.util.List;

/**
 * Created by Angeles on 10/3/2018.
 */
@Dao
public interface UserPriceDao {
    @Query("SELECT * FROM UserPrice")
    List<UserPrice> getAllProfile();
    @Query("SELECT * FROM userprice WHERE user_price_id=:userId LIMIT 1")
    UserPrice getOneUserPrice(int userId);

    @Insert
    void insertAll(UserPrice... userPrices);


    @Query("DELETE FROM UserPrice")
    void deleteAll();

    @Update
    void update(UserPrice userPrice);

    @Query("SELECT * FROM userprice WHERE user_price_id=:userpid")
    UserPrice getUserPriceByUserId(int userpid);
}

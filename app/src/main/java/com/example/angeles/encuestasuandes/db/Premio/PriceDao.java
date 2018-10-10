package com.example.angeles.encuestasuandes.db.Premio;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Angeles on 10/3/2018.
 */
@Dao
public interface PriceDao {

    @Query("SELECT * FROM price WHERE is_available=:is_true")
    List<Price> getAllPrice(boolean is_true);

    @Query("SELECT * FROM price WHERE is_available=:is_true and end_date=:date")
    List<Price> getPriceGivenADate(boolean is_true, String date);

    @Query("SELECT * FROM price WHERE price_id=:priceIde")
    Price getPricebyId(int priceIde);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Price... prices);


    @Query("DELETE FROM price")
    void deleteAll();
}

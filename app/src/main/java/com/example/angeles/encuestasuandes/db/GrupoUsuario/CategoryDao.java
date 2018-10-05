package com.example.angeles.encuestasuandes.db.GrupoUsuario;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.angeles.encuestasuandes.db.Premio.Price;

import java.util.List;

/**
 * Created by Angeles on 10/4/2018.
 */
@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<Category> getAllCategory();


    @Query("SELECT * FROM category WHERE category_id=:categoryId")
    Category getCategorybyId(int categoryId);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Category... categories);


    @Query("DELETE FROM category")
    void deleteAll();
}

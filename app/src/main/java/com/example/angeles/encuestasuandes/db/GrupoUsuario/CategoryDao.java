package com.example.angeles.encuestasuandes.db.GrupoUsuario;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.angeles.encuestasuandes.db.Premio.Price;

import java.util.List;

/**
 * Created by Angeles on 10/4/2018.
 */
@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    Category[] getAllCategory();


    @Query("SELECT * FROM category WHERE id=:categoryId")
    Category getCategorybyId(int categoryId);



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Category... categories);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category category);

    @Update
    int updateCategories(Category[] categories);


    @Update
    int updateCategory(Category category);

    @Query("DELETE FROM category")
    void deleteAll();
}

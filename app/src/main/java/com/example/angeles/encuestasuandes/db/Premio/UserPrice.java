package com.example.angeles.encuestasuandes.db.Premio;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.angeles.encuestasuandes.db.Usuario.User;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Angeles on 10/3/2018.
 */
@Entity
        (foreignKeys = {@ForeignKey(entity = User.class,
                parentColumns = "uid",
                childColumns = "userId",
                onDelete = CASCADE), @ForeignKey(entity = Price.class,
                parentColumns = "price_id",
                childColumns = "priceId",
                onDelete = CASCADE)} )
public class UserPrice {
    public int getUser_price_id() {
        return user_price_id;
    }

    public void setUser_price_id(int user_price_id) {
        this.user_price_id = user_price_id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    @PrimaryKey(autoGenerate = true)
    private int user_price_id;
    private int userId;
    private int priceId;
}

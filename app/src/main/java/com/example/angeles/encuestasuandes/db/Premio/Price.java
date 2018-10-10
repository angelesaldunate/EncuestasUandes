package com.example.angeles.encuestasuandes.db.Premio;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Angeles on 10/3/2018.
 */
@Entity
public class Price {
    @ColumnInfo(name = "price_id")
    @PrimaryKey(autoGenerate = true)
    private int price_id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @ColumnInfo(name = "selected")
    private boolean selected;
    @ColumnInfo(name = "end_date")
    private String end_date;
    @ColumnInfo(name = "is_available")
    private boolean is_available;

    public Price(int price_id, String name, String description, boolean selected, String end_date, boolean is_available) {
        this.price_id = price_id;
        this.name = name;
        this.description = description;
        this.selected = selected;
        this.end_date = end_date;
        this.is_available = is_available;
    }


    public int getPrice_id() {
        return price_id;
    }

    public void setPrice_id(int price_id) {
        this.price_id = price_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }
}

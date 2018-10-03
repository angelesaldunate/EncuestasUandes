package com.example.angeles.encuestasuandes.db.Encuestas;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Angeles on 10/2/2018.
 */

@Entity
public class Encuesta {
    @PrimaryKey(autoGenerate = true)
    private int enid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;


    @ColumnInfo(name = "score")
    private int score;


    @ColumnInfo(name = "start_date")
    private String start_date;


    @ColumnInfo(name = "end_date")
    private String end_date;


    @ColumnInfo(name = "max_responses")
    private int max_responses;


    @ColumnInfo(name = "min_responses")
    private int min_responses;
    public int getEnid() {
        return enid;
    }

    public void setEnid(int enid) {
        this.enid = enid;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getMax_responses() {
        return max_responses;
    }

    public void setMax_responses(int max_responses) {
        this.max_responses = max_responses;
    }

    public int getMin_responses() {
        return min_responses;
    }

    public void setMin_responses(int min_responses) {
        this.min_responses = min_responses;
    }



}

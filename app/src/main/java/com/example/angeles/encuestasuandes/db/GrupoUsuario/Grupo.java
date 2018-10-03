package com.example.angeles.encuestasuandes.db.GrupoUsuario;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.angeles.encuestasuandes.db.Usuario.User;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Angeles on 10/2/2018.
 */
@Entity
        (foreignKeys = {@ForeignKey(entity = User.class,
                parentColumns = "category_id",
                childColumns = "categoryId",
                onDelete = CASCADE)})
public class Grupo {

    @PrimaryKey(autoGenerate = true)
    private int group_id;
    @ColumnInfo(name = "tag")
    private String tag;
    @ColumnInfo(name = "is_editable")
    private boolean is_editable;
    private int categoryId;



    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isIs_editable() {
        return is_editable;
    }

    public void setIs_editable(boolean is_editable) {
        this.is_editable = is_editable;
    }



}

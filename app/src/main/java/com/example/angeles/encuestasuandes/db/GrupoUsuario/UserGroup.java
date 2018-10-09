package com.example.angeles.encuestasuandes.db.GrupoUsuario;

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
                parentColumns = "uid",
                childColumns = "userId",
                onDelete = CASCADE), @ForeignKey(entity = Group.class,
                parentColumns = "group_id",
                childColumns = "groupId",
                onDelete = CASCADE)})
public class UserGroup {


    @PrimaryKey(autoGenerate = true)
    private int user_group_id;

    private int userId;
    private int groupId;


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUser_group_id() {
        return user_group_id;
    }

    public void setUser_group_id(int user_group_id) {
        this.user_group_id = user_group_id;
    }

}

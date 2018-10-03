package com.example.angeles.encuestasuandes.db.GrupoUsuario;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.example.angeles.encuestasuandes.db.Usuario.User;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Angeles on 10/2/2018.
 */
@Entity
        (foreignKeys = {@ForeignKey(entity = User.class,
                parentColumns = "uid",
                childColumns = "userId",
                onDelete = CASCADE), @ForeignKey(entity = Grupo.class,
                parentColumns = "group_id",
                childColumns = "group_id",
                onDelete = CASCADE)})
public class UserGroup {
}

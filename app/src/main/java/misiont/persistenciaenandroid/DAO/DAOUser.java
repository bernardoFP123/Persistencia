package misiont.persistenciaenandroid.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import misiont.persistenciaenandroid.Entidades.User;
import misiont.persistenciaenandroid.EstructuraBaseDeDatos.DataBase;
import misiont.persistenciaenandroid.EstructuraBaseDeDatos.DataBaseStructure;

import static android.R.attr.cursorVisible;
import static android.R.attr.id;

/**
 * Created by Bernardo_NoAdmin on 24/02/2018.
 */

public class DAOUser implements DAO<User> {


    static Context context;
    DataBase dataBase;
    private static DAOUser instance;


    private DAOUser(){

    }

    public static DAOUser getInstance(Context context1){
        context = context1;
        if(instance == null){
            instance = new DAOUser();
        }
        return instance;
    }

    @Override
    public void insertar(User entidad) {
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseStructure.usuario_nombre,entidad.getNombre());
        values.put(DataBaseStructure.usuario_contrasena,entidad.getContraseña());
        db.insert(DataBaseStructure.usuario_tabla_nombre,DataBaseStructure.usuario_id,values);

    }

    @Override
    public void eliminar(Integer id) {
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getWritableDatabase();
        db.delete(DataBaseStructure.usuario_tabla_nombre,"ID = ?",new String[]{Integer.toString(id)});
    }

    @Override
    public ArrayList<User> listar(){
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseStructure.usuario_tabla_nombre + ";",null);
        ArrayList<User> users = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                users.add(getUserFromCursor(c));
            }
            while (c.moveToNext());
        }

        return null;
    }

    @Override
    public User buscar(Integer id) {
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseStructure.usuario_tabla_nombre + " WHERE " +DataBaseStructure.usuario_id + "= ?", new String[]{Integer.toString(id)});
        if(c.moveToFirst()){
        return getUserFromCursor(c);}
        else{
            return null;
        }
    }

    public User buscar(String nombre) {
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseStructure.usuario_tabla_nombre + " WHERE " +DataBaseStructure.usuario_nombre + "= ?", new String[] {nombre});
        if(c.moveToFirst()){
            return getUserFromCursor(c);}
        else{
            return null;
        }
    }

    @Override
    public void actualizar(User entidad) {
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseStructure.usuario_nombre,entidad.getNombre());
        values.put(DataBaseStructure.usuario_contrasena,entidad.getContraseña());
        //db.update(DataBaseStructure.donante_tabla_nombre,values,DataBaseStructure.donante_id + " = ?",new String[]{Integer.toString(entidad.getId())});
        db.update(DataBaseStructure.usuario_tabla_nombre,values,DataBaseStructure.usuario_id + " = ?",new String[]{Integer.toString(entidad.getId())});

    }

    @Override
    public boolean validate(User entidad) {
        boolean ret = false;
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseStructure.usuario_tabla_nombre + ";",null);
        if(c.moveToFirst()){
            do{
                if(getUserFromCursor(c).equals(entidad)){
                    ret = true;
                    break;
                }
            }
            while (c.moveToNext());
        }
        return ret;
    }

    private User getUserFromCursor(Cursor cursor){
        User u = new User();
        u.setId(cursor.getInt(cursor.getColumnIndex(DataBaseStructure.usuario_id)));
        u.setNombre(cursor.getString(cursor.getColumnIndex(DataBaseStructure.usuario_nombre)));
        u.setContraseña(cursor.getString(cursor.getColumnIndex(DataBaseStructure.usuario_contrasena)));
        return u;
    }
}

package misiont.persistenciaenandroid.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import misiont.persistenciaenandroid.Entidades.Donor;
import misiont.persistenciaenandroid.Entidades.User;
import misiont.persistenciaenandroid.EstructuraBaseDeDatos.DataBase;
import misiont.persistenciaenandroid.EstructuraBaseDeDatos.DataBaseStructure;

import static android.R.attr.id;
import static misiont.persistenciaenandroid.DAO.DAOUser.context;
import static misiont.persistenciaenandroid.R.array.rh;
import static misiont.persistenciaenandroid.R.string.apellido;
import static misiont.persistenciaenandroid.R.string.edad;
import static misiont.persistenciaenandroid.R.string.nombre;

/**
 * Created by Bernardo_NoAdmin on 24/02/2018.
 */

public class DAODonor implements DAO<Donor> {

    static Context context;
    DataBase dataBase;

    private static DAODonor instance;


    private DAODonor(){

    }

    public static DAODonor getInstance(Context context1){
        context = context1;
        if(instance == null){
            instance = new DAODonor();
        }
        return instance;

    }

    @Override
    public void insertar(Donor entidad) {
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseStructure.donante_id,entidad.getId());
        values.put(DataBaseStructure.donante_nombre,entidad.getNombre());
        values.put(DataBaseStructure.donante_apellido,entidad.getApellido());
        values.put(DataBaseStructure.donante_edad,entidad.getEdad());
        values.put(DataBaseStructure.donante_tipo_de_sangre,entidad.getTipoDeSangre());
        values.put(DataBaseStructure.donante_rh,entidad.getRh());
        values.put(DataBaseStructure.donante_peso,entidad.getPeso());
        values.put(DataBaseStructure.donante_estatura,entidad.getEstatura());
        db.insert(DataBaseStructure.donante_tabla_nombre,DataBaseStructure.donante_id,values);

    }

    @Override
    public void eliminar(Integer id) {
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getWritableDatabase();
        db.delete(DataBaseStructure.donante_tabla_nombre, DataBaseStructure.donante_id +" = ?",new String[]{Integer.toString(id)});
    }

    @Override
    public ArrayList<Donor> listar() {
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseStructure.donante_tabla_nombre + ";",null);
        ArrayList<Donor> donors = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                donors.add(getDonorFromCursor(c));
            }
            while (c.moveToNext());
        }

        return donors;
    }

    @Override
    public Donor buscar(Integer id) {

        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseStructure.donante_tabla_nombre + " WHERE " +DataBaseStructure.donante_id + "= ?", new String[]{Integer.toString(id)});
        if(c.moveToFirst()){
            return getDonorFromCursor(c);}
        else{
            return null;
        }
    }

    public ArrayList<Donor> buscarPatron(Integer id){
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getReadableDatabase();
        Cursor c1 = db.rawQuery("SELECT * FROM " + DataBaseStructure.donante_tabla_nombre + " WHERE " + DataBaseStructure.donante_id + " LIKE '%"+ id + "%' ;",null);
        ArrayList<Donor> donors = new ArrayList<>();
        if(c1.moveToFirst()){
            do{
                donors.add(getDonorFromCursor(c1));
            }
            while (c1.moveToNext());
        }
        return donors;
    }


    @Override
    public void actualizar(Donor entidad) {
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseStructure.donante_id,entidad.getId());
        values.put(DataBaseStructure.donante_nombre,entidad.getNombre());
        values.put(DataBaseStructure.donante_apellido,entidad.getApellido());
        values.put(DataBaseStructure.donante_edad,entidad.getEdad());
        values.put(DataBaseStructure.donante_tipo_de_sangre,entidad.getTipoDeSangre());
        values.put(DataBaseStructure.donante_rh,entidad.getRh());
        values.put(DataBaseStructure.donante_peso,entidad.getPeso());
        values.put(DataBaseStructure.donante_estatura,entidad.getEstatura());
        db.update(DataBaseStructure.donante_tabla_nombre,values,DataBaseStructure.donante_id + " = ?",new String[]{Integer.toString(entidad.getId())});
    }

    @Override
    public boolean validate(Donor entidad) {
        boolean ret = false;
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        SQLiteDatabase db = dataBase.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseStructure.donante_tabla_nombre + ";",null);
        if(c.moveToFirst()){
            do{
                if(getDonorFromCursor(c).equals(entidad)){
                    ret = true;
                    break;
                }
            }
            while (c.moveToNext());
        }
        return ret;
    }


    private Donor getDonorFromCursor(Cursor cursor){
        Donor d = new Donor();
        d.setId(cursor.getInt(cursor.getColumnIndex(DataBaseStructure.donante_id)));
        d.setNombre(cursor.getString(cursor.getColumnIndex(DataBaseStructure.donante_nombre)));
        d.setApellido(cursor.getString(cursor.getColumnIndex(DataBaseStructure.donante_apellido)));
        d.setEdad(cursor.getString(cursor.getColumnIndex(DataBaseStructure.donante_edad)));
        d.setTipoDeSangre(cursor.getString(cursor.getColumnIndex(DataBaseStructure.donante_tipo_de_sangre)));
        d.setRh(cursor.getString(cursor.getColumnIndex(DataBaseStructure.donante_rh)));
        d.setPeso(cursor.getString(cursor.getColumnIndex(DataBaseStructure.donante_peso)));
        d.setEstatura(cursor.getString(cursor.getColumnIndex(DataBaseStructure.donante_estatura)));

        return d;
    }
}

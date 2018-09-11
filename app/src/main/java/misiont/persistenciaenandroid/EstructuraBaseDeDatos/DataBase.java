package misiont.persistenciaenandroid.EstructuraBaseDeDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bernardo_NoAdmin on 24/02/2018.
 */

public class DataBase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Donadores.sqlite";

    private String sentenciaCrearTablaUsuario = "CREATE TABLE " +DataBaseStructure.usuario_tabla_nombre +
            " (" + DataBaseStructure.usuario_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DataBaseStructure.usuario_nombre+  " TEXT ,"+DataBaseStructure.usuario_contrasena+" TEXT);";
    private String sentenciaCrearTablaDonante = "CREATE TABLE " +DataBaseStructure.donante_tabla_nombre +
            " (" + DataBaseStructure.donante_id + " INTEGER PRIMARY KEY, " + DataBaseStructure.donante_nombre + " TEXT ,"+DataBaseStructure.donante_apellido+" TEXT ,"
            +DataBaseStructure.donante_edad + " TEXT, " + DataBaseStructure.donante_tipo_de_sangre + " TEXT, " + DataBaseStructure.donante_rh + " TEXT,"
            + DataBaseStructure.donante_peso + "  TEXT, " + DataBaseStructure.donante_estatura + " TEXT);";
    private static final String SQL_DELETE_ENTRIES_USUARIO = "DROP TABLE IF EXISTS " + DataBaseStructure.usuario_tabla_nombre;
    private static final String SQL_DELETE_ENTRIES_DONANTE = "DROP TABLE IF EXISTS " + DataBaseStructure.donante_tabla_nombre;
    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sentenciaCrearTablaUsuario);
        db.execSQL(sentenciaCrearTablaDonante);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES_USUARIO);
        db.execSQL(SQL_DELETE_ENTRIES_DONANTE);
        onCreate(db);
    }
}

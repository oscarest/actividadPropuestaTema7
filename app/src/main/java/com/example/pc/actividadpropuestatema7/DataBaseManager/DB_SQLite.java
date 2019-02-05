package com.example.pc.actividadpropuestatema7.DataBaseManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.pc.actividadpropuestatema7.DataBaseManager.Esquema.Usuarios;
public class DB_SQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tienda.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " +  Usuarios.TABLE_NAME + " (" +
                    Usuarios.COLUMN_NAME_ID + " " +  Usuarios.COLUMN_TYPE_ID + " PRIMARY KEY AUTOINCREMENT, " +
                    Usuarios.COLUMN_NAME_NOMBRE + " " +  Usuarios.COLUMN_TYPE_NOMBRE + "," +
                    Usuarios.COLUMN_NAME_APELLIDOS + " " +  Usuarios.COLUMN_TYPE_APELLIDOS +  "," +
                    Usuarios.COLUMN_NAME_EDAD + " " +  Usuarios.COLUMN_TYPE_EDAD + "," +
                    Usuarios.COLUMN_NAME_INFORMACION + " " +  Usuarios.COLUMN_TYPE_INFORMACION + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +  Usuarios.TABLE_NAME;

    public DB_SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}

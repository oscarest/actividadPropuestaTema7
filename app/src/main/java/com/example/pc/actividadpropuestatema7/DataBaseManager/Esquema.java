package com.example.pc.actividadpropuestatema7.DataBaseManager;

import android.provider.BaseColumns;

public class Esquema
{
    public Esquema()
    {
    }
    public static abstract class Usuarios implements BaseColumns
    {
        public static final String TABLE_NAME = "USUARIOS";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NOMBRE = "nombreUsuario";
        public static final String COLUMN_NAME_APELLIDOS = "apellidosUsuario";
        public static final String COLUMN_NAME_EDAD = "edadUsuario";
        public static final String COLUMN_NAME_INFORMACION = "recibirInformacion";
        public static final String COLUMN_TYPE_ID = "INTEGER";
        public static final String COLUMN_TYPE_NOMBRE = "TEXT";
        public static final String COLUMN_TYPE_APELLIDOS = "TEXT";
        public static final String COLUMN_TYPE_EDAD = "INTEGER";
        public static final String COLUMN_TYPE_INFORMACION = "INTEGER";
    }
}

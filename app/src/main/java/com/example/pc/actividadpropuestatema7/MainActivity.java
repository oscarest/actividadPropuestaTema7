package com.example.pc.actividadpropuestatema7;

import com.example.pc.actividadpropuestatema7.DataBaseManager.DB_SQLite;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pc.actividadpropuestatema7.DataBaseManager.Esquema.Usuarios;


public class MainActivity extends Activity {

    private EditText txtUsuario;
    private EditText txtApellidos;
    private EditText txtEdad;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsuario = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtEdad = findViewById(R.id.txtEdad);
    }

    public void insertarUsuario(View view) {
        String strUsuario = txtUsuario.getText().toString();
        String strApellidos = txtApellidos.getText().toString();
        String strEdad = txtEdad.getText().toString();
        String strCheckBox = Boolean.toString(checkBox.isActivated());

        if (strUsuario.equals("") || strApellidos.equals("")) {
            mostrarMensaje("Todos los campos son obligatorios.");
        } else {

            DB_SQLite db = new DB_SQLite(this);
            SQLiteDatabase conn = db.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put(Usuarios.COLUMN_NAME_NOMBRE, strUsuario);
            content.put(Usuarios.COLUMN_NAME_APELLIDOS, strApellidos);
            content.put(Usuarios.COLUMN_NAME_EDAD, strEdad);
            conn.insert(Usuarios.TABLE_NAME, null, content);
            conn.close();

            mostrarMensaje("El usuario " + strUsuario + " ha sido insertado.");
            limpiarCuadrosTexto();
        }

    }

    public void eliminarUsuario(View view) {
        String strUsuario = txtUsuario.getText().toString();

        if (strUsuario.equals("")) {
            mostrarMensaje("Debe indicar el usuario a eliminar");
        } else {
            DB_SQLite db = new DB_SQLite(this);
            SQLiteDatabase conn = db.getWritableDatabase();
            String sqlWhere = Usuarios.COLUMN_NAME_NOMBRE + " LIKE '" + strUsuario + "'";
            int count = conn.delete(Usuarios.TABLE_NAME, sqlWhere, null);
            conn.close();
            mostrarMensaje("Se ha eliminado el usuario " + strUsuario + ". Registros afectados: " + count);
            limpiarCuadrosTexto();
        }

    }

    public void modificarUsuario(View view) {
        String strUsuario = txtUsuario.getText().toString();
        String strApellidos = txtApellidos.getText().toString();
        String strEdad = txtEdad.getText().toString();

        if (strUsuario.equals("") || strApellidos.equals("")) {
            mostrarMensaje("Todos los campos son obligatorios.");
        } else {
            DB_SQLite db = new DB_SQLite(this);
            SQLiteDatabase conn = db.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put(Usuarios.COLUMN_NAME_NOMBRE, strUsuario);
            content.put(Usuarios.COLUMN_NAME_APELLIDOS, strApellidos);
            content.put(Usuarios.COLUMN_NAME_EDAD, strEdad);
            String sqlWhere = Usuarios.COLUMN_NAME_NOMBRE + " LIKE '" + strUsuario + "'";
            int count = conn.update(Usuarios.TABLE_NAME, content, sqlWhere, null);
            conn.close();
            mostrarMensaje("Se ha actualizado el usuario " + strUsuario + ". Registros afectados: " + count);
            limpiarCuadrosTexto();
        }

    }

    public void buscarUsuario(View view) {
        String strUsuario = txtUsuario.getText().toString();

        if (strUsuario.equals("")) {
            mostrarMensaje("Debe indicar el usuario a buscar");

        } else {
            DB_SQLite db = new DB_SQLite(this);
            SQLiteDatabase conn = db.getReadableDatabase();

            String[] sqlFields = {Usuarios.COLUMN_NAME_ID, Usuarios.COLUMN_NAME_NOMBRE, Usuarios.COLUMN_NAME_APELLIDOS, Usuarios.COLUMN_NAME_EDAD};
            String sqlWhere = Usuarios.COLUMN_NAME_NOMBRE + " LIKE '" + txtUsuario.getText().toString() + "'";

            Cursor cursor = conn.query(Usuarios.TABLE_NAME, sqlFields, sqlWhere, null, null, null, null);
            if (cursor.getCount() == 0) {
                mostrarMensaje("usuario " + strUsuario + " no existe.");
            }else{
                cursor.moveToFirst();
                long dataIdUsuario = cursor.getLong(cursor.getColumnIndex(Usuarios.COLUMN_NAME_ID));
                String dataNombre = cursor.getString(cursor.getColumnIndex(Usuarios.COLUMN_NAME_NOMBRE));
                String dataApellidos = cursor.getString(cursor.getColumnIndex(Usuarios.COLUMN_NAME_APELLIDOS));
                Integer dataEdad = cursor.getInt(cursor.getColumnIndex(Usuarios.COLUMN_NAME_EDAD));
                mostrarMensaje("RECUPERADO: id:" + dataIdUsuario + " nombre=" + dataNombre + " apellidos=" + dataApellidos + " edad=" + dataEdad);
                limpiarCuadrosTexto();
            }
            cursor.close();
            conn.close();
        }
    }

    public void listarUsuario(View view) {
        TextView txtResultados = findViewById(R.id.txtResultados);
        //Esto sirve para hacer scroll de un TextView y tal. Recordar añadir "android:scrollbars="vertical" al layout.
        txtResultados.setMovementMethod(new ScrollingMovementMethod());
        txtResultados.setText("");

        DB_SQLite db = new DB_SQLite(this);
        SQLiteDatabase conn = db.getReadableDatabase();

        String[] sqlFields = {Usuarios.COLUMN_NAME_ID, Usuarios.COLUMN_NAME_NOMBRE, Usuarios.COLUMN_NAME_APELLIDOS, Usuarios.COLUMN_NAME_EDAD};
        String sqlWhere = "";
        String sqlOrderBy = Usuarios.COLUMN_NAME_NOMBRE + " ASC";

        Cursor cursor = conn.query(Usuarios.TABLE_NAME, sqlFields, sqlWhere, null, null, null, sqlOrderBy);

        if (cursor.getCount() == 0) {
            mostrarMensaje("La base de datos está vacía.");
        } else {
            cursor.moveToFirst();
            String msg = "";
            do {
                long dataIdUsuario = cursor.getLong(cursor.getColumnIndex(Usuarios.COLUMN_NAME_ID));
                String dataNombre = cursor.getString(cursor.getColumnIndex(Usuarios.COLUMN_NAME_NOMBRE));
                String dataApellidos = cursor.getString(cursor.getColumnIndex(Usuarios.COLUMN_NAME_APELLIDOS));
                Integer dataEdad = cursor.getInt(cursor.getColumnIndex(Usuarios.COLUMN_NAME_EDAD));
                msg += "id:" + dataIdUsuario + " nombre=" + dataNombre + " apellidos=" + dataApellidos + " edad=" + dataEdad  + "\n";
            } while (cursor.moveToNext());
            txtResultados.append(msg);
        }

        cursor.close();
        conn.close();
    }

    private void mostrarMensaje(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void limpiarCuadrosTexto() {
        txtUsuario.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
    }

}


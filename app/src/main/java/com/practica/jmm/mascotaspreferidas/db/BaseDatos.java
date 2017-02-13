package com.practica.jmm.mascotaspreferidas.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.practica.jmm.mascotaspreferidas.pojo.Mascota;

import java.util.ArrayList;

/**
 * Created by sath on 3/01/17.
 */

public class BaseDatos extends SQLiteOpenHelper{

    private Context context;

    public BaseDatos(Context context) {
        super(context, ConstantesBaseDatos.DATABASE_NAME, null, ConstantesBaseDatos.DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String queryCtreateTabMascotas = "CREATE TABLE " + ConstantesBaseDatos.TABLE_MASCOTA + "( " + ConstantesBaseDatos.TABLE_MASCOTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                           ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE + " TEXT, " + ConstantesBaseDatos.TABLE_MASCOTA_FOTO + " INTEGER)";

        String queryRaitingMascotas = "CREATE TABLE " + ConstantesBaseDatos.TABLE_RAITING +"( " + ConstantesBaseDatos.TABLE_RAITING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                        ConstantesBaseDatos.TABLE_RAITING_ID_MASCOTA + " INTEGER, " +
                                                        ConstantesBaseDatos.TABLE_RAITING_NUM_LIKE + " INTEGER, FOREIGN KEY ("+ ConstantesBaseDatos.TABLE_RAITING_ID_MASCOTA  +")" +
                                                        " REFERENCES " + ConstantesBaseDatos.TABLE_MASCOTA + " (" + ConstantesBaseDatos.TABLE_MASCOTA_ID + "))";

        db.execSQL(queryCtreateTabMascotas);
        db.execSQL(queryRaitingMascotas);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
           db.execSQL("DROP TABLE " + ConstantesBaseDatos.TABLE_RAITING);
           db.execSQL("DROP TABLE " + ConstantesBaseDatos.TABLE_MASCOTA);
           onCreate(db);
    }

    public ArrayList<Mascota> obtenerTodasMascotas(){


        ArrayList<Mascota> mascotas = new ArrayList<>();

        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_MASCOTA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(query, null);
        while (cur.moveToNext()){
            Mascota mascota = new Mascota();
            mascota.setId(cur.getString(0));
            mascota.setNombre(cur.getString(1));
           // mascota.setImagen(cur.getInt(2));

            mascota.setRaiting(String.valueOf(obtenerLikesContacto(mascota)));

            //mascota.setRaiting("0");
            mascotas.add(mascota);
        }

        db.close();


        return mascotas;
    }

    public void InsertarMascota(ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_MASCOTA,null,contentValues);
        db.close();
    }

    public boolean hayMascotas(){

        boolean rep = true;

        String query = "SELECT COUNT(*) FROM " + ConstantesBaseDatos.TABLE_MASCOTA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(query, null);
        while (cur.moveToNext()){
            int i = cur.getInt(0);
            if(i>0){
                rep = false;
            }

        }
        return rep;
    }
    public void insertarLikeMascota(ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_RAITING, null, contentValues);
        db.close();
    }


    public int obtenerLikesContacto(Mascota mascota){
        int likes = 0;

        String query = "SELECT COUNT("+ConstantesBaseDatos.TABLE_RAITING_ID+")" +
                " FROM " + ConstantesBaseDatos.TABLE_RAITING +
                " WHERE " + ConstantesBaseDatos.TABLE_RAITING_ID_MASCOTA + "="+mascota.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        if (registros.moveToNext()){
            likes = registros.getInt(0);
        }

        db.close();

        return likes;
    }
}

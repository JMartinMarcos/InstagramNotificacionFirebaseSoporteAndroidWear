package com.practica.jmm.mascotaspreferidas.pojo;

import android.content.ContentValues;
import android.content.Context;
import com.practica.jmm.mascotaspreferidas.R;
import com.practica.jmm.mascotaspreferidas.db.BaseDatos;
import com.practica.jmm.mascotaspreferidas.db.ConstantesBaseDatos;

import java.util.ArrayList;

/**
 * Created by sath on 3/01/17.
 */

public class ConstructorMascotas {

    private Context contexto;
    private static final int LIKE = 1;


    public ConstructorMascotas(Context contexto) {
        this.contexto = contexto;
    }

    public ArrayList<Mascota> obtenerDatos(){
        BaseDatos db = new BaseDatos(contexto);
        if(db.hayMascotas()){
            cargaInicialMascotas(db);
        }
        return db.obtenerTodasMascotas();
    }

    public void cargaInicialMascotas(BaseDatos db){

        ContentValues values = new ContentValues();
        values.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Cody" );
        values.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.perro_cahorro);
        db.InsertarMascota(values);

        values = new ContentValues();
        values.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Tigre" );
        values.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.gato);
        db.InsertarMascota(values);

        values = new ContentValues();
        values.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Porky" );
        values.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.fotos_de_los_animales66);
        db.InsertarMascota(values);

        values = new ContentValues();
        values.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Tara" );
        values.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.mascota_exotica3);
        db.InsertarMascota(values);

        values = new ContentValues();
        values.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Gloop" );
        values.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.pez);
        db.InsertarMascota(values);

        values = new ContentValues();
        values.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Rocky" );
        values.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.cute_dog_dogs_33531409_1440_900);
        db.InsertarMascota(values);

    }
    public void darLikeCotnacto(Mascota mascota){
        BaseDatos db = new BaseDatos(contexto);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_RAITING_ID_MASCOTA, mascota.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_RAITING_NUM_LIKE, LIKE);
        db.insertarLikeMascota(contentValues);
    }

    public int obtenerLikesContacto(Mascota mascota){
        BaseDatos db = new BaseDatos(contexto);
        return db.obtenerLikesContacto(mascota);
    }
}

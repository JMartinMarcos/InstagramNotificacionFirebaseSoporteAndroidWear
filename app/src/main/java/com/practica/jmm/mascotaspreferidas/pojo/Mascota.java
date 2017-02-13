package com.practica.jmm.mascotaspreferidas.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sath on 22/12/16.
 */

public class Mascota implements Parcelable{

    private String id;
    //private int imagen;
    private String imagen;
    private String nombre;
    private String raiting;
    private String id_medeia;

    public Mascota(String imagen, String nombre, String raiting, String id_medeia) {
        this.imagen    = imagen;
        this.nombre    = nombre;
        this.raiting   = raiting;
        this.id_medeia = id_medeia;
    }

    protected Mascota(Parcel in) {
        imagen    = in.readString();
        nombre    = in.readString();
        raiting   = in.readString();
        id_medeia = in.readString();
    }

    public static final Creator<Mascota> CREATOR = new Creator<Mascota>() {
        @Override
        public Mascota createFromParcel(Parcel in) {
            return new Mascota(in);
        }

        @Override
        public Mascota[] newArray(int size) {
            return new Mascota[size];
        }
    };

    public Mascota() {

    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaiting() {
        return raiting;
    }

    public void setRaiting(String raiting) {
        this.raiting = raiting;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_medeia() {
        return id_medeia;
    }

    public void setId_medeia(String id_medeia) {
        this.id_medeia = id_medeia;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imagen);
        parcel.writeString(nombre);
        parcel.writeString(raiting);
        parcel.writeString(id_medeia);
    }
}

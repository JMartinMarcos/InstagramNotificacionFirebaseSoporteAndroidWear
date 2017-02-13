package com.practica.jmm.mascotaspreferidas.restApi.model;

import com.practica.jmm.mascotaspreferidas.pojo.Mascota;

import java.util.ArrayList;

/**
 * Created by sath on 18/01/17.
 */

public class PetResponse {

    ArrayList<Mascota> mascotas;

    public ArrayList<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(ArrayList<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
}

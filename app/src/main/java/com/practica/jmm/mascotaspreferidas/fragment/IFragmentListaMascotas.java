package com.practica.jmm.mascotaspreferidas.fragment;

import com.practica.jmm.mascotaspreferidas.adapter.MascotaAdaptador;
import com.practica.jmm.mascotaspreferidas.pojo.Mascota;

import java.util.ArrayList;

/**
 * Created by sath on 3/01/17.
 */

public interface IFragmentListaMascotas {

    public void generaLinearLayautVertical();

    public MascotaAdaptador crearAdaptador(ArrayList<Mascota> mascotas);

    public void inicializaeAdaptador(MascotaAdaptador mAdaptador);
}

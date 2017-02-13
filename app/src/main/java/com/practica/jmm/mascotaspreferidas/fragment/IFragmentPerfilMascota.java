package com.practica.jmm.mascotaspreferidas.fragment;

import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.practica.jmm.mascotaspreferidas.adapter.MascotaAdaptadorTab2;
import com.practica.jmm.mascotaspreferidas.pojo.Mascota;
import com.practica.jmm.mascotaspreferidas.pojo.Usuario;

import java.util.ArrayList;

/**
 * Created by sath on 4/01/17.
 */

public interface IFragmentPerfilMascota {

    public void generaGridLayaut();

    public MascotaAdaptadorTab2 crearAdaptador(ArrayList<Mascota> mascotas, CircularImageView circularImageView, TextView textView, Usuario user);

    public void inicializaeAdaptador(MascotaAdaptadorTab2 mAdaptador);
}

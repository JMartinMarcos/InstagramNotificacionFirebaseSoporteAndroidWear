package com.practica.jmm.mascotaspreferidas.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.practica.jmm.mascotaspreferidas.R;
import com.practica.jmm.mascotaspreferidas.adapter.MascotaAdaptadorTab2;
import com.practica.jmm.mascotaspreferidas.pojo.Mascota;
import com.practica.jmm.mascotaspreferidas.pojo.Usuario;
import com.practica.jmm.mascotaspreferidas.presentador.FragmentPerfilMascotaPresenter;
import com.practica.jmm.mascotaspreferidas.presentador.IFragmentPerfilMascotaPresenter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPerfilMascota extends Fragment implements IFragmentPerfilMascota{

    private RecyclerView listaMascotas2;
    IFragmentPerfilMascotaPresenter presenterPerfil;


    public FragmentPerfilMascota() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fragment_perfil_mascota, container, false);
        // Inflate the layout for this fragment
        CircularImageView circularImageView = (CircularImageView) v.findViewById(R.id.fotoPerfil);
        listaMascotas2 = (RecyclerView) v.findViewById(R.id.rvMascota);
        TextView nombreMascota = (TextView) v.findViewById(R.id.txtNombrePerfil);
        generaGridLayaut();
        presenterPerfil = new FragmentPerfilMascotaPresenter(this, getContext(), circularImageView, nombreMascota);

        return v;
    }

    @Override
    public void generaGridLayaut() {
        GridLayoutManager glm = new GridLayoutManager(getContext(),3);
        glm.setOrientation(LinearLayoutManager.VERTICAL);
        listaMascotas2.setLayoutManager(glm);
    }

    @Override
    public MascotaAdaptadorTab2 crearAdaptador(ArrayList<Mascota> mascotas, CircularImageView circularImageView, TextView textView, Usuario user) {
        MascotaAdaptadorTab2 adaptador = new MascotaAdaptadorTab2(mascotas,circularImageView, textView, getActivity(),user);
        return adaptador;    }

    @Override
    public void inicializaeAdaptador(MascotaAdaptadorTab2 mAdaptador) {
        listaMascotas2.setAdapter(mAdaptador);
    }
}

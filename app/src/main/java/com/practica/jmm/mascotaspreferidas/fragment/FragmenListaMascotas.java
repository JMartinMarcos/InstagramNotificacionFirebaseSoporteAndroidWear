package com.practica.jmm.mascotaspreferidas.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.practica.jmm.mascotaspreferidas.R;
import com.practica.jmm.mascotaspreferidas.adapter.MascotaAdaptador;
import com.practica.jmm.mascotaspreferidas.pojo.Mascota;
import com.practica.jmm.mascotaspreferidas.presentador.FragmentListaMascotasPresenter;
import com.practica.jmm.mascotaspreferidas.presentador.IFragmentListaMascotasPresenter;

import java.util.ArrayList;



public class FragmenListaMascotas extends Fragment implements IFragmentListaMascotas {

    private RecyclerView listaMascotas;
    private IFragmentListaMascotasPresenter presenter;

    ArrayList<Mascota> mascota;

    ImageView imgTop5;

    public FragmenListaMascotas(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fragmen_lista_mascotas, container, false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listaMascotas = (RecyclerView) v.findViewById(R.id.rvMascota);
        generaLinearLayautVertical();

        presenter = new FragmentListaMascotasPresenter(this ,getContext());

   //     MascotaAdaptador adaptador = crearAdaptador(mascota);
   //     inicializaeAdaptador(adaptador);

        /*
        imgTop5 = (ImageView) findViewById(R.id.imgTop5);
        imgTop5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Collections.sort(mascota, new Comparator<Mascota>() {

                    @Override
                    public int compare(Mascota p1, Mascota p2){
                        return new Integer(p2.getRaiting()).compareTo(new Integer(p1.getRaiting()));
                    }

                });

                mascotaIntent = new ArrayL ist<>();

                for (int i = 0;i<5;i++){
                    mascotaIntent.add(mascota.get(i));
                }
                Intent intent = new Intent(FragmenListaMascotas.super.getContext(), top5mascotas.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("Mascota", mascotaIntent);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
*/
        return v ;
    }
/*
    public void inicializarDatos(){
        mascota = new ArrayList<>();

        mascota.add(new Mascota(R.drawable.perro_cahorro,"Cody","7"));
        mascota.add(new Mascota(R.drawable.gato,"Tigre","6"));
        mascota.add(new Mascota(R.drawable.fotos_de_los_animales66,"Porky","10"));
        mascota.add(new Mascota(R.drawable.mascota_exotica3,"Tara","3"));
        mascota.add(new Mascota(R.drawable.pez,"Gloop","4"));
        mascota.add(new Mascota(R.drawable.cute_dog_dogs_33531409_1440_900,"Rocky","7"));
    }
*/

    @Override
    public void generaLinearLayautVertical() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaMascotas.setLayoutManager(llm);
    }

    @Override
    public MascotaAdaptador crearAdaptador(ArrayList<Mascota> mascota) {
        MascotaAdaptador adaptador = new MascotaAdaptador(mascota,getActivity());
        return adaptador;
    }

    @Override
    public void inicializaeAdaptador(MascotaAdaptador adaptador) {
        listaMascotas.setAdapter(adaptador);
    }
}

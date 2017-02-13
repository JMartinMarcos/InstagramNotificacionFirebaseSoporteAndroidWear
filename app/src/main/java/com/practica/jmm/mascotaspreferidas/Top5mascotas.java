package com.practica.jmm.mascotaspreferidas;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.practica.jmm.mascotaspreferidas.adapter.MascotaAdaptador;
import com.practica.jmm.mascotaspreferidas.fragment.IFragmentListaMascotas;
import com.practica.jmm.mascotaspreferidas.pojo.Mascota;
import com.practica.jmm.mascotaspreferidas.presentador.FragmentListaMascotasPresenter;
import com.practica.jmm.mascotaspreferidas.presentador.IFragmentListaMascotasPresenter;
import com.practica.jmm.mascotaspreferidas.presentador.Top5MascotasPresenter;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class Top5mascotas extends AppCompatActivity implements IFragmentListaMascotas {

    ImageView imgTop5;
    ActionBar actionBar;
    private RecyclerView listaMascotas;
    ArrayList<Mascota> mascota;
    private IFragmentListaMascotasPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top5mascotas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.acctionbar2);
        setSupportActionBar(toolbar);

       //getSupportActionBar().setHomeAsUpIndicator(R.drawable.dog_bone_48_color);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //imgTop5 = (ImageView) findViewById(R.id.imgTop5);
        //imgTop5.setVisibility(View.INVISIBLE);

        listaMascotas = (RecyclerView) findViewById(R.id.rvMascota);

        generaLinearLayautVertical();

        presenter = new Top5MascotasPresenter(this, getApplicationContext());
/*       Intent i = getIntent();
       mascota = (ArrayList<Mascota>) i.getSerializableExtra("list");

        Bundle bundle = getIntent().getExtras();
        mascota = bundle.getParcelableArrayList("Mascota");



//         actionBar = getActionBar();
        // actionBar.setDisplayHomeAsUpEnabled(true);
//
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */

    }

    @Override
    public void generaLinearLayautVertical() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaMascotas.setLayoutManager(llm);

    }

    @Override
    public MascotaAdaptador crearAdaptador(ArrayList<Mascota> mascotas) {
        MascotaAdaptador adaptador = new MascotaAdaptador(mascotas,this);

        return adaptador;
    }

    @Override
    public void inicializaeAdaptador(MascotaAdaptador mAdaptador) {
        listaMascotas.setAdapter(mAdaptador);
    }
}

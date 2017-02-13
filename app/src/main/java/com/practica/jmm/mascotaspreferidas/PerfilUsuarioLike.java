package com.practica.jmm.mascotaspreferidas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.practica.jmm.mascotaspreferidas.adapter.MascotaAdaptador;
import com.practica.jmm.mascotaspreferidas.fragment.IFragmentListaMascotas;
import com.practica.jmm.mascotaspreferidas.pojo.Mascota;
import com.practica.jmm.mascotaspreferidas.presentador.IFragmentListaMascotasPresenter;
import com.practica.jmm.mascotaspreferidas.presentador.Top5MascotasPresenter;
import com.practica.jmm.mascotaspreferidas.restApi.ConstantesRestApi;

import java.util.ArrayList;

public class PerfilUsuarioLike extends AppCompatActivity implements IFragmentListaMascotas {

    private RecyclerView listaMascotas;
    private IFragmentListaMascotasPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario_like);

        Intent intent = getIntent();
        String userAux = ConstantesRestApi.UsuarioInsta;
        ConstantesRestApi.UsuarioInsta = intent.getStringExtra("USER");

        listaMascotas = (RecyclerView) findViewById(R.id.rvMascota);
        generaLinearLayautVertical();
        presenter = new Top5MascotasPresenter(this, getApplicationContext());
        ConstantesRestApi.UsuarioInsta = userAux;
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

package com.practica.jmm.mascotaspreferidas;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.iid.FirebaseInstanceId;
import com.practica.jmm.mascotaspreferidas.adapter.PageAdapter;
import com.practica.jmm.mascotaspreferidas.fragment.FragmenListaMascotas;
import com.practica.jmm.mascotaspreferidas.fragment.FragmentPerfilMascota;
import com.practica.jmm.mascotaspreferidas.restApi.ConstantesRestApi;
import com.practica.jmm.mascotaspreferidas.restApi.EndPointApi;
import com.practica.jmm.mascotaspreferidas.restApi.adapter.RestApiAdapter;
import com.practica.jmm.mascotaspreferidas.restApi.model.UsuarioResponsePost;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.acctionbar1);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }

        tabLayout = (TabLayout) findViewById(R.id.tabLayaut);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setUpViewPager();

    }

    private ArrayList<Fragment> agregarFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentPerfilMascota());

        fragments.add(new FragmenListaMascotas());

        return fragments;
    }

    private void setUpViewPager(){
      viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),agregarFragments()));
      tabLayout.setupWithViewPager(viewPager);
      tabLayout.getTabAt(1).setIcon(R.drawable.ic_dog_house);
      tabLayout.getTabAt(0).setIcon(R.drawable.ic_lobo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.fiveStar:
                Intent fiveStar = new Intent(MainActivity.this,Top5mascotas.class);
                startActivity(fiveStar);
                break;
            case R.id.mAcercaDe:
                Intent mAcercaDe = new Intent(MainActivity.this,AcercaDe.class);
                startActivity(mAcercaDe);
                break;

            case R.id.mContacto:
                Intent mContacto = new Intent(MainActivity.this,FormularioMail.class);
                startActivity(mContacto);
                break;
            case R.id.mUser:
                Intent mUser = new Intent(MainActivity.this,Usuario_loguin.class);
                startActivity(mUser);
                break;
            case R.id.mNotificaciones:
                String token = FirebaseInstanceId.getInstance().getToken();
                enviarTokenRegistro(token);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enviarTokenRegistro(String token){
        Log.d("TOKEN",token);
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        EndPointApi endPointApi = restApiAdapter.establecerConexionRestApiHeroku();
        Call<UsuarioResponsePost> usuarioResponsePostCall = endPointApi.registrarTokenID(token, ConstantesRestApi.IdUser);
    //    Call<UsuarioResponsePost> usuarioResponsePostCall = endPointApi.registrarTokenID(token, ConstantesRestApi.IdUser,ConstantesRestApi.IdMedia);

        usuarioResponsePostCall.enqueue(new Callback<UsuarioResponsePost>() {
            @Override
            public void onResponse(Call<UsuarioResponsePost> call, Response<UsuarioResponsePost> response) {
                UsuarioResponsePost usuarioResponsePost = response.body();
                Log.d("ID_FIREBASE", usuarioResponsePost.getId());
                Log.d("TOKEN_FIREBASE", usuarioResponsePost.getId_dispositivo());
                Log.d("ID_USUARIO_FIREBASE", usuarioResponsePost.getId_usuario_instagram());
                //Log.d("ID_FOTO_INSTAGRAM", usuarioResponsePost.getId_foto_instagram());
            }

            @Override
            public void onFailure(Call<UsuarioResponsePost> call, Throwable t) {

            }
        });


    }
}

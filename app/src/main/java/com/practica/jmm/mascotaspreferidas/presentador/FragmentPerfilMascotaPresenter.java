package com.practica.jmm.mascotaspreferidas.presentador;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.practica.jmm.mascotaspreferidas.fragment.IFragmentPerfilMascota;
import com.practica.jmm.mascotaspreferidas.pojo.ConstructorMascotas;
import com.practica.jmm.mascotaspreferidas.pojo.Mascota;
import com.practica.jmm.mascotaspreferidas.pojo.Usuario;
import com.practica.jmm.mascotaspreferidas.restApi.ConstantesRestApi;
import com.practica.jmm.mascotaspreferidas.restApi.EndPointApi;
import com.practica.jmm.mascotaspreferidas.restApi.adapter.RestApiAdapter;
import com.practica.jmm.mascotaspreferidas.restApi.model.PetResponse;
import com.practica.jmm.mascotaspreferidas.restApi.model.UserResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sath on 4/01/17.
 */

public class FragmentPerfilMascotaPresenter implements IFragmentPerfilMascotaPresenter{


    private IFragmentPerfilMascota iFragmentPerfilMascota;
    private Context context;
    private ConstructorMascotas constructorMascotas;
    private ArrayList<Mascota> mascotas;
    private CircularImageView circularImageView;
    private TextView text;
    private ArrayList<Usuario> usuarios;
    private String userId;

    public FragmentPerfilMascotaPresenter(IFragmentPerfilMascota iFragmentPerfilMascota, Context context, CircularImageView circularImageView, TextView text) {
        this.context = context;
        this.iFragmentPerfilMascota = iFragmentPerfilMascota;
        this.circularImageView = circularImageView;
        this.text = text;
        //obtenerMascotasDB();
        obtenerIdUsuario();
    }

    @Override
    public void obtenerMascotasDB() {
        constructorMascotas = new ConstructorMascotas(context);
        mascotas = constructorMascotas.obtenerDatos();
        mostrarMascotasRV();

    }

    @Override
    public void mostrarMascotasRV() {
        iFragmentPerfilMascota.inicializaeAdaptador(iFragmentPerfilMascota.crearAdaptador(mascotas,circularImageView,text, usuarios.get(0)));
        iFragmentPerfilMascota.generaGridLayaut();
    }

    @Override
    public void obtenerMediosRecientes() {

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorMediaRecent();
        EndPointApi endPointApi = restApiAdapter.estalecerConnexionRestApiInstagram(gsonMediaRecent);
        Call<PetResponse> petResponseCall = endPointApi.getRecentMedia();

        petResponseCall.enqueue(new Callback<PetResponse>() {
            @Override
            public void onResponse(Call<PetResponse> call, Response<PetResponse> response) {
                PetResponse petResponse = response.body();
                mascotas = petResponse.getMascotas();
                mostrarMascotasRV();
            }

            @Override
            public void onFailure(Call<PetResponse> call, Throwable t) {
                Toast.makeText(context,"Fallo inesperado de conexxión. Intentelo de nuevo.", Toast.LENGTH_LONG).show();
                Log.e("FALLO DE CONEXION", t.toString());
            }
        });
    }

    @Override
    public void obtenerIdUsuario() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorSearchUser();
        EndPointApi endPointApi = restApiAdapter.estalecerConnexionRestApiInstagram(gsonMediaRecent);
        Call<UserResponse> userInstaCall = endPointApi.getIdUser(ConstantesRestApi.UsuarioInsta);

        userInstaCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                usuarios = userResponse.getUsuarios();
                userId = usuarios.get(0).getId();
                obtenerMediosRecienteesByUserId(userId);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context,"Fallo inesperado de conexión ID_USER. Intentelo de nuevo." + ConstantesRestApi.URL_GET_USER_BY_NAME, Toast.LENGTH_LONG).show();
                Log.e("FALLO DE CONEXION", t.toString());
            }
        });
    }

    @Override
    public void obtenerMediosRecienteesByUserId(String user) {
        ConstantesRestApi.IdUser = user;
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorMediaRecent();
        EndPointApi endPointApi = restApiAdapter.estalecerConnexionRestApiInstagram(gsonMediaRecent);
        Call<PetResponse> petResponseCall = endPointApi.getRecentMediaByIdUser(ConstantesRestApi.IdUser);

        petResponseCall.enqueue(new Callback<PetResponse>() {
            @Override
            public void onResponse(Call<PetResponse> call, Response<PetResponse> response) {
                PetResponse petResponse = response.body();
                mascotas = petResponse.getMascotas();
                mostrarMascotasRV();
            }

            @Override
            public void onFailure(Call<PetResponse> call, Throwable t) {
                Toast.makeText(context,"Fallo inesperado de conexión RECENTS. Intentelo de nuevo.", Toast.LENGTH_LONG).show();
                Log.e("FALLO DE CONEXION", t.toString());
            }
        });
    }
}
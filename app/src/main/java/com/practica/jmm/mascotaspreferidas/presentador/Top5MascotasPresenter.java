package com.practica.jmm.mascotaspreferidas.presentador;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.practica.jmm.mascotaspreferidas.fragment.IFragmentListaMascotas;
import com.practica.jmm.mascotaspreferidas.pojo.ConstructorMascotas;
import com.practica.jmm.mascotaspreferidas.pojo.Mascota;
import com.practica.jmm.mascotaspreferidas.pojo.Usuario;
import com.practica.jmm.mascotaspreferidas.restApi.ConstantesRestApi;
import com.practica.jmm.mascotaspreferidas.restApi.EndPointApi;
import com.practica.jmm.mascotaspreferidas.restApi.adapter.RestApiAdapter;
import com.practica.jmm.mascotaspreferidas.restApi.model.PetResponse;
import com.practica.jmm.mascotaspreferidas.restApi.model.UserResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sath on 5/01/17.
 */

public class Top5MascotasPresenter implements IFragmentListaMascotasPresenter {


    private IFragmentListaMascotas iFragmentListaMascotas;
    private Context context;
    private ConstructorMascotas constructorMascotas;
    private ArrayList<Mascota> mascotas;
    private  ArrayList<Mascota> mascotaOrder;
    private ArrayList<Usuario> usuarios;
    private String userId;

    public Top5MascotasPresenter(IFragmentListaMascotas iFragmentListaMascotas, Context context) {
        this.context = context;
        this.iFragmentListaMascotas = iFragmentListaMascotas;
        //obtenerMascotasDB();
        obtenerIdUser();
    }


    @Override
    public void obtenerMascotasDB() {
        constructorMascotas = new ConstructorMascotas(context);
        mascotas = constructorMascotas.obtenerDatos();

        Collections.sort(mascotas, new Comparator<Mascota>() {

            @Override
            public int compare(Mascota p1, Mascota p2){
                return new Integer(p2.getRaiting()).compareTo(new Integer(p1.getRaiting()));
            }

        });
        mascotaOrder = new ArrayList<>();
        for (int i = 0;i<5;i++){
            mascotaOrder.add(mascotas.get(i));
        }
        mostrarMascotasRV();
    }

    @Override
    public void obtenerMediosRecientes() {

    }

    @Override
    public void mostrarMascotasRV() {
        iFragmentListaMascotas.inicializaeAdaptador(iFragmentListaMascotas.crearAdaptador(mascotaOrder));
        iFragmentListaMascotas.generaLinearLayautVertical();

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
                Collections.sort(mascotas, new Comparator<Mascota>() {

                    @Override
                    public int compare(Mascota p1, Mascota p2){
                        return new Integer(p2.getRaiting()).compareTo(new Integer(p1.getRaiting()));
                    }

                });
                mascotaOrder = new ArrayList<>();
                for (int i = 0;i<5;i++){
                    mascotaOrder.add(mascotas.get(i));
                }
                mostrarMascotasRV();
            }

            @Override
            public void onFailure(Call<PetResponse> call, Throwable t) {
                Toast.makeText(context,"Fallo inesperado de conexión RECENTS. Intentelo de nuevo.", Toast.LENGTH_LONG).show();
                Log.e("FALLO DE CONEXION", t.toString());
            }
        });
    }

    @Override
    public void obtenerIdUser() {
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

}

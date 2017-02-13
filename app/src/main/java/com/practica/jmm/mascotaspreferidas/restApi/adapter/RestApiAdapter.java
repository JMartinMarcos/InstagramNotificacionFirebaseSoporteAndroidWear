package com.practica.jmm.mascotaspreferidas.restApi.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.practica.jmm.mascotaspreferidas.restApi.ConstantesRestApi;
import com.practica.jmm.mascotaspreferidas.restApi.EndPointApi;
import com.practica.jmm.mascotaspreferidas.restApi.deserializador.MascotaDeserializador;
import com.practica.jmm.mascotaspreferidas.restApi.deserializador.UsuarioDeserializador;
import com.practica.jmm.mascotaspreferidas.restApi.model.PetResponse;
import com.practica.jmm.mascotaspreferidas.restApi.model.UserResponse;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sath on 18/01/17.
 */

public class RestApiAdapter {

    public EndPointApi estalecerConnexionRestApiInstagram(Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(EndPointApi.class);
    }

    public EndPointApi estalecerConnexionRestApiInstagramNoGson(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(EndPointApi.class);
    }

    public Gson construyeGsonDeserializadorMediaRecent (){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PetResponse.class, new MascotaDeserializador());
        return gsonBuilder.create();
    }
    public Gson construyeGsonDeserializadorSearchUser (){
        GsonBuilder gsonBuilder2 = new GsonBuilder();
        gsonBuilder2.registerTypeAdapter(UserResponse.class, new UsuarioDeserializador());
        return gsonBuilder2.create();
    }

    public EndPointApi establecerConexionRestApiHeroku(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL_HEROKU)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(EndPointApi.class);

    }
}

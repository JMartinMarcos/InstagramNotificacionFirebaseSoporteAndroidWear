package com.practica.jmm.mascotaspreferidas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.practica.jmm.mascotaspreferidas.restApi.ConstantesRestApi;
import com.practica.jmm.mascotaspreferidas.restApi.EndPointApi;
import com.practica.jmm.mascotaspreferidas.restApi.adapter.RestApiAdapter;
import com.practica.jmm.mascotaspreferidas.restApi.model.LikeResponse;
import com.practica.jmm.mascotaspreferidas.restApi.model.UsuarioResponsePost;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sath on 9/02/17.
 */

public class RespuestasWear extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        final String ACTION_KEY_PERFIL = "PERFIL";
        final String ACTION_KEY_LIKE   = "LIKE";
        final String ACTION_KEY_USER   = "USER";

        String acction = intent.getAction();
        switch (acction){
            case ACTION_KEY_PERFIL:
                Intent perfil = new Intent(context.getApplicationContext(), MainActivity.class);
                perfil.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(perfil);
                break;
            case ACTION_KEY_LIKE:
                deleteLike();
                break;
            case ACTION_KEY_USER:
                String userID = intent.getStringExtra("USER").toString();
                Intent user = new Intent(context.getApplicationContext(), PerfilUsuarioLike.class);
                user.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                user.putExtra("USER",userID);
                context.startActivity(user);
                break;
        }
    }

    private void deleteLike(){

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        EndPointApi endPointApi = restApiAdapter.estalecerConnexionRestApiInstagramNoGson();
        Call<LikeResponse> likeResponseCall = endPointApi.desacerLikeMedia(ConstantesRestApi.IdMedia);

        likeResponseCall.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                Log.d("ID_FIREBASE", "YO QUE SE");

            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Log.d("ID_FIREBASE", "CHUNGO");

            }
        });


    }






}

package com.practica.jmm.mascotaspreferidas.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.practica.jmm.mascotaspreferidas.pojo.ConstructorMascotas;
import com.practica.jmm.mascotaspreferidas.pojo.Mascota;
import com.practica.jmm.mascotaspreferidas.R;
import com.practica.jmm.mascotaspreferidas.restApi.ConstantesRestApi;
import com.practica.jmm.mascotaspreferidas.restApi.EndPointApi;
import com.practica.jmm.mascotaspreferidas.restApi.JsonKeys;
import com.practica.jmm.mascotaspreferidas.restApi.adapter.RestApiAdapter;
import com.practica.jmm.mascotaspreferidas.restApi.model.LikeResponse;
import com.practica.jmm.mascotaspreferidas.restApi.model.UsuarioResponsePost;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sath on 22/12/16.
 */

public class MascotaAdaptador extends RecyclerView.Adapter<MascotaAdaptador.MascotaViewHolder>{

    ArrayList<Mascota> mascotas;

    int valorLike = 0;
    Activity activity;


    public MascotaAdaptador(ArrayList<Mascota> mascotas, Activity activity) {
        this.mascotas = mascotas;
        this.activity = activity;
    }

    @Override
    public MascotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mascota,parent,false);
        return new MascotaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MascotaViewHolder mascotaViewHolder, int position) {
        final Mascota mascota = mascotas.get(position);
        //mascotaViewHolder.imgImagen.setImageResource(mascota.getImagen());
        Picasso.with(activity).load(mascota.getImagen()).into(mascotaViewHolder.imgImagen);
        mascotaViewHolder.txtNombre.setText(mascota.getNombre());
       // mascotaViewHolder.txtRaiting.setText(mascota.getRaiting());
        ConstructorMascotas constructorMascotas = new ConstructorMascotas(activity);
        //mascotaViewHolder.txtRaiting.setText(String.valueOf(constructorMascotas.obtenerLikesContacto(mascota)));
        mascotaViewHolder.txtRaiting.setText(mascota.getRaiting());


        mascotaViewHolder.imgHuesoLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             valorLike = Integer.parseInt(mascotaViewHolder.txtRaiting.getText().toString());
             valorLike++;

                ConstantesRestApi.IdMedia = mascota.getId_medeia();
                mascota.setRaiting(""+valorLike);
                mascotaViewHolder.txtRaiting.setText(mascota.getRaiting());
                ConstructorMascotas constructorMascotas = new ConstructorMascotas(activity);
                constructorMascotas.darLikeCotnacto(mascota);

                RestApiAdapter restApiAdapter = new RestApiAdapter();
                EndPointApi endPointApi = restApiAdapter.estalecerConnexionRestApiInstagramNoGson();
                Call<LikeResponse> likeResponseCall = endPointApi.darLikeMedia(ConstantesRestApi.ACCESS_TOKEN,ConstantesRestApi.IdMedia);

                likeResponseCall.enqueue(new Callback<LikeResponse>() {
                    @Override
                    public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                        LikeResponse likeResponse = response.body();

                        //if(likeResponse.getCode() == JsonKeys.RESPONSE_OK){
                            String token = FirebaseInstanceId.getInstance().getToken();
                            RestApiAdapter restApiAdapter = new RestApiAdapter();
                            EndPointApi endPointApi = restApiAdapter.establecerConexionRestApiHeroku();
                        Call<UsuarioResponsePost> usuarioResponsePostCall = endPointApi.notificaLikeMedia("-KcYfD_tv8Q01112G2Md",ConstantesRestApi.IdUser,ConstantesRestApi.UsuarioInsta);
                 //       Call<UsuarioResponsePost> usuarioResponsePostCall = endPointApi.notificaLikeMedia("-KcIdeWtmdwndg4pzcW4",ConstantesRestApi.IdUser,ConstantesRestApi.UsuarioInsta);

                            usuarioResponsePostCall.enqueue(new Callback<UsuarioResponsePost>() {
                                @Override
                                public void onResponse(Call<UsuarioResponsePost> call, Response<UsuarioResponsePost> response) {
                                    UsuarioResponsePost usuarioResponsePost = response.body();
                                    Log.d("ID_FIREBASE", usuarioResponsePost.getId());
                                    Log.d("TOKEN_FIREBASE", usuarioResponsePost.getId_dispositivo());
                                    Log.d("ID_USUARIO_FIREBASE", usuarioResponsePost.getId_usuario_instagram());
                                  //  Log.d("ID_FOTO_INSTAGRAM", usuarioResponsePost.getId_foto_instagram());
                                }

                                @Override
                                public void onFailure(Call<UsuarioResponsePost> call, Throwable t) {

                                }
                            });
                        //}

                    }

                    @Override
                    public void onFailure(Call<LikeResponse> call, Throwable t) {

                    }
                });



            }
        });
        /*
        mascotaViewHolder.txtNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valorLike = Integer.parseInt(mascotaViewHolder.txtRaiting.getText().toString());
                valorLike++;
                mascota.setRaiting(""+valorLike);
                mascotaViewHolder.txtRaiting.setText(mascota.getRaiting());
                ConstructorMascotas constructorMascotas = new ConstructorMascotas(activity);
                constructorMascotas.darLikeCotnacto(mascota);
            }

        });
        */
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }

    public static class MascotaViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgImagen, imgHuesoLike,imgHuesoRaiting;
        private TextView txtNombre,txtRaiting;

        public MascotaViewHolder(View itemView) {
            super(itemView);
            imgImagen       = (ImageView) itemView.findViewById(R.id.imgImagen);
            imgHuesoLike    = (ImageView) itemView.findViewById(R.id.imgHuesoLike);
            imgHuesoRaiting = (ImageView) itemView.findViewById(R.id.imgHuesoRaiting);
            txtNombre       = (TextView)  itemView.findViewById(R.id.txtNombre);
            txtRaiting      = (TextView)  itemView.findViewById(R.id.txtRaiting);
        }
    }
}

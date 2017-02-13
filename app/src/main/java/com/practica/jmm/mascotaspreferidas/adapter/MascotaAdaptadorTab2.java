package com.practica.jmm.mascotaspreferidas.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.practica.jmm.mascotaspreferidas.R;
import com.practica.jmm.mascotaspreferidas.pojo.ConstructorMascotas;
import com.practica.jmm.mascotaspreferidas.pojo.Mascota;
import com.practica.jmm.mascotaspreferidas.pojo.Usuario;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sath on 22/12/16.
 */

public class MascotaAdaptadorTab2 extends RecyclerView.Adapter<MascotaAdaptadorTab2.MascotaViewHolder>{

    ArrayList<Mascota> mascotas;
    CircularImageView circularImageView;
    TextView nombreMascota;
    Activity activity;
    Usuario user;

    int valorLike = 0;

    public MascotaAdaptadorTab2(ArrayList<Mascota> mascotas, CircularImageView circularImageView, TextView nombreMascota, Activity activity,Usuario user) {
        this.mascotas = mascotas;
        this.circularImageView = circularImageView;
        this.nombreMascota = nombreMascota;
        this.activity = activity;
        this.user = user;
    }

    @Override
    public MascotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mascota_littel,parent,false);
        return new MascotaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MascotaViewHolder mascotaViewHolder, int position) {
        final Mascota mascota = mascotas.get(position);
        ///mascotaViewHolder.imgImagen.setImageResource(mascota.getImagen());
        Picasso.with(activity).load(mascota.getImagen()).into(mascotaViewHolder.imgImagen);
        mascotaViewHolder.txtNombre.setText(mascota.getNombre());
        nombreMascota.setText(user.getFullName().toString());
        ConstructorMascotas constructorMascotas = new ConstructorMascotas(activity);
        mascotaViewHolder.txtRaiting.setText(String.valueOf(constructorMascotas.obtenerLikesContacto(mascota)));
        //mascotaViewHolder.txtRaiting.setText(mascota.getRaiting());
        Picasso.with(activity).load(user.getPicture()).into(circularImageView);
        mascotaViewHolder.imgImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(activity).load(mascota.getImagen()).into(circularImageView);

                //circularImageView.setImageResource(mascota.getImagen());
                //nombreMascota.setText(mascota.getNombre());
            }
        });
        /*
        mascotaViewHolder.imgHuesoLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             valorLike = Integer.parseInt(mascotaViewHolder.txtRaiting.getText().toString());
             valorLike++;
                mascota.setRaiting(""+valorLike);
                mascotaViewHolder.txtRaiting.setText(mascota.getRaiting());
                ConstructorMascotas constructorMascotas = new ConstructorMascotas(activity);
                constructorMascotas.darLikeCotnacto(mascota);
            }
        });*/
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

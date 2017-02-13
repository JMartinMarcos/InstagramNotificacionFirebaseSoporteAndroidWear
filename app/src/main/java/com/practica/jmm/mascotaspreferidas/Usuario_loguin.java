package com.practica.jmm.mascotaspreferidas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.practica.jmm.mascotaspreferidas.restApi.ConstantesRestApi;
import com.practica.jmm.mascotaspreferidas.restApi.EndPointApi;

public class Usuario_loguin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_loguin);
    }

    public void guardarUsuario(View v){
        SharedPreferences preferenteUsuario = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferenteUsuario.edit();

        EditText edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        String usuarioInstag = edtUsuario.getText().toString();

        editor.putString("User", usuarioInstag);
        editor.commit();

        ConstantesRestApi.UsuarioInsta = usuarioInstag;
        Toast.makeText(Usuario_loguin.this, "Usuario guardado : " + ConstantesRestApi.UsuarioInsta, Toast.LENGTH_SHORT).show();
        Intent mUser = new Intent(Usuario_loguin.this,MainActivity.class);
        stopService(mUser);
        startActivity(mUser);
    }

    public void recuperarUsuarioGuardado(View v){

    }
}

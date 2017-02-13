package com.practica.jmm.mascotaspreferidas;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class FormularioMail extends AppCompatActivity {

    private static final int SOLICITUD_PERMISO = 1;
    EditText nombre;
    EditText email;
    EditText cuerpo;
    private Context context;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_mail);

        context = getApplicationContext();
        activity = this;

        nombre = (EditText) findViewById(R.id.edtNombre);
        email  = (EditText) findViewById(R.id.edtEmail);
        cuerpo = (EditText) findViewById(R.id.edtContacto);

        Button boton = (Button) findViewById(R.id.btnSiguinte);
//        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET},SOLICITUD_PERMISO);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                creaMail();
            }
        });
    }

    public boolean estadoPermiso(){
        int resultado = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);
        if (resultado == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    public void solicitarPermiso(){
        try {
              if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.INTERNET)) {
                  Toast.makeText(FormularioMail.this, "El permiso se encuentra otorgado, para desactivarlo ir a la seccion Ajustes.", Toast.LENGTH_SHORT).show();
              } else {
                   ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET},SOLICITUD_PERMISO);
              }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case SOLICITUD_PERMISO:
                if(estadoPermiso()) {
                    Toast.makeText(FormularioMail.this, "Ya está activo el permioso para Internet", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(FormularioMail.this, "No está activo el permioso para Internet", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void creaMail() {

 //       solicitarPermiso();

        Properties props = new Properties();

// Nombre del host de correo, es smtp.gmail.com
        props.setProperty("mail.smtp.host", "smtp.gmail.com");

// TLS si está disponible
        props.setProperty("mail.smtp.starttls.enable", "true");

// Puerto de gmail para envio de correos
        props.setProperty("mail.smtp.port","587");

// Nombre del usuario
        props.setProperty("mail.smtp.user", "jmartin.marcos@gmail.com");

// Si requiere o no usuario y password para conectarse.
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        //session.setDebug(true);
        MimeMessage message = new MimeMessage(session);

// Quien envia el correo

        try {
            message.setFrom(new InternetAddress(email.getText().toString()));

// A quien va dirigido

            message.addRecipient(Message.RecipientType.TO, new InternetAddress("jmartin.marcos@gmail.com"));
            message.setSubject(nombre.getText().toString());
            message.setText(cuerpo.getText().toString());

            Transport t = null;
            t = session.getTransport("smtp");
            t.connect("jmartin.marcos@gmal.com","********");
            t.sendMessage(message,message.getAllRecipients());
            t.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

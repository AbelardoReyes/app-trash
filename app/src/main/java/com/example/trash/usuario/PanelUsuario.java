package com.example.trash.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trash.R;
import com.example.trash.login.Login;

public class PanelUsuario extends AppCompatActivity {
    Button btnPerfil,btnAgregarCarrito,btnVerCarrito;
    TextView cerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_usuario);
        guardarSession();
        cerrar = findViewById(R.id.cerrarSesion);
        btnPerfil = findViewById(R.id.perfil);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("session_usuario", Context.MODE_PRIVATE);
                boolean session = false;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("session",session);
                editor.commit();
                Intent intent = new Intent(PanelUsuario.this, Login.class);
                startActivity(intent);
            }
        });
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PanelUsuario.this, PerfilUsuario.class);
                startActivity(intent);
            }
        });
    }
    public void guardarSession(){
        SharedPreferences preferences = getSharedPreferences("session_usuario", Context.MODE_PRIVATE);
        boolean session = true;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("session",session);
        editor.commit();
    }
}
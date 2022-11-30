package com.example.trash.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trash.R;

public class PanelUsuario extends AppCompatActivity {
    Button btnPerfil,btnAgregarCarrito,btnVerCarrito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_usuario);
        btnPerfil = findViewById(R.id.perfil);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PanelUsuario.this, PerfilUsuario.class);
                startActivity(intent);
            }
        });
    }
}
package com.example.trash.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.trash.R;

public class ActivarCuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activar_cuenta);
    }
    private void recibirDatos(){
        Bundle extras = getIntent().getExtras();
        String url = extras.getString("url");
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }
}
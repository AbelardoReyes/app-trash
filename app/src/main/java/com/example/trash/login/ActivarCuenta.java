package com.example.trash.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trash.R;
import com.example.trash.clases.SingletonRequest;
import com.example.trash.usuario.PanelUsuario;

import org.json.JSONObject;

public class ActivarCuenta extends AppCompatActivity {
    String ruta = "http://192.168.1.72:3333";
    Button btnenviarCodigo;
    EditText N1, N2, N3, N4;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activar_cuenta);
        //recibir datos
        Bundle extras = getIntent().getExtras();
        verficarCuenta();
        String url = ruta + extras.getString("url");
        Toast.makeText(this, "la url es " + url, Toast.LENGTH_SHORT).show();

        btnenviarCodigo = findViewById(R.id.enviarCodigo);
        N1 = findViewById(R.id.num1);
        N2 = findViewById(R.id.num2);
        N3 = findViewById(R.id.num3);
        N4 = findViewById(R.id.num4);
        btnenviarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = N1.getText().toString() + N2.getText().toString() + N3.getText().toString() + N4.getText().toString();
                Toast.makeText(ActivarCuenta.this, code, Toast.LENGTH_SHORT).show();
                JSONObject datos = new JSONObject();
                try {
                    datos.put("code", code);
                } catch (Exception e) {
                }
                JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.POST, url, datos, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ActivarCuenta.this, code, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ActivarCuenta.this, Login.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivarCuenta.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                SingletonRequest.getInstance(ActivarCuenta.this).addToRequestQue(peticion);
            }
        });
    }
    public void verficarCuenta(){
        SharedPreferences preferences = getSharedPreferences("cuenta_inactiva", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Boolean codigo = true;
        editor.putBoolean("codigo", codigo);
        editor.commit();
    }
}
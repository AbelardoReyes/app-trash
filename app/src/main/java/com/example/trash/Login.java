package com.example.trash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import  com.example.trash.clases.Respuesta;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.trash.clases.SingletonRequest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trash.usuario.PanelUsuario;
import com.google.gson.Gson;

import org.json.JSONObject;

public class Login extends AppCompatActivity {
    TextView registrarse;
    Button iniciarSesion;
    EditText email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registrarse = findViewById(R.id.registrarse);
        iniciarSesion = findViewById(R.id.iniciarSesion);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registrarse.class);
                startActivity(intent);
            }
        });
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.1.72:8000/api/login";
                JSONObject login = new JSONObject();
                try {
                    login.put("email", email.getText().toString());
                    login.put("password", password.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JsonObjectRequest usuario = new JsonObjectRequest
                        (Request.Method.POST, url, login, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response", response.toString());
                        Gson gson = new Gson();
                        Respuesta respuesta = gson.fromJson(response.toString(), Respuesta.class);
                        if (respuesta.getResponse().equals("ok")) {
                            Intent intent = new Intent(Login.this, PanelUsuario.class);
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error", error.toString());
                        Toast.makeText(Login.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                    }
                });
                SingletonRequest.getInstance(Login.this).addToRequestQue(usuario);
            }
        });
    }
}
package com.example.trash.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import com.example.trash.admin.PanelAdmin;
import com.example.trash.R;
import  com.example.trash.clases.Respuesta;

import android.content.SharedPreferences;
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
        cargarSession();
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
                String url = "http://trash-api.me:3333/login";
                JSONObject login = new JSONObject();
                try {
                    login.put("password", password.getText().toString());
                    login.put("email", email.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JsonObjectRequest usuario = new JsonObjectRequest
                        (Request.Method.POST, url, login, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Gson gson = new Gson();
                                Respuesta respuesta = gson.fromJson(response.toString(), Respuesta.class);
                                if (respuesta.getStatus().equals("201") && respuesta.getRole().equals("3")) {
                                    Intent intent = new Intent(Login.this, PanelUsuario.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(Login.this, PanelAdmin.class);
                                    startActivity(intent);
                                }
                                Toast.makeText(Login.this, respuesta.getToken(), Toast.LENGTH_SHORT).show();
                                String token = respuesta.getToken();
                                guardarToken(token);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Error", error.toString());
                                Toast.makeText(Login.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                            }
                        });
                SingletonRequest.getInstance(Login.this).addToRequestQue(usuario);
                cargarToken();
            }

        });
    }
    public void cargarToken() {
        SharedPreferences preferences = getSharedPreferences("guardarToken", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "No encontrado");

        Log.i("Token", token);
    }
    public void guardarToken(String token){
        SharedPreferences preferences = getSharedPreferences("guardarToken", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.commit();
    }
    public void  cargarSession(){
        SharedPreferences preferences = getSharedPreferences("session_usuario", Context.MODE_PRIVATE);
        if(preferences.getBoolean("session",true)==true){
            Intent intent = new Intent(Login.this, PanelUsuario.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Inicia Sesion", Toast.LENGTH_SHORT).show();
        }
    }
}
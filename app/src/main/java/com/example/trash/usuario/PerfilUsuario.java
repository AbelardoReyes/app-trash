package com.example.trash.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import  com.example.trash.clases.DataUsuario;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trash.R;
import com.example.trash.clases.SingletonRequest;
import com.example.trash.clases.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerfilUsuario extends AppCompatActivity {
    TextView name, email, phone_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone_number = findViewById(R.id.phone);
        SharedPreferences preferences = getSharedPreferences("guardarToken", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "No encontrado");

        obtenerUsuario(token);
    }

    public void obtenerUsuario(String token) {
        String url = "http://192.168.1.72:8000/api/info";
        JsonObjectRequest usuario = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(PerfilUsuario.this, "Usuario obtenido", Toast.LENGTH_SHORT).show();
                            Gson gson = new Gson();
                            Usuario usuario = gson.fromJson(response.toString(), Usuario.class);
                            name.setText(usuario.getName());
                            email.setText(usuario.getEmail());
                            phone_number.setText(usuario.getPhone_number());
                        }
                        catch (JsonIOException e) {
                            e.printStackTrace();
                            Toast.makeText(PerfilUsuario.this, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error", error.toString());
                        Toast.makeText(PerfilUsuario.this, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };
        SingletonRequest.getInstance(PerfilUsuario.this).addToRequestQue(usuario);
    }
}
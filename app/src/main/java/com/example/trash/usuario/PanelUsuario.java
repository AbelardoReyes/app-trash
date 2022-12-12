package com.example.trash.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trash.clases.SingletonRequest;
import com.example.trash.usuario.VerCarritos;
import com.example.trash.R;
import com.example.trash.Views.MainActivity;
import com.example.trash.login.Login;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        btnAgregarCarrito = findViewById(R.id.agregarCarrito);
        btnVerCarrito = findViewById(R.id.verCarritos);
        SharedPreferences preferences = getSharedPreferences("guardarToken", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "No encontrado");
        Log.i("Token", token);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("guardarToken", Context.MODE_PRIVATE);
                String token = preferences.getString("token", "No encontrado");
                Log.i("Token", token);
                SharedPreferences preferences1 = getSharedPreferences("session_usuario", Context.MODE_PRIVATE);
                boolean session = false;
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putBoolean("session",session);
                editor.commit();
                Intent intent = new Intent(PanelUsuario.this, Login.class);
                startActivity(intent);
                String url = "https://trash-api.me/api/user/logout";
                JsonObjectRequest eliminarToken = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Respuesta", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error", error.toString());
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };
                SingletonRequest.getInstance(PanelUsuario.this).addToRequestQue(eliminarToken);
            }
        });
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PanelUsuario.this, PerfilUsuario.class);
                startActivity(intent);
            }
        });
        btnAgregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PanelUsuario.this, NuevoCarrito.class);
                startActivity(intent);
            }
        });
        btnVerCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PanelUsuario.this, VerCarritos.class);
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
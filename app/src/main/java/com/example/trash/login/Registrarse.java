package com.example.trash.login;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.trash.R;
import com.example.trash.clases.Respuesta;
import com.example.trash.clases.SingletonRequest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

public class Registrarse extends AppCompatActivity {
    Button enviar;
    EditText nombre, telefono, email, contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        enviar = findViewById(R.id.enviar);
        nombre = findViewById(R.id.nombre);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);
        contraseña = findViewById(R.id.contraseña);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Aqui se debe enviar el JSON al servidor;
                String urls = "https://trash-api.me/api/user/register";
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("name",nombre.getText().toString());
                    jsonBody.put("password",contraseña.getText().toString());
                    jsonBody.put("email",email.getText().toString());
                    jsonBody.put("phone_number",telefono.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, urls, jsonBody, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("Response", response.toString());
                                String url;
                                Gson gson = new Gson();
                                Respuesta respuesta = gson.fromJson(response.toString(), Respuesta.class);
                                url = respuesta.getUrl();
                                Toast.makeText(Registrarse.this, "Tu usuario se registro correctamente!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Registrarse.this, ActivarCuenta.class);
                                intent.putExtra("url",url);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error", error.toString());
                                Toast.makeText(Registrarse.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                            }
                        });
                SingletonRequest.getInstance(getApplicationContext()).addToRequestQue(jsonObjectRequest);
            }
        });
    }
}
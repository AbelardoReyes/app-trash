package com.example.trash.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.example.trash.clases.SingletonRequest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trash.R;
import com.example.trash.clases.SingletonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CuentaAdafruit extends AppCompatActivity {
    Button btnAdafruit;
    EditText userAdafruit, keyAdafruit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_adafruit);
        btnAdafruit = findViewById(R.id.btnGuardarAdafruit);
        userAdafruit = findViewById(R.id.username);
        keyAdafruit = findViewById(R.id.iokey);
        SharedPreferences preferences = getSharedPreferences("guardarToken", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "No encontrado");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        btnAdafruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.1.72:8000/api/cuentaAdafruit";
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", userAdafruit.getText().toString());
                    jsonObject.put("active_key", keyAdafruit.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest Adafruit = new JsonObjectRequest
                        (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(CuentaAdafruit.this, "Cuenta guardada", Toast.LENGTH_SHORT).show();

                                Log.d("Adafruit", response.toString());
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(CuentaAdafruit.this, "Cuenta", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CuentaAdafruit.this, PerfilUsuario.class);
                                startActivity(intent);
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };
                SingletonRequest.getInstance(CuentaAdafruit.this).addToRequestQue(Adafruit);
            }
        });
    }
}
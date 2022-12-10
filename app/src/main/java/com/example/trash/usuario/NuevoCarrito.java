package com.example.trash.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trash.R;
import com.example.trash.admin.PanelAdmin;
import com.example.trash.clases.Respuesta;
import com.example.trash.clases.SingletonRequest;
import com.example.trash.login.Login;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NuevoCarrito extends AppCompatActivity {
    EditText carName;
    CheckBox geolocalizador, temperatura, peso;
    Button btnEnviar;
    public ArrayList<String> miArreglo = new ArrayList<String>();
    int idSensores[] = new int[3];
    int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_carrito);
        geolocalizador = findViewById(R.id.geolocalizador);
        temperatura = findViewById(R.id.tempearatura);
        peso = findViewById(R.id.peso);
        carName = findViewById(R.id.carName);
        btnEnviar = findViewById(R.id.enviarAdafruit);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarDatos();
            }
        });
    }

    public void enviarDatos() {
        SharedPreferences preferences = getSharedPreferences("guardarToken", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "No encontrado");
        JSONArray sensores = new JSONArray();
        String url = "http://192.168.1.72:8000/api/adafruit/addcar";
        if (geolocalizador.isChecked()) {
            idSensores[j] = 1;
            j++;
        }
        if (temperatura.isChecked()) {
            j = 1;
            idSensores[j] = 2;
            j++;
        }
        if (peso.isChecked()) {
            idSensores[j] = 3;
            j++;}
        for (int i = 0; i < idSensores.length; i++) {
            sensores.put(idSensores[i]);}
        JSONObject carrito = new JSONObject();
        try {
            carrito.put("car_name", carName.getText().toString());
            carrito.put("sensors", sensores);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, carrito.toString(), Toast.LENGTH_SHORT).show();
        JsonObjectRequest car = new JsonObjectRequest(Request.Method.POST, url, carrito,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(NuevoCarrito.this, "Carrito creado", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Log.i("Error", carrito.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        SingletonRequest.getInstance(this).addToRequestQue(car);
    }
}
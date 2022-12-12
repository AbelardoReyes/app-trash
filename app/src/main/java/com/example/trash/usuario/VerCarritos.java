package com.example.trash.usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trash.Adapters.CarritoAdapter;
import com.example.trash.R;
import com.example.trash.clases.Carrito;
import com.example.trash.clases.ResponseCars;
import com.example.trash.clases.SingletonRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerCarritos extends AppCompatActivity {
    private ArrayList<Carrito> CarritoList;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_carritos);
        recyclerview = findViewById(R.id.recyclador);
        CarritoList = new ArrayList<>();
        Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show();
        setGetCarrito();
    }

    private void setAdapter(CarritoAdapter adapter) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapter);
    }
    private void setGetCarrito() {
        SharedPreferences preferences = getSharedPreferences("guardarToken", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "No encontrado");

        Log.i("Token", token);
        String url = "https://trash-api.me/api/adafruit/getcars";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                ResponseCars carrito = gson.fromJson(String.valueOf(response), ResponseCars.class);
                List<Carrito> results = carrito.getCars();
                CarritoAdapter adapter = new CarritoAdapter(results);
                setAdapter(adapter);
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
        SingletonRequest.getInstance(getApplicationContext()).addToRequestQue(jsonObjectRequest);
    }
}
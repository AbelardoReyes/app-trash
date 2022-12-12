package com.example.trash.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trash.R;
import com.example.trash.clases.Carrito;

import java.util.ArrayList;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.MyViewHolder>{
    private ArrayList<Carrito> CarritoList;

    public CarritoAdapter(ArrayList<Carrito> carritoList) {
        CarritoList = carritoList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtname;
        public MyViewHolder(final View view){
            super(view);
            txtname = view.findViewById(R.id.nombreCarrito);
        }
    }

    @NonNull
    @Override
    public CarritoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_carritos,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoAdapter.MyViewHolder holder, int position) {
        String name = CarritoList.get(position).getName();
        holder.txtname.setText(name);
    }

    @Override
    public int getItemCount() {
        return CarritoList.size();
    }
}

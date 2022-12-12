package com.example.trash.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trash.R;
import com.example.trash.Views.MainActivity;
import com.example.trash.clases.Carrito;

import java.util.ArrayList;
import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.MyViewHolder>{
    private List<Carrito> CarritoList;

    public CarritoAdapter(List<Carrito> carritoList) {
        CarritoList = carritoList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtname;
        private ImageView imagen;
        public MyViewHolder(final View view){
            super(view);
            imagen = view.findViewById(R.id.imageView4);
            txtname = view.findViewById(R.id.nombreCarrito);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
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
        holder.imagen.setImageResource(R.drawable.imagencarrito);
    }

    @Override
    public int getItemCount() {
        return CarritoList.size();
    }
}

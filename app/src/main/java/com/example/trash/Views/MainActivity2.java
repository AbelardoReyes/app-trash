package com.example.trash.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.trash.viewModel.LiveDataBluetoothViewModel;
import android.os.Bundle;
import android.widget.TextView;

import com.example.trash.Models.Bluetooth;
import com.example.trash.R;
import  com.example.trash.viewModel.LiveDataBluetoothViewModel;

public class MainActivity2 extends AppCompatActivity {
    TextView txtblue;
    private LiveDataBluetoothViewModel liveDataBluetoothViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtblue=findViewById(R.id.txtblue);

        liveDataBluetoothViewModel= new ViewModelProvider(this).get(LiveDataBluetoothViewModel.class);
        liveDataBluetoothViewModel.getBluetoothMutableLiveData().observe(this, new Observer<Bluetooth>() {
            @Override
            public void onChanged(Bluetooth bluetooth) {
                txtblue.setText(bluetooth.getName());
            }
        });
        liveDataBluetoothViewModel.setBluetoothMutableLiveData(new Bluetooth("rasssmiro","direccion","estado"));

    }
}
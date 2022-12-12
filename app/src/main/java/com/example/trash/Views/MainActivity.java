package com.example.trash.Views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trash.Adapters.BluetoothAdapterLista;
import com.example.trash.Models.Bluetooth;
import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trash.Adapters.BluetoothAdapterLista;
import com.example.trash.Models.Bluetooth;
import com.example.trash.R;
import com.example.trash.clases.ResponseAdafruit;
import com.example.trash.clases.SingletonRequest;
import com.example.trash.socket.ConnectedThread;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView txtblue, mBluetoothStatus, mTextViewAngleRight, mTextViewStrengthRight, mTextViewCoordinateRight;
    TextView txtTapa;
    TextView txtHumedad;
    TextView txtTemperatura;
    Button btnPedirDatos;
    Switch btnconectar;
    BluetoothAdapter mBluetoothAdapter;
    List<Bluetooth> ListaBluetooth;
    Spinner SpnListaBluetooth;
    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier
    public final static int MESSAGE_READ = 2;
    private final static int CONNECTING_STATUS = 3;
    private BluetoothSocket mBTSocket = null;
    private Handler mHandler;
    private ConnectedThread mConnectedThread;

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothStatus = (TextView) findViewById(R.id.mBluetoothStatus);
        SpnListaBluetooth = (Spinner) findViewById(R.id.SpnListaBluetooth);
        btnPedirDatos = (Button) findViewById(R.id.pedirDatosAdafruit);
        txtTapa = (TextView) findViewById(R.id.adafruitTapa);
        txtHumedad = (TextView) findViewById(R.id.adafruitHumedad);
        txtTemperatura = (TextView) findViewById(R.id.adafruitTemperatura);
        ListaBluetooth = new ArrayList<>();
        btnconectar = (Switch) findViewById(R.id.btnconectar);
        pedirDatosAdafruit();

        btnPedirDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirDatosAdafruit();
            }
        });
        btnconectar.setOnCheckedChangeListener((v,i) -> {
            if(!mBluetoothAdapter.isEnabled()) {
                bluetoothOn();

            }
        });
        if (mBluetoothAdapter.isEnabled()) {
            btnconectar.setChecked(true);
        } else {
            btnconectar.setChecked(false);
        }


        listPairedDevices();
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_READ) {
                    String readMessage = null;
                    readMessage = new String((byte[]) msg.obj, StandardCharsets.UTF_8);
                    txtblue.setText(readMessage);
                }

                if (msg.what == CONNECTING_STATUS) {
                    char[] sConnected;
                    if (msg.arg1 == 1)
                        mBluetoothStatus.setText(getString(R.string.BTConnected) + msg.obj);
                    else
                        mBluetoothStatus.setText(getString(R.string.BTconnFail));
                }
            }
        };
        SpnListaBluetooth.setOnItemSelectedListener(this);


        mTextViewAngleRight = (TextView) findViewById(R.id.textView_angle_right);
        mTextViewStrengthRight = (TextView) findViewById(R.id.textView_strength_right);
        mTextViewCoordinateRight = findViewById(R.id.textView_coordinate_right);

        final JoystickView joystickRight = (JoystickView) findViewById(R.id.joystickView_right2);


        joystickRight.setOnMoveListener(new JoystickView.OnMoveListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onMove(int angle, int strength) {
                mTextViewAngleRight.setText(angle + "°");
                mTextViewStrengthRight.setText(strength + "%");
                mTextViewCoordinateRight.setText(String.format("x%03d:y%03d", joystickRight.getNormalizedX(), joystickRight.getNormalizedY()));

                JSONObject datosEnviar = new JSONObject();

                try {
                    datosEnviar.put("x", joystickRight.getNormalizedX());
                    datosEnviar.put("y", joystickRight.getNormalizedY());
                    datosEnviar.put("angulo", angle);
                    datosEnviar.put("strength", strength);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mBluetoothAdapter != null) {
                    if (mConnectedThread != null) {
                        mConnectedThread.write(datosEnviar.toString() + "?");

                    }
                }
            }
        }, 50);

    }

    public void bluetoothOn() {

        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
            finish();
        } else {
            if (mBluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(), "Bluetooth is already on", Toast.LENGTH_LONG).show();
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                mGetContent.launch(enableBtIntent);
            }
        }
    }

    private void listPairedDevices() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
        }
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter.getBondedDevices();
        ListaBluetooth.clear();
        if (mBluetoothAdapter.isEnabled()) {

            mPairedDevices.forEach(device -> {
                BluetoothDevice mDevice = device;
//                Log.i("listPairedDevices: ",mDevice.getName()+" "+mDevice.getAddress());
                ListaBluetooth.add(new Bluetooth(mDevice.getName(), mDevice.getAddress(), "Paired"));
            });
//
            SpnListaBluetooth.setAdapter(new BluetoothAdapterLista(this, R.layout.itembluetooth, ListaBluetooth));

            Toast.makeText(getApplicationContext(), getString(R.string.show_paired_devices), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), getString(R.string.BTnotOn), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        BluetoothAdapterLista adapter = (BluetoothAdapterLista) adapterView.getAdapter();
        Bluetooth mDevice = adapter.getListaBluetooth().get(i);

        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getBaseContext(), getString(R.string.BTnotOn), Toast.LENGTH_SHORT).show();
            return;
        }

        mBluetoothStatus.setText(getString(R.string.cConnet));
        // Get the device MAC address, which is the last 17 chars in the View

        final String name = mDevice.getName();
        final String address = mDevice.getAddress();

        // Spawn a new thread to avoid blocking the GUI one
        new Thread() {
            @Override
            public void run() {
                boolean fail = false;

                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

                try {
                    mBTSocket = createBluetoothSocket(device);
                } catch (IOException e) {
                    fail = true;
                    Toast.makeText(getBaseContext(), getString(R.string.ErrSockCrea), Toast.LENGTH_SHORT).show();
                }
                // Establish the Bluetooth socket connection.
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                    }
                    mBTSocket.connect();
                } catch (IOException e) {
                    try {
                        fail = true;
                        mBTSocket.close();
                        mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                .sendToTarget();
                    } catch (IOException e2) {
                        //insert code to deal with this
                        Toast.makeText(getBaseContext(), getString(R.string.ErrSockCrea), Toast.LENGTH_SHORT).show();
                    }
                }
                if (!fail) {
                    mConnectedThread = new ConnectedThread(mBTSocket, mHandler);
                    // Start the thread
                    mConnectedThread.run();
                    mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name)
                            .sendToTarget();
                }
            }
        }.start();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_MODULE_UUID);
        } catch (Exception e) {
            Log.e("Error", "Could not create Insecure RFComm Connection", e);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

        }
        return device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }
    private void pedirDatosAdafruit(){
        SharedPreferences preferences = getSharedPreferences("guardarToken", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "No encontrado");
        String url = "https://trash-api.me/api/adafruit/getdata";
        JsonObjectRequest pedirDatosAdafruit = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                ResponseAdafruit responseAdafruit =
                        gson.fromJson(response.toString(), ResponseAdafruit.class);
                txtTapa.setText(responseAdafruit.getTapa());
                txtHumedad.setText(responseAdafruit.getHumedad());
                txtTemperatura.setText(responseAdafruit.getTemperatura());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        SingletonRequest.getInstance(getApplicationContext()).addToRequestQue(pedirDatosAdafruit);
    }
}

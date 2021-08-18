package com.vsga.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView sensorText;
    private SensorManager sensorManager;
    private TextView sensorAccelerometerText;
    private TextView sensorProximityText;
    private Sensor sensorAccelerometer;
    private Sensor sensorProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorText = findViewById(R.id.sensor_list);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometerText = findViewById(R.id.sensor_accelerometer);
        sensorProximityText = findViewById(R.id.sensor_proximity);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorTx = new StringBuilder();
        for (Sensor sensor:sensorList){
            sensorTx.append(sensor.getName()+" "+sensor.getVendor()+"\n");
        }
        sensorText.setText(sensorTx.toString());

        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensorAccelerometer == null){
            Toast.makeText(this, "No Accelerometer Sensor", Toast.LENGTH_SHORT).show();
        }
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (sensorProximity == null)
            Toast.makeText(this, "No Proximity Sensor", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (sensorAccelerometer != null){
            sensorManager.registerListener(this,sensorAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorProximity != null){
            sensorManager.registerListener( this, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener((SensorEventListener) this);
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        int sensorType = event.sensor.getType();
        float value = event.values[0];
        if (sensorType == Sensor.TYPE_ACCELEROMETER){
            sensorAccelerometerText.setText(String.format("Accelerometer Sensor: %1$.2f", value));
        }
        if (sensorType == Sensor.TYPE_PROXIMITY){
            sensorProximityText.setText(String.format("Proximity Sensor: %1$.2f", value));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

     }
}
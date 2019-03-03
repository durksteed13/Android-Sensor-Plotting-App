package com.example.sensorplotting;

import android.content.Intent;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {

    SensorManager am;
    Sensor a;
    PlotView pv;
    AccelScale as;
    AccelYBall ay;

    int ct = 0;

    Button b;
    Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        pv = (PlotView) findViewById(R.id.plotview);
        as = (AccelScale) findViewById(R.id.accelScale);
        ay = (AccelYBall) findViewById(R.id.accelYBall);

        am = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(am.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!= null) {
            a = am.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            am.registerListener(this, a, SensorManager.SENSOR_DELAY_UI);
        }

        b = (Button) findViewById(R.id.pause);
        b2 = (Button) findViewById(R.id.cont);
        b2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ct++;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && ct == 5) {
            float toAdd;
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            toAdd = (float) Math.sqrt(x * x + y * y + z * z);
            Log.v("MYTAG", "Accelerometer = " + toAdd);
            pv.addPoint(toAdd);
            as.addMean(pv.getRecentMean());
            pv.invalidate();
            as.invalidate();
            ay.addX(x);
            ay.invalidate();
            ct = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void pause(View v) {
        am.unregisterListener(this, a);
        b.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.VISIBLE);
    }

    public void cont(View v) {
        am.registerListener(this, a, SensorManager.SENSOR_DELAY_UI);
        b2.setVisibility(View.INVISIBLE);
        b.setVisibility(View.VISIBLE);
    }

    public void back(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        am.unregisterListener(this, a);
        startActivity(intent);
        pv.clearData();
    }
}

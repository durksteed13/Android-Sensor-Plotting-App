package com.example.sensorplotting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.view.View;

public class RandomSimulator extends AppCompatActivity {

    SensorManager am;
    Sensor a;
    PlotView pv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_simulator);

        pv = (PlotView) findViewById(R.id.plotview);
    }

    public void back(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        pv.clearData();
    }

    public void addpoint(View v) {
        float random = (float) (0 + Math.random() * (25 - 0));
        random = Math.round(random);
        pv.addPoint(random);
        pv.invalidate();
    }
}
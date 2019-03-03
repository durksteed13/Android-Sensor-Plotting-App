package com.example.sensorplotting;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t = (TextView) findViewById(R.id.acceldata);
        t.setText(accelInfo());

        TextView t2 = (TextView) findViewById(R.id.lightdata);
        t2.setText(lightInfo());

    }

    public void accel(View v) {
        Intent intent = new Intent(this, Accelerometer.class);
        startActivity(intent);
    }

    public void light(View v) {
        Intent intent = new Intent(this, Light.class);
        startActivity(intent);
    }

    public void random(View v) {
        Intent intent = new Intent(this, RandomSimulator.class);
        startActivity(intent);
    }

    public String accelInfo() {
        SensorManager am = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor a = am.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        String info = "";
        info += "Status: " + a.getName() + " is present \n";
        info += "Resolution: " + a.getResolution() + "\n";
        info += "Range: " + a.getMaximumRange() + "\n";
        info += "Delay: " + a.getMinDelay() + "\n";
        return info;
    }

    public String lightInfo() {
        SensorManager am = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor a = am.getDefaultSensor(Sensor.TYPE_LIGHT);
        String info = "";
        info += "Status: " + a.getName() + " is present \n";
        info += "Resolution: " + a.getResolution() + "\n";
        info += "Range: " + a.getMaximumRange() + "\n";
        info += "Delay: " + a.getMinDelay() + "\n";

        return info;
    }
}

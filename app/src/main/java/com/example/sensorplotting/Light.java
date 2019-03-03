package com.example.sensorplotting;

import android.content.Intent;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Light extends AppCompatActivity implements SensorEventListener {

    SensorManager am;
    Sensor a;
    PlotView pv;
    ImageView iv;
    TextView tv;

    int ct = 0;

    Button b;
    Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        pv = (PlotView) findViewById(R.id.plotview);
        iv = (ImageView) findViewById(R.id.brightness);
        tv = (TextView) findViewById(R.id.lighttext);

        am = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(am.getDefaultSensor(Sensor.TYPE_LIGHT)!= null) {
            a = am.getDefaultSensor(Sensor.TYPE_LIGHT);
            am.registerListener(this, a, SensorManager.SENSOR_DELAY_UI);
        }

        b = (Button) findViewById(R.id.pause);
        b2 = (Button) findViewById(R.id.cont);
        b2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ct++;
        if(ct ==5) {
            float light = event.values[0];
            pv.addPoint(light);
            pv.invalidate();

            if(light <= 100) {
                iv.setBackgroundResource(R.drawable.low);
                tv.setText("Low Brightness");
            }
            else  if( light <= 450){
                iv.setBackgroundResource(R.drawable.medium);
                tv.setText("Medium Brightness");
            }
            else {
                iv.setBackgroundResource(R.drawable.bright);
                tv.setText("High Brightness");
            }
            ct = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onResume() {
        super.onResume();
        am.registerListener(this, a, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        am.unregisterListener(this);
    }

    public void back(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        am.unregisterListener(this, a);
        startActivity(intent);
        pv.clearData();
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

    public void addpoint(View v) {
        float random = (float) (0 + Math.random() * (25 - 0));
        random = Math.round(random);
        Log.v("MYTAG", "rand = " + random);
        pv.addPoint(random);
        pv.invalidate();
    }
}

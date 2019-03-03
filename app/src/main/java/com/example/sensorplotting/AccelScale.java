package com.example.sensorplotting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class AccelScale extends View {

    ArrayList<Float> means = new ArrayList<>();
    Float scaler = (float) 0;

    public AccelScale(Context context) {
        super(context);
    }

    public AccelScale(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AccelScale(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AccelScale(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.parseColor("#999999"));
        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(2);

        Paint gp = new Paint();
        gp.setColor(Color.parseColor("#33ccff"));
        gp.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(2);

        if(means.isEmpty()) {
            means.add((float)0);
        }

        scaler = (float) getWidth()/100;
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), p);
        canvas.drawLine(0, getHeight(), 0, getHeight()-10, p);
        canvas.drawLine(getWidth(), getHeight(), getWidth(), getHeight()-10, p);
        canvas.drawText("mean", 0, 10, gp);
        canvas.drawText("0", 2, getHeight(), p);
        canvas.drawText("100", getWidth()-25, getHeight(), p);

        //draw plot
        float toPlot = means.get(means.size()-1);
        float yscale = (float) .15;
        float xscale = 15;
        for(int i = 0; i < 3; i++) {
            canvas.drawLine(scaler*toPlot-xscale, getHeight(), scaler*toPlot-xscale, getHeight() - yscale*getHeight(), gp);
            xscale-=5;
            yscale+=.15;
        }
        canvas.drawLine(scaler * means.get(means.size()-1), getHeight(), scaler * means.get(means.size()-1),  getHeight()-((float)0.6*getHeight()), gp);
        canvas.drawText("(" + Float.toString(toPlot) +")", scaler * means.get(means.size()-1) -12, getHeight()-((float)0.65*getHeight()), p);
        for(int i = 0; i < 4; i++) {
            canvas.drawLine(scaler*toPlot+xscale, getHeight(), scaler*toPlot+xscale, getHeight() - yscale*getHeight(), gp);
            xscale+=5;
            yscale-=.15;
        }
    }

    public void addMean(float f) {
        means.add(f);
    }
}

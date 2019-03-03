package com.example.sensorplotting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class AccelYBall extends View {

    ArrayList<Float> values = new ArrayList<>();
    Float scaler = (float) 0;

    public AccelYBall(Context context) {
        super(context);
    }

    public AccelYBall(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AccelYBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AccelYBall(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        gp.setColor(Color.parseColor("#ff3333"));
        gp.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(2);

        Paint bp = new Paint();
        bp.setColor(Color.parseColor("#cccccc"));
        bp.setStyle(Paint.Style.FILL);


        if(values.isEmpty()) {
            values.add((float)0);
        }

        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), p);
        canvas.drawLine(0, getHeight(), 0, getHeight()-10, p);
        canvas.drawLine(getWidth(), getHeight(), getWidth(), getHeight()-10, p);
        canvas.drawLine(getWidth()/2, getHeight(), getWidth()/2, getHeight()-10, p);
        canvas.drawText("tilt", 0, 10, gp);

        //draw plot
        canvas.drawCircle(scaleX(values.get(values.size()-1))-10, getHeight()/2, 20, bp);
    }

    public void addX(float f) {
        values.add(f);
    }

    public float scaleX(float x) {
        if(Math.abs(x) > 7) {
            if(x > 0) {
                return 7;
            }
            return -7;
        }
        if(x == 0) {
            return getWidth()/2;
        }
        if(x > 0) {
            float scaler = (getWidth()/2)/7;
            float toPlot = x*scaler;
            return getWidth()/2 - Math.abs(toPlot);
        }
        else {
            float scaler = (getWidth()/2)/7;
            float toPlot = x*scaler;
            return getWidth()/2 + Math.abs(toPlot);
        }
    }
}

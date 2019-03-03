package com.example.sensorplotting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class PlotView extends View {

    ArrayList<Float> values = new ArrayList<Float>();
    ArrayList<Float> means = new ArrayList<Float>();
    ArrayList<Float> stdevs = new ArrayList<Float>();
    ArrayList<Double> timeCount = new ArrayList<Double>();
    Paint gp = new Paint();
    Paint gp2 = new Paint();
    Paint vp = new Paint();
    Paint mp = new Paint();
    Paint sp = new Paint();
    float scaler;

    public PlotView(Context context) {
        super(context);
    }

    public PlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlotView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setColors();

        if (timeCount.isEmpty()) {
            for(double i = 0; i < 3.5; i+= 0.5) {
                timeCount.add(i);
            }
        }

        if(values.isEmpty()) {
            values.add((float) 0);
            means.add((float)0);
            stdevs.add((float)0);
        }

        float yspace = (getHeight()-15)/5;
        float xspace = (getWidth()-15)/7;
        float yinc = yspace;
        float xinc = xspace;
        float maxy = maxYPlot();
        float yaxis = maxy/5;
        int yaxtext = (int) yaxis;

        scaler = (getHeight() - 15)/maxYPlot();

        //10dp padding for x
        //15dp padding for y
        //draw lines on graph
        canvas.drawText("seconds", 0, (float) getHeight(), gp);
        for(int i = 0; i < 7; i++) {
            canvas.drawLine(xinc + 10, (float) getHeight() - 15, xinc + 10, 0, gp);
            canvas.drawText(Double.toString(timeCount.get(i)), (xinc) +3, (float) getHeight(), gp2);

            if(i < 5) {
                canvas.drawLine(10, (float) getHeight() - (15 + yinc) + 15,  (float)getWidth()+10, (float) getHeight() - (15 + yinc) + 15, gp);
                canvas.drawText(Integer.toString(yaxtext), 0, (float) getHeight() - (15 + yinc) + 30, gp2);
            }
            yinc += yspace; yaxtext += yaxis; xinc += xspace;
        }

        float plotx = xspace;
        float prevxv = 0; float prevyv = 0;
        float prevxm = 0; float prevym = 0;
        float prevxs = 0; float prevys = 0;
        for(int i = 0; i < values.size(); i++) {
            canvas.drawCircle(plotx + 10, scaleY(stdevs.get(i)), 3, sp);
            canvas.drawCircle(plotx + 10, scaleY(means.get(i)), 3, mp);
            canvas.drawCircle(plotx + 10, scaleY(values.get(i)), 3, vp);
            if(i != 0) {
                canvas.drawLine(prevxs, prevys, plotx + 10, scaleY(stdevs.get(i)), sp);
                canvas.drawLine(prevxm, prevym, plotx + 10, scaleY(means.get(i)), mp);
                canvas.drawLine(prevxv, prevyv, plotx + 10, scaleY(values.get(i)), vp);
            }
            prevxs = plotx + 10; prevys = scaleY(stdevs.get(i));
            prevxm = plotx + 10; prevym = scaleY(means.get(i));
            prevxv = plotx + 10; prevyv = scaleY(values.get(i));
            plotx += xspace;
        }
    }

    public void setColors() {
        gp.setColor(Color.parseColor("#999999"));
        gp.setStyle(Paint.Style.FILL);

        gp2.setColor(Color.parseColor("#ff3333"));
        gp2.setStyle(Paint.Style.FILL);

        vp.setColor(Color.parseColor("#333399"));
        vp.setStyle(Paint.Style.FILL);
        vp.setStrokeWidth(2);

        mp.setColor(Color.parseColor("#33ccff"));
        mp.setStyle(Paint.Style.FILL);
        mp.setStrokeWidth(2);

        sp.setColor(Color.parseColor("#00cc99"));
        sp.setStyle(Paint.Style.FILL);
        sp.setStrokeWidth(2);
    }

    public float scaleY(float toScale) {
        if(toScale == 0) {
            return getHeight() - 15;
        }
        if(toScale == getHeight()) {
            return 15;
        }
        if(toScale < 1) {
            return getHeight() - 15 - toScale;
        }
        else {
            return (float) getHeight() - (toScale * scaler);
        }
    }

    public float maxYPlot() {
        float maxy = 0;
        for(int i = 0; i < values.size(); i++) {
            if (values.get(i) > maxy) {
                maxy = values.get(i);
            }
        }
        if(maxy > 15) {
            for(int i = 0; i < 4; i++) {
                if(maxy % 5 ==0) {
                    return maxy;
                }
                maxy++;
            }
            return maxy;
        }
        else {
            return 15;
        }
    }

    public void addPoint(float f) {
        values.add(f);
        if(values.size() == 8) {
            values.remove(0);
            timeCount.add(timeCount.get(timeCount.size()-1) +0.5);
            timeCount.remove(0);
        }
       addMean();
    }

    public float getRecentValue() {
        return means.get(values.size()-1);
    }

    public float getRecentMean() {
        return means.get(means.size()-1);
    }

    public void addMean() {
        float total = 0;
            int valct = 0;
            for(int i = values.size()-1; i >= 0; i--) {
                total += values.get(i);
                valct++;
                if(valct == 5) {
                    break;
                }
            }
            means.add(total/valct);
            if(means.size() == 8) {
                means.remove(0);
            }
        addSTDev();
    }

    public void addSTDev() {
        float total = 0;
            float mean = means.get(means.size()-1);
            int valct = 0;
            for(int i = values.size()-1; i >= 0; i--) {
                total += ((values.get(i) - mean) * (values.get(i) - mean));
                valct++;
                if(valct == 5) {
                    break;
                }
            }
            stdevs.add((float) Math.sqrt(total/valct));
            if(stdevs.size() == 8) {
                stdevs.remove(0);
            }
    }

    public void clearData() {
        for(int i = 0; i < values.size(); i++) {
            values.remove(i);
            means.remove(i);
            stdevs.remove(i);
        }
    }
}

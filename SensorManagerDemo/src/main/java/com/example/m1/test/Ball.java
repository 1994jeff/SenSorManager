package com.example.m1.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by m1 on 18-3-21.
 */

public class Ball extends View implements Runnable{

    /********************************************/
    SensorManager sensorManager;
    SensorEventListener eventListener;
    Sensor sensor;
    /********************************************/

    private final float ball_radius = 40f;
    private final float round_radius = 100f;
    private final int ball_color = Color.BLUE;

    private float screenWidth,screenHight;
    private float x,y;
    boolean isGameRun;
    private Paint paint;

    public Ball(Context context) {
        super(context);
    }

    public Ball(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        isGameRun = true;
        new Thread(Ball.this).start();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        eventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                Ball.this.x -= x*10;
                Ball.this.y += y*10;

//                if(event.sensor.getMaximumRange()>x && x>2f){
//                    Ball.this.x -= x*10;
//                }else {
//                    Ball.this.x -= x*20;
//                }
//                if(event.sensor.getMaximumRange()>y && y>2f){
//                    Ball.this.y += y*10;
//                }else {
//                    Ball.this.y += y*20;
//                }
                Log.i("jeff","setMoveX,"+Ball.this.x);
                Log.i("jeff","setMoveY,"+Ball.this.y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(eventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public Ball(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Ball(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        screenHight = getHeight();
        screenWidth = getWidth();
        y = screenHight/2;
        x = screenWidth/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("jeff","onDraw,"+getWidth());
        paint.setAntiAlias(false);
        paint.setColor(ball_color);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(x,y,ball_radius,paint);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i("jeff","onFinishInflate,"+getWidth());
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.i("jeff","onWindowFocusChanged,"+getWidth());
    }

    @Override
    public void run() {
        while (isGameRun){
            try {
                postInvalidate();
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        sensorManager.unregisterListener(eventListener);
    }
}

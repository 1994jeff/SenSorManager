package com.example.m1.test;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView gSensor,dis,oSensor,ois,mSensor,mis,gySensor,gyis;
    SensorManager sensorManager;
    Button start;

    SensorEventListener eventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Log.i("jeff","onSensorChanged event="+event.values.toString());
            switch (event.sensor.getName()){
                case "Gravity":
                    gSensor.setText("x="+event.values[0]+",y="+event.values[1]+",z="+event.values[2]);
                    break;
                case "Orientation":
                    oSensor.setText("x="+event.values[0]+",y="+event.values[1]+",z="+event.values[2]);
                    break;
                case "BMI120 Accelerometer":
                    mSensor.setText("x="+event.values[0]+",y="+event.values[1]+",z="+event.values[2]);
                    break;
                case "BMI120 Gyroscope":
                    gySensor.setText("x="+event.values[0]+",y="+event.values[1]+",z="+event.values[2]);
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Sensor mySensor = sensor;
            Log.i("jeff","onAccuracyChanged sensor="+sensor.getName()+",accuracy="+accuracy);
            switch (mySensor.getName()){
                case "Gravity":
                    dis.setText("重力传感器:"+mySensor.getName()+",accuracy="+accuracy);
                    break;
                case "Orientation":
                    ois.setText("方向传感器o-sensor:"+mySensor.getName()+",accuracy="+accuracy);
                    break;
                case "BMI120 Accelerometer":
                    mis.setText("加速度传感器:"+mySensor.getName()+",accuracy="+accuracy);
                    break;
                case "BMI120 Gyroscope":
                    gyis.setText("陀螺仪传感器gyro-sensor:"+mySensor.getName()+",accuracy="+accuracy);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gSensor = (TextView) findViewById(R.id.g_sensor);
        dis = (TextView) findViewById(R.id.dis);
        oSensor = (TextView) findViewById(R.id.o_sensor);
        ois = (TextView) findViewById(R.id.ois);
        mSensor = (TextView) findViewById(R.id.m_sensor);
        mis = (TextView) findViewById(R.id.mis);
        gySensor = (TextView) findViewById(R.id.gy_sensor);
        gyis = (TextView) findViewById(R.id.gyis);
        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BallActivity.class));
            }
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor gSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Sensor mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor oSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        Sensor gySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(eventListener,gSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(eventListener,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(eventListener,oSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(eventListener,gySensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(eventListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}

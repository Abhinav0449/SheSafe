package com.example.womeniya;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;

import java.util.Random;

public class ShakeService extends Service implements SensorEventListener {
    private SensorManager nSensorManager;
    private Sensor nAccelerometer;
    private float a;
    private float ua;
    private float va;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        nSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        nAccelerometer = nSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        nSensorManager.registerListener(this, nAccelerometer, SensorManager.SENSOR_DELAY_UI, new Handler(Looper.myLooper()));
        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float a = event.values[0];
        float b = event.values[1];
        float c = event.values[2];
        va = ua;
        ua = (float) Math.sqrt((double) (a * a + b * b + c * c));
        float z = ua - va;
        a = a * 0.9f + z;

        if (a > 11) {
            Random rand = new Random();
            int no = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

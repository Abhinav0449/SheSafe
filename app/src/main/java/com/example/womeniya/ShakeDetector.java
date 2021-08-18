package com.example.womeniya;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector implements SensorEventListener {
    private static final float SHAKE_THRESHOLD = 2.7F;
    private static final int SHAKE_SLOP_TIME = 500;
    private static final int SHAKE_COUNT_RESET = 3000;


    private OnShakeListener nListener;
    private long nShakeTimestamp;
    private int nShakeCount;

    public void setOnShakeListener(OnShakeListener listener) {
        this.nListener = listener;
    }


    public interface OnShakeListener {
        public void onShake(int count);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (nListener != null) {
            float a = event.values[0];
            float b = event.values[1];
            float c = event.values[2];

            float grA = a / SensorManager.GRAVITY_EARTH;
            float grB = b / SensorManager.GRAVITY_EARTH;
            float grC = c / SensorManager.GRAVITY_EARTH;

            float gForce = (float) Math.sqrt(grA * grA + grB * grB + grC * grC);

            if (gForce > SHAKE_THRESHOLD) {
                final long now = System.currentTimeMillis();
                if (nShakeTimestamp + SHAKE_SLOP_TIME > now) {
                    return;
                }
                if (nShakeTimestamp + SHAKE_COUNT_RESET < now) {
                    nShakeCount = 0;
                }
                nShakeTimestamp = now;
                nShakeCount++;
                nListener.onShake(nShakeCount);
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}



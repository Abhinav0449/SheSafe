package com.example.womeniya;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartService=new Intent(getApplicationContext(),this.getClass());
        restartService.setPackage(getPackageName());
        startService(rootIntent);
    }
}
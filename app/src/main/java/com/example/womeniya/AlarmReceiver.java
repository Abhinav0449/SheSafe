package com.example.womeniya;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    private final String Action = "com.a.alarm";

    @Override
    public void onReceive(Context context, Intent intent) {
        String a = intent.getAction();
        if (Action.equals(a)) {
            MainActivity.sendSms();
        }
    }
}

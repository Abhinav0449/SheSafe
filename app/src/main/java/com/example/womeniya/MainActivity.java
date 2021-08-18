package com.example.womeniya;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    db database;
    static HashMap<String, String> hArray = null;
    Context context = MainActivity.this;

    private SensorManager nSensorManager;
    private Sensor nAccelerometer;
    private ShakeDetector nShakeDetector;


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Do you really want to exit?")
                .setPositiveButton("yes", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }).setNegativeButton("no", null).show();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void message(View view) {
        if (database.number() == 2) {
            String phoneNo1 = database.databaseToPhoneFirst();
            String phoneNo2 = database.databaseToPhoneSecond();
            double latitude, longitude;
            String message = "I am in danger.Please Help.Contact me ASAP.Call Police if unable to reach me.";
            LocationManager nlocmangaer;
            LocationListener nloclistener;
            nlocmangaer = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            nloclistener = new MyLocation(this);
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            nlocmangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, nloclistener);
            if (nlocmangaer.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                latitude = MyLocation.latitude;
                longitude = MyLocation.longitude;
                message = message + "\n My Location is -" + "https://maps.google.com/maps?daddr=" + latitude + "," + longitude;
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                if (latitude == 0.0) {
                    Toast.makeText(getApplicationContext(), "Gps Location not found", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "GPS is OFF....", Toast.LENGTH_LONG).show();
            }
            hArray.put(phoneNo1, message);
            hArray.put(phoneNo2, message);
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo1, null, message, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo2, null, message, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please add 2 numbers", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(context, "x" + hArray, Toast.LENGTH_SHORT).show();
        fireAlarm();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Add_Numbers.class);
                startActivity(i);

            }
        });
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Tips.class);
                startActivity(i);

            }
        });
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Laws.class);
                startActivity(i);
                overridePendingTransition(R.anim.animation_in, R.anim.animation_out);

            }
        });
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SafetyThreat.class);
                startActivity(i);
                overridePendingTransition(R.anim.animation_in, R.anim.animation_out);

            }
        });
        Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                startActivity(new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", "1091",
                        null)));

            }
        });
        hArray = new HashMap<String, String>();

        getSupportActionBar().hide();
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken permissionToken) {
            }
        }).check();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        database = new db(this, null, null, 1);
        Bundle numbers = getIntent().getExtras();
        nSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        nAccelerometer = nSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        nShakeDetector = new ShakeDetector();
        nShakeDetector.setOnShakeListener((count) -> {
            if (count == 3) {
                if (database.number() == 2) {
                    String phoneNo1 = database.databaseToPhoneFirst();
                    String phoneNo2 = database.databaseToPhoneSecond();
                    Double latitude = 0.0, longitude;
                    String message = "I am in danger.Please Help.Contact me ASAP.Call Police if unable to reach me.";
                    LocationManager nlocmanager = null;
                    LocationListener nloclistener;
                    nlocmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    nloclistener = new MyLocation(MainActivity.this);
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    nlocmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, nloclistener);
                    if (nlocmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        latitude = MyLocation.latitude;
                        longitude = MyLocation.longitude;
                        message = message + "\n My Location is - " + "https://maps.google.com/maps?daddr=" + latitude + ","
                                + longitude;
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        if (latitude == 0.0) {
                            Toast.makeText(getApplicationContext(), "gps has not found your location....", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "GPS is currently off...", Toast.LENGTH_LONG).show();
                    }

                    hArray.put(phoneNo1, message);
                    hArray.put(phoneNo2, message);
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo1, null, message, null, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo2, null, message, null, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please add 2 numbers", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(context, "x" + hArray, Toast.LENGTH_SHORT).show();
                fireAlarm();

            }

        });


        if (numbers == null) {
            return;
        }
        String number1 = numbers.getString("Number1");
        String number2 = numbers.getString("Number2");

        phone_number n1 = new phone_number(number1);
        phone_number n2 = new phone_number(number2);
        database.addnumber1(n1);
        database.addnumber2(n2);
    }

    @Override
    public void onResume() {
        super.onResume();
        nSensorManager.registerListener(nShakeDetector, nAccelerometer, SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    public void onPause() {
        super.onPause();
        nSensorManager.unregisterListener(nShakeDetector);
    }


    public static void sendSms() {

        Set<?> set = hArray.entrySet();
        // Get an iterator
        Iterator<?> i = set.iterator();
        // Display elements
        while (i.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry me = (Map.Entry) i.next();
            System.out.print(me.getKey() + ": ");
            Log.d("msg123", me.getKey() + ": ");
            System.out.println(me.getValue());
            Log.d("msg456", me.getValue().toString());

            try {
                android.telephony.gsm.SmsManager smsManager = android.telephony.gsm.SmsManager.getDefault();
                smsManager.sendTextMessage(me.getKey().toString(), null, me
                        .getValue().toString(), null, null);
                System.out.println("message sent");
            } catch (Exception e) {
                System.out.println("sending failed!");
                e.printStackTrace();
            }
        }
    }

    public void fireAlarm() {
        /**
         * call broadcost reciver for send sms
         */
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("com.a.alarmcheck");
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(MainActivity.this, 0, intent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        AlarmManager alarm = (AlarmManager) MainActivity.this
                .getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pendingIntent);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                100000, pendingIntent);
    }

}

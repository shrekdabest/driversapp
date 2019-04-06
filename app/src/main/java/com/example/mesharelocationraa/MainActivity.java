package com.example.mesharelocationraa;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

public class MainActivity extends AppCompatActivity {
    LocationManager lm;
    AlphaAnimation animesh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
         lm= (LocationManager) getSystemService(LOCATION_SERVICE);

animesh=new AlphaAnimation(1.0f,0.5f);
    }



    public void start(View view) {
        view.startAnimation(animesh);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            createalertbuilder();
        }
        else if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            else
                {
startService(new Intent(this,TrackerService.class));


            }





        }
    }

    public void stop(View view) {
        view.startAnimation(animesh);
        stopService(new Intent(MainActivity.this,TrackerService.class));
    }
    public void createalertbuilder()
    {
        AlertDialog.Builder bob=new AlertDialog.Builder(this,R.style.MyDialogTheme);
        bob.setTitle("Please Enable Gps");
        bob.setMessage("We will redirect you to setting where you can enable location service");
        bob.setCancelable(true);
        bob.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        AlertDialog alert=bob.create();
        alert.show();

    }
}

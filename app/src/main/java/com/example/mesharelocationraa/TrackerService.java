package com.example.mesharelocationraa;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrackerService extends Service {
double lat,lon;
LocationCallback callback;
    FusedLocationProviderClient client;
    DatabaseReference mref;


    @Override
    public void onCreate() {
        Toast.makeText(this,"Started",Toast.LENGTH_SHORT).show();
        getlocation();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void getlocation()
    { LocationRequest request = new LocationRequest();
        request.setInterval(2000);
        request.setFastestInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        client = LocationServices.getFusedLocationProviderClient(getApplicationContext());
callback=new LocationCallback() {
    @Override
    public void onLocationResult(LocationResult locationResult) {

        Location location = locationResult.getLastLocation();
        lat=location.getLatitude();
        lon=location.getLongitude();

        sendtofirebase();
    }
};
        int permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(request, callback, null);
        }

    }
   public void sendtofirebase()
    {mref=FirebaseDatabase.getInstance().getReferenceFromUrl("https://applico-9faa6.firebaseio.com/");
        Toast.makeText(this,"im not running now",Toast.LENGTH_SHORT).show();
        DatabaseReference latti,longi;
latti=mref.child("9601608920(Lattitude)");
        latti.setValue(String.valueOf(lat));
        longi=mref.child("9601608920(Longitude)");
        longi.setValue(String.valueOf(lon));
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this,"Stopped",Toast.LENGTH_SHORT).show();
       // client.removeLocationUpdates(callback);





        super.onDestroy();
    }
}

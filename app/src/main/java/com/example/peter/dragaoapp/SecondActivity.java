package com.example.peter.dragaoapp;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends Activity  {


    // GPSTracker class
    GPSTracker gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void smsClick(View view) {
        gps = new GPSTracker(SecondActivity.this);
        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            sendSMS(latitude,longitude);
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

    }

    protected void sendSMS(double lati, double longi) {

        Log.i("Send SMS", "");
        String phoneNumber = "9999999999";
        String smsBody = "This is an SMS!, your latitiude and longitude are:"+lati+longi;
        //from http://codetheory.in/android-sms/
        // Add the phone number in the data
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        // Create intent with the action and data
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
        // smsIntent.setData(uri); // We just set the data in the constructor above
                // Set the message
        smsIntent.putExtra("sms_body", smsBody);

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SecondActivity.this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
}
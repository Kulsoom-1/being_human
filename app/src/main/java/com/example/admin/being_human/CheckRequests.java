package com.example.admin.being_human;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class CheckRequests extends AsyncTask<Void,Void,String>
{
    String email;
    Requests request=null;
    LocationService locationService;
    public CheckRequests(String email, LocationService locationService)
    {
        this.locationService=locationService;
        this.email=email;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String parameters="?email="+email;
        String pending_order_url="http://zmdelivery.com/BeingHuman/checkrequest.php";
        try {
            URL url=new URL(pending_order_url+parameters);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.getResponseCode();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String string;
                while ((string = bufferedReader.readLine()) != null) {
                    stringBuilder.append(string).append("\n");
                }
                bufferedReader.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } finally {
                httpURLConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(String s) {
        if(!s.equals("nothing"))
        {
            request=new JSONParser().parseCRequest(s);
            Intent intent=new Intent(locationService.getApplicationContext(),BaseActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(locationService.getApplicationContext(),0,intent,0);
            NotificationManager notif=(NotificationManager)locationService.getSystemService(Context.NOTIFICATION_SERVICE);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification notify=new Notification.Builder(locationService.getApplicationContext())
                    .setContentTitle("Blood Request")
                    .setContentText("For details go to Requests tab")
                    .setSound(alarmSound)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(request.getUserName() + " need blood").setSmallIcon(R.drawable.ic_launcher)
                    .build();
            notify.flags = Notification.FLAG_AUTO_CANCEL;
            notif.notify(0, notify);
        }
    }
}
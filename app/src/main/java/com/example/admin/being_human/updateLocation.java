package com.example.admin.being_human;

import android.app.FragmentManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class updateLocation extends AsyncTask<Void,Void,String>
{
    String location;
    String email;
    public updateLocation(String location,String email)
    {
        this.email=email;
        this.location=location;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String parameters="?email="+email+
                "&location="+location;
        String bh_url="http://zmdelivery.com/BeingHuman/update-location.php";
        try {
            URL url=new URL(bh_url+parameters);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.getResponseCode();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String string=bufferedReader.readLine();
                bufferedReader.close();
                httpURLConnection.disconnect();
                return string;
            } finally {
                httpURLConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        System.out.println(s);

    }
}
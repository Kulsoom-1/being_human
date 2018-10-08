package com.example.admin.being_human;

import android.os.AsyncTask;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RequestDoner extends AsyncTask<Void,Void,String>
{
    private String d_email;
    private String u_email;

    public RequestDoner(String d_email, String u_email) {
        this.d_email = d_email;
        this.u_email = u_email;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String parameters="?u_email="+u_email+
                "&d_email="+d_email;
        String pending_order_url="http://zmdelivery.com/BeingHuman/request.php";
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

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        System.out.println("Sherazi: Request Send-Status="+s);
    }
}
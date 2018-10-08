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


public class AcceptRequest extends AsyncTask<Void,Void,String>
{
    private String id;
    public AcceptRequest(String id)
    {
        this.id=id;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String parameters="?id="+id;
        String pending_order_url="http://zmdelivery.com/BeingHuman/accept_request.php";
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
        System.out.println("ACCEPT::"+s);
    }
}
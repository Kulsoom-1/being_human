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

public class GetDonersByGender extends AsyncTask<Void,Void,String>
{
    private String email;
    private List<User> allUsers=null;
    private BaseActivity baseActivity;
    private String bg;
    private String gender;
    public GetDonersByGender(BaseActivity baseActivity, String email, String bg, String gender)
    {
        this.baseActivity=baseActivity;
        this.bg=bg;
        this.gender=gender;
        this.email=email;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String parameters="?bg="+bg+
                "&gender="+gender+
                "&email="+email;
        String pending_order_url="http://zmdelivery.com/BeingHuman/getdonersbygender.php";
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
        if(s.equals("nothing"))
        {
            ((RelativeLayout)baseActivity.findViewById(R.id.container)).removeAllViews();
            android.support.v4.app.FragmentManager fManager=baseActivity.getSupportFragmentManager();
            //fManager.beginTransaction().replace(R.id.container, new Home(mylocation,baseActivity,null)).commit();
        }
        else
        {
            allUsers=new JSONParser().parseDoners(s);
            ((RelativeLayout)baseActivity.findViewById(R.id.container)).removeAllViews();
            android.support.v4.app.FragmentManager fManager=baseActivity.getSupportFragmentManager();
            //fManager.beginTransaction().replace(R.id.container, new Home(mylocation,baseActivity,allUsers)).commit();
        }
    }
}
package com.example.admin.being_human;

import android.app.VoiceInteractor;
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
public class CheckRequest extends AsyncTask<Void,Void,String>
{
    private BaseActivity baseActivity;
    private String email;
    private List<Requests> requestsList;
    public CheckRequest(BaseActivity baseActivity, String email)
    {
        this.baseActivity=baseActivity;
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

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        if(!s.equals("nothing") && !s.equals(""))
        {
            requestsList=new JSONParser().parseRequest(s);
            ((RelativeLayout)baseActivity.findViewById(R.id.container)).removeAllViews();
            android.app.FragmentManager fragmentM=baseActivity.getFragmentManager();
            fragmentM.beginTransaction().replace(R.id.container,new RequestFragment(baseActivity,requestsList)).commit();
       }
       else
        {
            ((RelativeLayout)baseActivity.findViewById(R.id.container)).removeAllViews();
            android.app.FragmentManager fragmentM=baseActivity.getFragmentManager();
            fragmentM.beginTransaction().replace(R.id.container,new RequestFragment(baseActivity,null)).commit();
        }

    }
}
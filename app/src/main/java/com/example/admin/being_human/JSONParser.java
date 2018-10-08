package com.example.admin.being_human;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kulsoom on 12-Jan-18.
 */

public class JSONParser {

    public List<User> parseDoners(String JSON)
    {
        List<User> users = new LinkedList<User>();
        try {
            JSONArray jsonArray=new JSONArray(JSON);
            for (int a=0;a<jsonArray.length();a++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(a);
                users.add(new User(jsonObject.getString("email"),
                        jsonObject.getString("pass"),
                        jsonObject.getString("name"),
                        jsonObject.getString("location"),
                        jsonObject.getString("address"),
                        jsonObject.getString("DOB"),
                        jsonObject.getString("BG"),
                        jsonObject.getString("last_donation"),
                        jsonObject.getString("donar_status"),
                        jsonObject.getString("gender"),
                        jsonObject.getString("contact")));
            }
            return users;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Requests> parseRequest(String JSON)
    {
        List<Requests> requests = new LinkedList<Requests>();
        try {
            JSONArray jsonArray=new JSONArray(JSON);
            for (int a=0;a<jsonArray.length();a++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(a);
                requests.add(new Requests(jsonObject.getString("name"),
                        jsonObject.getString("gender"),
                        jsonObject.getString("contact"),
                        jsonObject.getString("id"),
                        jsonObject.getString("afd")));
            }
            return requests;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Requests parseCRequest(String JSON)
    {
        try {
            JSONArray jsonArray=new JSONArray(JSON);
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            return new Requests(jsonObject.getString("name"),
                    jsonObject.getString("gender"),
                    jsonObject.getString("contact"),
                    jsonObject.getString("id"),
                    jsonObject.getString("afd"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User parseLoginUser(String JSON)
    {
        try {
            JSONArray jsonArray=new JSONArray(JSON);
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            return new User(jsonObject.getString("email"),
                    jsonObject.getString("pass"),
                    jsonObject.getString("name"),
                    jsonObject.getString("location"),
                    jsonObject.getString("address"),
                    jsonObject.getString("DOB"),
                    jsonObject.getString("BG"),
                    jsonObject.getString("last_donation"),
                    jsonObject.getString("donar_status"),
                    jsonObject.getString("gender"),
                    jsonObject.getString("contact"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}

package com.example.admin.being_human;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class UserSessionManager {
    SharedPreferences pref;
    Editor editor;
    Context context;

    int PRIVATE_MODE =0;
    private static final String PREFER_NAME="KalsoomWork";
    private static final String IS_USER_LOGIN="IsUserLogIn";

    public static final String KEY_NAME="name";
    public static final String KEY_EMAIL="email";
    public static final String KEY_STATUS="donar_status";

    public UserSessionManager(Context context)
    {
        this.context=context;
        pref=context.getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
        editor=pref.edit();
    }

    public void createUserLoginSession(String name,String email,String donar_status)
    {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_STATUS,donar_status);
        editor.commit();
    }
    public boolean isUserLoggedIn()
    {
        return pref.getBoolean(IS_USER_LOGIN,false);
    }

    public boolean checkLogin()
    {
        if(!this.isUserLoggedIn())
        {
            Intent intent=new Intent(context,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    public HashMap<String,String> getUserDetails()
    {
        HashMap<String,String> user=new HashMap<>();
        user.put(KEY_NAME,pref.getString(KEY_NAME,null));
        user.put(KEY_EMAIL,pref.getString(KEY_EMAIL,null));
        user.put(KEY_STATUS,pref.getString(KEY_STATUS,null));
        return user;
    }

    public void LogoutUser()
    {
        editor.clear();
        editor.commit();

        Intent intent=new Intent(context,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}

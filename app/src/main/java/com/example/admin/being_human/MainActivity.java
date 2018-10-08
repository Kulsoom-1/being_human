package com.example.admin.being_human;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    UserSessionManager session; //usersession type variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this); // binding butterknife
        session=new UserSessionManager(getApplicationContext());
        Intent in = new Intent(getBaseContext(), LocationService.class);
        startService(in);
        if(session.isUserLoggedIn())
        {
            gotoBaseActivity();
        }
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#900000")));
        getSupportActionBar().setTitle("BeingHuman");
    }

    @OnClick(R.id.btn_login)
    public void getLogin(View view){
        if(!isLocationEnabled(this))
        {
            return;
           // Toast.makeText(this, "Please enable Location", Toast.LENGTH_LONG).show();
        }
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_sign_up)
    public void getSignup(View view){
        if(!isLocationEnabled(this))
        {
            return;
            //Toast.makeText(this, "Please enable Location", Toast.LENGTH_LONG).show();
        }
//        RelativeLayout mainLayout = (RelativeLayout)
//                findViewById(R.id.activity_main_layout);
//
//        // inflate the layout of the popup window
//        LayoutInflater inflater = (LayoutInflater)
//                getSystemService(LAYOUT_INFLATER_SERVICE);
//        View popupView = inflater.inflate(R.layout.popup_layout, null);
//        Button donarbtn = (Button)
//                popupView.findViewById(R.id.donarbutton);
//        Button needybtn = (Button)
//                popupView.findViewById(R.id.needybutton);
//
//        // create the popup window
//        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        boolean focusable = true; // lets taps outside the popup also dismiss it
//        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//
//        // show the popup window
//        popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);
//
//        donarbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, SignupActivity.class));
//            }
//        });
//        needybtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, NeedyActivity.class));
//            }
//        });
//        // dismiss the popup window when touched
//        popupView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                popupWindow.dismiss();
//                return true;
//            }
//        });


        Intent in=new Intent(this, SignupActivity.class);
        startActivity(in);
    }

    private void gotoBaseActivity()
    {
        Intent intent=new Intent(getBaseContext(),BaseActivity.class);
        startActivity(intent);
        finish();
    }
    private boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}

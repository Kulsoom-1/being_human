package com.example.admin.being_human;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.btn_login)
    public void getLogin(View view){
        System.out.println("i am in");
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_sign_up)
    public void getSignup(View view){
        Intent intent=new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    private void startLocation() {
//        try {
//            if (uiComponents.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
//                System.out.println("Permission available");
//                Intent in = new Intent(this, LocationService.class);
//                startService(in);
//            } else {
//                uiComponents.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, 111);
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
    }

}

package com.example.admin.being_human;

import android.*;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

import static android.content.ContentValues.TAG;

public class BaseActivity extends AppCompatActivity
        implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener{


    @BindView(R.id.parentTextField)
    Spinner spinnerBloodgrp;
    private EditText txtLocation;
    RelativeLayout textFieldParent;
    private GoogleMap mMap;
    StorageManager storageManager;
    private ArrayAdapter<String> mAdapter;
    private String bg="Aplus";
    double lat;
    double lng;
    HashMap<String,String> user;
    private String userEmail;
    private String userName;
    private String donarStatus;
    private UserSessionManager session;

    String[] BloodGroup = {"A+", "B+", "AB+",
            "AB-", "A-", "B-", "O+", "O-"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        storageManager=new StorageManager(this);
        session=new UserSessionManager(getApplicationContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menuNav=navigationView.getMenu();
        if(session.checkLogin())
        {
            finish();
        }
        user=session.getUserDetails();
        userEmail=user.get(UserSessionManager.KEY_EMAIL);
        userName=user.get(UserSessionManager.KEY_NAME);
        donarStatus=user.get(UserSessionManager.KEY_STATUS);
//        if(donarStatus.equals("0"))
//        {
//            navigationView = (NavigationView) findViewById(R.id.nav_view);
//            Menu nav_Menu = navigationView.getMenu();
//            nav_Menu.findItem(R.id.nav_requests).setVisible(false);
//        }
        txtLocation=(EditText) findViewById(R.id.txt_location);
        spinnerBloodgrp=(Spinner) findViewById(R.id.txt_bloodgroup);
        mAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,BloodGroup );
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodgrp.setAdapter(mAdapter);
        spinnerBloodgrp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();
                if(selected.equals("A+"))
                {
                    bg="Aplus";
                }
                else if(selected.equals("B+")){
                    bg="Bplus";
                }
                else if(selected.equals("A-")){
                    bg="Anagetive";
                }
                else if(selected.equals("B-")){
                    bg="Bnagetive";
                }
                else if(selected.equals("AB+")){
                    bg="ABplus";
                }
                else if(selected.equals("AB-")){
                    bg="ABnagetive";
                }
                else if(selected.equals("O+")){
                    bg="Oplus";
                }
                else{
                    bg="Onagetive";
                }
                System.out.println("Spinner Text : " + selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        storageManager=new StorageManager(getBaseContext());
//        lat= Double.parseDouble(storageManager.getData(Constant.LOCATION_LATITUDE));
//        lng=Double.parseDouble(storageManager.getData(Constant.LOCATION_Longitude));
//        double lat=0.0;
//        double lng=0.0;
        Button button=(Button) findViewById(R.id.btn_on_sign_up);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtLocation.getText().toString().equals(""))
                {
                    try{
                        double lat = Double.parseDouble(storageManager.getData(Constant.LOCATION_LATITUDE));
                        double lng = Double.parseDouble(storageManager.getData(Constant.LOCATION_LONGITUDE));
                        new GetDoners(BaseActivity.this, userEmail, bg, txtLocation.getText().toString(), new LatLng(lat, lng)).execute();
                    }catch(Exception ex)
                    {
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                        System.out.println("Sherazi: "+ex.getMessage());
                    }
                }
                else
                {
                    txtLocation.setError("Required");
                    txtLocation.requestFocus();
                }
            }
        });
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#900000")));
        getSupportActionBar().setTitle(user.get(UserSessionManager.KEY_NAME));
    }



    public void getRequestBtn(View view){
        textFieldParent.setVisibility(View.GONE);
    }


    @OnClick(R.id.btn_Find_Donar)
    public void findDonar(View view){
       FragmentManager fragmentManager=this.getSupportFragmentManager();
        MapFragment fragment = new MapFragment();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }


@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            /*StorageManager storageManager= new StorageManager(this);
            storageManager.saveData(Constant.LOGSTATUS,"false");
            Intent in= new Intent(this, MainActivity.class);
            startActivity(in);*/
            session.LogoutUser();
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (!isOnline()) {
            Toast.makeText(this, "Please Connect to internet", Toast.LENGTH_LONG).show();
            finish();
            return false;
        }
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction  ();
        int id = item.getItemId();
        ((RelativeLayout)findViewById(R.id.container)).removeAllViews();

        if (id == R.id.nav_home) {
//            fragmentTransaction.replace(R.id.container, new Home(this));
//            fragmentTransaction.commit();
            android.support.v4.app.FragmentManager fragmentManagerm = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransactionm = fragmentManagerm.beginTransaction();
            Home fragment = new Home(this);
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_account) {
            System.out.println("on ServicesFragment select");
            SettingFragment fragment = new SettingFragment(userEmail,this,userName);
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_requests) {
            new CheckRequest(this,userEmail).execute();
        } else if (id == R.id.nav_finddonar) {
            System.out.println("on ServicesFragment select");
            FindDonarFragment fragment = new FindDonarFragment(this);
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_help) {
            System.out.println("on ServicesFragment select");
            AboutFragment fragment = new AboutFragment();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try{
            double lat= Double.parseDouble(storageManager.getData(Constant.LOCATION_LATITUDE));
            System.out.println("oyeeee" + storageManager.getData(Constant.LOCATION_LATITUDE));
            double lng=Double.parseDouble(storageManager.getData(Constant.LOCATION_LONGITUDE));
            System.out.println("location::::" + lat + ""+ lng);
            LatLng marker=new LatLng(lat,lng);
            mMap.addMarker(new MarkerOptions().position(marker).title("you"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    marker, 15);
            mMap.animateCamera(location);
        }catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }

    public void getLocationFromAddress(String strAddress){
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            double lat=(double) (location.getLatitude() * 1E6);
            double lng=(double) (location.getLongitude() * 1E6);
            ActiveLocation ac= new ActiveLocation();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent in = new Intent(getBaseContext(), LocationService.class);
        stopService(in);
    }

    private boolean isOnline()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        else {
            return false;
        }
    }
}


package com.example.admin.being_human;


import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


@SuppressLint("ValidFragment")
public class Home extends Fragment implements OnMapReadyCallback , GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private GoogleMap uMap;
    private View v;
    private LatLng mylocation;
    private BaseActivity baseActivity;
    private List<User> allUsers=null;
    private String bg=null;
    private Spinner spinnerBloodgrp;
    private EditText txtLocation;
    String location;
    String selected;
    UserSessionManager session;
    String user;
    StorageManager storageManager;
    HashMap<Marker,User> hashMap;
    private ArrayAdapter<String> mAdapter;
    String[] BloodGroup = {"A+", "B+", "AB+",
            "AB-", "A-", "B-", "O+", "O-"};
    @SuppressLint("ValidFragment")
    public Home(LatLng mylocation,BaseActivity baseActivity,List<User> allUsers)
    {
        hashMap=new HashMap<Marker, User>();
        this.allUsers=allUsers;
        this.baseActivity=baseActivity;
        this.mylocation=mylocation;

    }
//    public Home(BaseActivity baseActivity)
//    {
//        storageManager=new StorageManager(baseActivity);
//        hashMap=new HashMap<Marker, User>();
//        allUsers=null;
//        mylocation=new LatLng(Double.valueOf(storageManager.getData(Constant.LOCATION_LATITUDE)),Double.valueOf(storageManager.getData(Constant.LOCATION_LONGITUDE)));
//        this.baseActivity=baseActivity;
//    }
    public Home(BaseActivity baseActivity)
    {
        storageManager=new StorageManager(baseActivity);
        hashMap=new HashMap<Marker, User>();
        allUsers=null;
        mylocation = new LatLng(Double.valueOf(storageManager.getData(Constant.LOCATION_LATITUDE)), Double.valueOf(storageManager.getData(Constant.LOCATION_LONGITUDE)));
         this.baseActivity=baseActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v= inflater.inflate(R.layout.content_base, container, false);
        txtLocation=(EditText) v.findViewById(R.id.txt_location);
        spinnerBloodgrp=(Spinner) v.findViewById(R.id.txt_bloodgroup);
        location=txtLocation.getText().toString();
        mAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,BloodGroup );
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodgrp.setAdapter(mAdapter);
        spinnerBloodgrp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               selected = adapterView.getItemAtPosition(i).toString();
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
        Button button=v.findViewById(R.id.btn_on_sign_up);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtLocation.getText().toString().equals(""))
                {
                    new GetDoners(baseActivity,"kalsoom@maavan.com",bg,txtLocation.getText().toString(),mylocation).execute();
                }
                else
                {
                    txtLocation.setError("Required");
                    txtLocation.requestFocus();
                }
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        uMap =googleMap;
        if(allUsers!=null)
        {
            for (int a=0;a<allUsers.size();a++)
            {
                if(!allUsers.get(a).getLocation().equals("") || allUsers.get(a).getLocation()!=null)
                {
                    String [] location=allUsers.get(a).getLocation().split(",");
                    LatLng source=new LatLng(Double.parseDouble(location[0]),Double.parseDouble(location[1]));
                    Marker marker=uMap.addMarker(new MarkerOptions().position(source).title(allUsers.get(a).getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                    uMap.setOnInfoWindowClickListener(this);
                    hashMap.put(marker,allUsers.get(a));
                }
            }
        }
        else
        {
          //  Toast.makeText(getContext(), "No Donner available", Toast.LENGTH_LONG).show();
        }
        mMap.addMarker(new MarkerOptions().position(mylocation).title("You"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 9));

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        System.out.println("on Marker click");
        if (!marker.getTitle().equals("You")) {
            android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DonarProfileFragment fragment = new DonarProfileFragment(hashMap.get(marker),baseActivity);
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();
        }else
        {
            Toast.makeText(getContext(),"Its You",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(TAG, "In frag's on save instance state ");
        ArrayList<String> list= new ArrayList<>();
        list.add(location);
        list.add(selected);
        outState.putStringArrayList("array" , list);
    }
}

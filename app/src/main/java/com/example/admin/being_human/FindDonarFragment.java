package com.example.admin.being_human;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FindDonarFragment extends Fragment{
    List<User> mlist;
    Button btn;
    BaseActivity baseActivity;
    private ArrayAdapter<String> mAdapter;
    String[] BloodGroup = {"A+", "B+", "AB+", "AB-", "A-", "B-", "O+", "O-"};
    String[] Gender = {"Male", "Female", "All"};
    Spinner spinnerBloodgrp;
    Spinner spinnerGender;
    String bloodgroup;
    String gender;
    private RecyclerView rvDonar;
    private Handler mHandler;
    private UserSessionManager session;
    private FindDonarRecyclerAdapter donarAdapter;
    private String userEmail;
    private GetDonersList getDonersList=null;
    View view;
    public FindDonarFragment(BaseActivity baseActivity) {
        // Required empty public constructor
        this.baseActivity=baseActivity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_find_donar, container, false);
        btn= (Button) view.findViewById(R.id.btn_Find_Donar) ;
        rvDonar=(RecyclerView) view.findViewById(R.id.rvDonar);
        spinnerBloodgrp=(Spinner) view.findViewById(R.id.spnBloodgroup);
        spinnerGender=(Spinner) view.findViewById(R.id.spnGender) ;
        mAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,BloodGroup );
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodgrp.setAdapter(mAdapter);
        session=new UserSessionManager(baseActivity);
        HashMap<String,String> user=session.getUserDetails();
        userEmail=user.get(UserSessionManager.KEY_EMAIL);
        spinnerBloodgrp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();
                if(selected.equals("A+"))
                {
                    bloodgroup="Aplus";
                }
                else if(selected.equals("B+")){
                    bloodgroup="Bplus";
                }
                else if(selected.equals("A-")){
                    bloodgroup="Anagetive";
                }
                else if(selected.equals("B-")){
                    bloodgroup="Bnagetive";
                }
                else if(selected.equals("AB+")){
                    bloodgroup="ABplus";
                }
                else if(selected.equals("AB-")){
                    bloodgroup="ABnagetive";
                }
                else if(selected.equals("O+")){
                    bloodgroup="Oplus";
                }
                else{
                    bloodgroup="Onagetive";
                }
                System.out.println("Spinner Text : " + bloodgroup);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,Gender );
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(mAdapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = adapterView.getItemAtPosition(i).toString();
                System.out.println("Spinner Text : " + gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getDonersList==null)
                {
                    getDonersList=new GetDonersList();
                    getDonersList.execute();
                }
            }
        });

        return view;
    }
    private class GetDonersList extends AsyncTask<Void,Void,String>
    {
        public GetDonersList()
        {

        }

        @Override
        protected void onPreExecute() {
            ((RelativeLayout)view.findViewById(R.id.pBView)).setVisibility(View.VISIBLE);
            ((RelativeLayout)view.findViewById(R.id.donerView)).setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String parameters="?bg="+bloodgroup+
                    "&gender="+gender+
                    "&email="+userEmail;
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
            System.out.println("Ali Sherazi: "+s);
            ((RelativeLayout)view.findViewById(R.id.pBView)).setVisibility(View.INVISIBLE);
            ((RelativeLayout)view.findViewById(R.id.donerView)).setVisibility(View.VISIBLE);
            mlist=new JSONParser().parseDoners(s);
            donarAdapter= new FindDonarRecyclerAdapter(view.getContext(), mlist, getFragmentManager(),baseActivity);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvDonar);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                    DividerItemDecoration.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(donarAdapter);
            getDonersList=null;
        }

        @Override
        protected void onCancelled() {
            getDonersList=null;
        }
    }
}

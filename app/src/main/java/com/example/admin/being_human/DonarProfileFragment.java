package com.example.admin.being_human;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class DonarProfileFragment extends Fragment {

    BaseActivity baseActivity;
    User user;
    View view;
    private UserSessionManager session;
    String userEmail;

//    public  DonarProfileFragment (BaseActivity baseActivity) {
//        this.baseActivity=baseActivity;
//    }
    @SuppressLint("ValidFragment")
    public DonarProfileFragment(User user,BaseActivity baseActivity) {
        this.baseActivity=baseActivity;
        session=new UserSessionManager(this.baseActivity);
        HashMap<String,String> rUser=session.getUserDetails();
        userEmail=rUser.get(UserSessionManager.KEY_EMAIL);
        this.user=user;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_donar_profile, container, false);
        ((TextView)view.findViewById(R.id.tvDonarName)).setText(user.getName());
        ((TextView)view.findViewById(R.id.bloodGroup)).setText(user.getBG());
        ((TextView)view.findViewById(R.id.phoneNo)).setText(user.getContact());
        ((TextView)view.findViewById(R.id.cityName)).setText(user.getAddress());
        ((TextView)view.findViewById(R.id.genderDoner)).setText(user.getGender());
        ((Button)view.findViewById(R.id.btn_on_profile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RequestDoner(user.getEmail(),userEmail).execute();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + user.getContact()));
                startActivity(intent);
            }
        });
        return view;
}

}

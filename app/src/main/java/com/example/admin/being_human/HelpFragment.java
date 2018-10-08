package com.example.admin.being_human;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {

     Button btnAbout;
    private FragmentManager mFragmentManager;

    public HelpFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_help, container, false);

        return  view;
    }
//
//    @OnClick(R.id.btn_about)
    public void getAbout(View view) {
        FragmentManager fragmentManager = this.mFragmentManager;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AboutFragment fragment = new AboutFragment();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}

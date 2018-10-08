package com.example.admin.being_human;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.VoiceInteractor;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */

@SuppressLint("ValidFragment")
public class RequestFragment extends Fragment {

    List<Requests> requestsList=new LinkedList<Requests>();
    BaseActivity baseActivity;
    View view;
    public RequestFragment(BaseActivity baseActivity,List<Requests> requestsList)
    {
        this.baseActivity=baseActivity;
        this.requestsList=requestsList;
    }
    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (requestsList==null)
        {
            view=inflater.inflate(R.layout.fragment_nothing, container, false);
            return view;

        }
       view=inflater.inflate(R.layout.fragment_request, container, false);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        RequestAdapter customAdapter=new RequestAdapter(getActivity(),0,requestsList,baseActivity);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(customAdapter);
        return view;
    }

    public void sendNotification(View view) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.appicon)
                        .setContentTitle("You have a request!! please respond.")
                        .setContentText("Being Human");

        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(001, mBuilder.build());
    }

@RequiresApi(api = Build.VERSION_CODES.O)
public Boolean  getAvailability() {
    String date= LocalDate.now().toString();
    LocalDate aDate = LocalDate.parse(date);
    return aDate.isBefore( LocalDate.now().minusMonths(6));
}


}

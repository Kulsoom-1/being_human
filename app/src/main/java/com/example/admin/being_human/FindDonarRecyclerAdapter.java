package com.example.admin.being_human;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by kulsoom on 18-Jan-18.
 */

public class FindDonarRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<User> donars;
    TextView donarName;
   TextView donarBlood;
   ImageView getDonar;
    private Context mContext;
    private FragmentManager mFragmentManager;
    BaseActivity baseActivity;

    public FindDonarRecyclerAdapter(Context context, List list, FragmentManager fragmentManager,BaseActivity baseActivity){
        this.mContext = context;
        this.donars = list;
        this.baseActivity=baseActivity;
        this.mFragmentManager = fragmentManager;
    }

/*

    public void setDonar(List<User> donars) {
        this.donars = donars;
        notifyDataSetChanged();
    }
*/


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_find_donar, parent, false);
        donarName= (TextView)itemView.findViewById(R.id.tvDonarName);
        donarBlood= (TextView)itemView.findViewById(R.id.tvDonarBlood);
        getDonar=(ImageView) itemView.findViewById(R.id.ivDonarProfile);

        return new BaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final User serviceObject = donars.get(position);
        donarName.setText(donars.get(position).getName());
        donarBlood.setText(donars.get(position).getBG());
        getDonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDonarProfile(serviceObject);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donars == null ? 0 : donars.size();
    }
    void startDonarProfile(User user){
        FragmentManager fragmentManager = this.mFragmentManager;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DonarProfileFragment fragment = new DonarProfileFragment(user,baseActivity);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

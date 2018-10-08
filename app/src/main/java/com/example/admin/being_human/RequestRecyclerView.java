package com.example.admin.being_human;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kulsoom on 07-Jan-18.
 */

public class RequestRecyclerView extends RecyclerView.Adapter<RequestRecyclerView.MyViewHolder> implements  View.OnClickListener {

    TextView name;
    TextView gender;
    TextView number;
    Button btnAccept;
    RelativeLayout parentLayout;
    private Context mContext;
    private List<Requests> mList;
    private FragmentManager mFragmentManager;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvRequesterName);
            genre = (TextView) view.findViewById(R.id.tvRequesterGender);
            year = (TextView) view.findViewById(R.id.tvRequesterNumber);
            btnAccept=(Button) view.findViewById(R.id.acceptReq);
        }
    }

    public RequestRecyclerView(Context context, List list, FragmentManager fragmentManager){
        this.mContext = context;
        this.mList = list;
        this.mFragmentManager = fragmentManager;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_request, parent, false);

        return new MyViewHolder(itemView);

}

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Requests offerObject = mList.get(position);
        //System.out.println(offerObject.getText());
        name.setText(offerObject.getUserName());
         gender.setText(offerObject.getGender());
        number.setText(offerObject.getPhoneNumber());
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });



//        txtOfferTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startServiceDetailFragment(offerObject);
//            }
//        });
//        layoutGradient.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startServiceDetailFragment(offerObject);
//            }
//        });
//        ivOfferImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startServiceDetailFragment(offerObject);
//            }
//        });
//        parentTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startServiceDetailFragment(offerObject);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onClick(View view) {
    }

    void startServiceDetailFragment(int position){
    }
}

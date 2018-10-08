package com.example.admin.being_human;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Request;

import java.util.List;

/**
 * Created by Ali Sherazi on 7/24/2017.
 */
public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
    private List<Requests> L;
    private Context context;
    private LayoutInflater Inflater;
    private BaseActivity baseActivity;
    public RequestAdapter(Context context, int resource, List<Requests> L, BaseActivity baseActivity)
    {
        System.out.println("hereAdapter");
        this.L=L;
        this.context=context;
        this.Inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.baseActivity=baseActivity;
        System.out.println("hereAmove");
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=Inflater.inflate(R.layout.items_request, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Requests p=L.get(position);
        holder.Name.setText(p.getUserName());
        holder.Contact.setText(p.getPhoneNumber());
        holder.Gender.setText(p.getGender());
        if(p.getAfd().equals("true"))
        {
            holder.acceptButton.setText("Complete");
            holder.acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(baseActivity,"Completed",Toast.LENGTH_LONG).show();
                    new CompleteRequest(p.getId()).execute();
                    ((RelativeLayout)baseActivity.findViewById(R.id.container)).removeAllViews();
                    FragmentManager fragmentM = baseActivity.getSupportFragmentManager();
                    fragmentM.beginTransaction().replace(R.id.container,new HelpFragment()).commit();
                }
            });
            holder.cancelButton.setVisibility(View.INVISIBLE);
        }
        else {
            holder.cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(baseActivity,"Canceled",Toast.LENGTH_LONG).show();
                    new CancelRequest(p.getId()).execute();
                    ((RelativeLayout)baseActivity.findViewById(R.id.container)).removeAllViews();
                    FragmentManager fragmentM = baseActivity.getSupportFragmentManager();
                    fragmentM.beginTransaction().replace(R.id.container,new Home(baseActivity)).commit();
                }
            });
            holder.acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(baseActivity,"Accepted",Toast.LENGTH_LONG).show();
                    new AcceptRequest(p.getId()).execute();
                    ((RelativeLayout)baseActivity.findViewById(R.id.container)).removeAllViews();
                    FragmentManager fragmentM = baseActivity.getSupportFragmentManager();
                    fragmentM.beginTransaction().replace(R.id.container,new Home(baseActivity)).commit();
                }
            });
        }

        holder.view.setTag(p);
    }

    @Override
    public int getItemCount() {
        return L.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name;
        public TextView Gender;
        public TextView Contact;
        Button acceptButton;
        Button cancelButton;
        View view;

        public MyViewHolder(View view)
        {
            super(view);
            Name=(TextView) view.findViewById(R.id.tvRequesterName);
            Gender=(TextView) view.findViewById(R.id.tvRequesterGender);
            Contact=(TextView) view.findViewById(R.id.tvRequesterNumber);
            acceptButton=(Button) view.findViewById(R.id.acceptReq);
            cancelButton=(Button) view.findViewById(R.id.cancalReq);
            this.view=view;
        }
    }
}

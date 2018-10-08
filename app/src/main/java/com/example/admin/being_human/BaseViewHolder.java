package com.example.admin.being_human;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.OnClick;

/**
 * Created by kulsoom on 07-Jan-18.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public View layout;

    public BaseViewHolder(View v) {
        super(v);
        this.layout = v;
    }


    @OnClick
    void onClick(View view) {
        System.out.println("Position " + getAdapterPosition()); //clicked item position
    }
}

package com.example.admin.being_human;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {
    TextView emailOne;
    TextView emailTwo;
    TextView number;
    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_about, container, false);
          emailOne=(TextView)view.findViewById(R.id.emailAddress);
          emailTwo=(TextView)view.findViewById(R.id.tvzuni);
          number =(TextView)view.findViewById(R.id.number);
        emailOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("vnd.android.cursor.item/email");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {emailOne.getText().toString()});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Help");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Tell us your detailed issue");
                startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
            }
        });
        emailTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("vnd.android.cursor.item/email");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {emailTwo.getText().toString()});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Help");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Tell us your detailed issue");
                startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
            }
        });
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number.getText().toString() ));
                startActivity(intent);
            }
        });
       return view;
    }

}

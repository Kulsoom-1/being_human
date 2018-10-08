package com.example.admin.being_human;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SettingFragment extends Fragment {
    private EditText pass,mail;
    private String email;
    private Button update;
    private String name;
    private BaseActivity baseActivity;
    private UserUpdateTask userUpdateTask=null;

    public SettingFragment(String email,BaseActivity baseActivity,String name) {
        this.email=email;
        this.name=name;
        this.baseActivity=baseActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        mail = (EditText) view.findViewById(R.id.editemail);
        pass = (EditText) view.findViewById(R.id.editpassword);
        mail.setText(email);
        mail.setEnabled(false);
        ((TextView)view.findViewById(R.id.textSignup)).setText(name);
        update=(Button) view.findViewById(R.id.btn_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attempUpdate();
            }
        });
        return  view;
    }
    private void attempUpdate()
    {
        if(userUpdateTask!=null)
        {
            return;
        }

        if(pass.getText().toString().equals(""))
        {
            pass.setError("Required");
            pass.requestFocus();
            return;
        }
        else if(pass.getText().toString().length()<6)
        {
            pass.setError("Length should be greater than 5");
            pass.requestFocus();
            return;
        }
        userUpdateTask=new UserUpdateTask(email,pass.getText().toString());
        userUpdateTask.execute();
    }

    public class UserUpdateTask extends AsyncTask<Void, Void, String> {

        String email;
        String pass;

        public UserUpdateTask(String email, String pass) {
            this.email = email;
            this.pass = pass;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String parameters="?email="+email+
                    "&pass="+pass;
            String su_url="http://zmdelivery.com/BeingHuman/update_profile.php";
            try {
                URL url=new URL(su_url+parameters);
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
            if(s.equals("success"))
            {
                Toast.makeText(baseActivity, "Update Successful", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(baseActivity, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
            onCancelled();
        }

        @Override
        protected void onCancelled() {
            userUpdateTask=null;

        }
    }

}

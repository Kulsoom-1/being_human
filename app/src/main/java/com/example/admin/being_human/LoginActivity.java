package com.example.admin.being_human;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private UserLoginTask mAuthTask = null;
    UserSessionManager session;
    StorageManager storageManager;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if (!isOnline()) {
            Toast.makeText(this, "Please Connect to internet", Toast.LENGTH_LONG).show();
            finish();
            return ;
        }
        storageManager=new StorageManager(this);
        session=new UserSessionManager(getApplicationContext());
        setContentView(R.layout.activity_login);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#900000")));
        getSupportActionBar().setTitle("BeingHuman");
    }

    @OnClick(R.id.btn_on_login)
    public void getHome(View view) {
        if(!isOnline())
        {
            Toast.makeText(this,"Please Connect to internet",Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if (mAuthTask != null) {
            return;
        }
        email = (EditText) findViewById(R.id.loginemail);
        password = (EditText) findViewById(R.id.loginpass);

        String emailref = email.getText().toString().trim();
        String passwordref = password.getText().toString().trim();


        //checking if email is empty
        if (TextUtils.isEmpty(emailref)) {
            email.setError("Required");
            email.requestFocus();
            return;
        }
        else if (!isValid(emailref)) {
            email.setError("Invalid Email");
            email.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(passwordref)) {
            password.setError("Required");
            email.requestFocus();
            return;
        }

        mAuthTask = new UserLoginTask(emailref, passwordref);
        mAuthTask.execute((Void) null);
    }
    private boolean isValid(String email)
    {
        return email.contains("@");
    }
    private boolean isOnline()
    {
        //checking network
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        else {
            return false;
        }
    }

    // User Login Async Task
    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String parameters="?email="+mEmail+
                    "&pass="+mPassword;
            System.out.println("here:"+mEmail+"-"+mPassword);
            String restautent_signup_url="http://zmdelivery.com/BeingHuman/login.php";
            try {
                URL url=new URL(restautent_signup_url+parameters);
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
        protected void onPostExecute(final String response) {
            mAuthTask = null;
            if(response.equals(("nothing")))
            {
                email.setError("Email or password is wrong");
                email.requestFocus();
            }
            else {
                System.out.println("Login Response: "+response);
                user = new JSONParser().parseLoginUser(response);
           session.createUserLoginSession(user.getName(), user.getEmail(),user.getDonar_status());
                try {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == 0) {
                        System.out.println("Permission available");
                        ActivityCompat.requestPermissions(LoginActivity.this,
                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},111);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
//                try{
//                    while (true) {
//                        try {
//                            if (!storageManager.getData(Constant.LOCATION_LATITUDE).equals("")) {
//                                break;
//                            }
//                        } catch (Exception ex) {
//
//                        }
//                       // Toast.makeText(getApplicationContext(), "please wait, trying to get your location", Toast.LENGTH_SHORT).show();
//                    }
                    gotoBaseActivity();
                }
//                catch (final Exception lException)
//                {
//                    Log.e("Getdata", "Exception looping Cursor: " + lException.getMessage());
//                }


        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    private void startLocation() {
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == 0) {
                System.out.println("Permission available");
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},111);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void gotoBaseActivity()
    {
        Intent intent=new Intent(this,BaseActivity.class);
        startActivity(intent);
        finish();
    }

}

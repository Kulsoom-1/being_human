package com.example.admin.being_human;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActiveLocation act=new ActiveLocation();
    private String userId;
    private ProgressBar progressBar;
    private Spinner bloodgroup;
    private EditText nam,age,city,address,lastdonation,phonenum,pass,mail;
    private CheckBox chkDonar;
    private RadioButton genderF;
    private RadioButton genderM;
    String gender;
    RadioGroup gen;
    int loginStatus;
    String selected;
    UserSessionManager session;

    private ArrayAdapter<String> mAdapter;
    String[] BloodGroup = {"A+", "B+", "AB+",
            "AB-", "A-", "B-", "O+", "O-"};
    String Email;
    String Password;
    String Name;
    String Age;
    String City;
    String LastDonation;
    String PhoneNum;
    String Address;
    UserSignupTask userSignupTask=null;
    int dStatus;

    @BindView(R.id.btn_on_sign_up) Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_signup);
        if (!isOnline()) {
            Toast.makeText(this, "Please Connect to internet", Toast.LENGTH_LONG).show();
            finish();
            return ;
        }
        session=new UserSessionManager(this);
        bloodgroup=(Spinner) findViewById(R.id.editBloodGroup);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,BloodGroup
        );
        gen=(RadioGroup) findViewById(R.id.gen);
        genderF = (RadioButton) findViewById(R.id.radioGenderFemale);
        genderM = (RadioButton) findViewById(R.id.radioGenderMale);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        bloodgroup.setAdapter(spinnerArrayAdapter);
        bloodgroup.setOnItemSelectedListener(this);
        //Actionbar color set
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#900000")));
        getSupportActionBar().setTitle("BeingHuman");
    }

    @OnClick(R.id.btn_on_sign_up)
    public void Onclick(View view) {

        if(userSignupTask!=null)
        {
            return;
        }
        if(!isOnline()){
            Toast.makeText(this,"Internet problem", Toast.LENGTH_LONG).show();
        }

        mail=(EditText) findViewById(R.id.editemail);
        pass=(EditText)findViewById( R.id.editpassword);
        nam=(EditText)findViewById( R.id.editName);
        age=(EditText)findViewById( R.id.editAge);
        city=(EditText)findViewById( R.id.editCity);
        lastdonation=(EditText)findViewById( R.id.editDonation);
        phonenum=(EditText)findViewById( R.id.editPhone);
        address=(EditText)findViewById(R.id.editaddress) ;
        chkDonar=(CheckBox) findViewById(R.id.chkbxDonar);


        Email = mail.getText().toString().trim();
        Password = pass.getText().toString().trim();
        Name = nam.getText().toString().trim();
        Age = age.getText().toString().trim();
        City = city.getText().toString().trim();
        LastDonation = lastdonation.getText().toString().trim();
        PhoneNum = phonenum.getText().toString().trim();
        Address=address.getText().toString().trim();
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        if (chkDonar.isChecked()) {
          dStatus = 1;
        } else {
            dStatus = 0;
        }

        if (TextUtils.isEmpty(Name)) {
            Toast.makeText(getApplicationContext(), "Enter Name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Age)) {
            Toast.makeText(getApplicationContext(), "Enter Age!", Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println("Gender Id: " + gen.getCheckedRadioButtonId());

        if(gen.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            // get selected radio button from radioGroup
            int selectedId = gen.getCheckedRadioButtonId();
            if(selectedId == genderF.getId()){
                gender=genderF.getText().toString();
            }
            else
            {
                gender=genderM.getText().toString();
            }
         //   Toast.makeText(getApplicationContext(), gender+" is selected", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(City)) {
            Toast.makeText(getApplicationContext(), "Enter City!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(LastDonation)) {
            Toast.makeText(getApplicationContext(), "Enter Last Location!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(PhoneNum)) {
            Toast.makeText(getApplicationContext(), "Enter Phone Number!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Address)) {
            Toast.makeText(getApplicationContext(), "Enter Address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (Password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        userSignupTask=new UserSignupTask(selected,Name,Age,gender,City,LastDonation,PhoneNum,Address,Email,Password);
        userSignupTask.execute();
    }

    public class UserSignupTask extends AsyncTask<Void, Void, String> {
        String bg;
        String name;
        String age;
        String gender;
        String city;
        String ld;
        String pn;
        String address;
        String email;
        String pass;

        public UserSignupTask(String selected, String name, String age, String gender, String city, String ld, String pn, String address, String email, String pass) {
            if(selected.equals("A+"))
            {
                bg="Aplus";
            }
            else if(selected.equals("B+")){
                bg="Bplus";
            }
            else if(selected.equals("A-")){
                bg="Anagetive";
            }
            else if(selected.equals("B-")){
                bg="Bnagetive";
            }
            else if(selected.equals("AB+")){
                bg="ABplus";
            }
            else if(selected.equals("AB-")){
                bg="ABnagetive";
            }
            else if(selected.equals("O+")){
                bg="Oplus";
            }
            else{
                bg="Onagetive";
            }
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.city = city;
            this.ld = ld;
            this.pn = pn;
            this.address = address;
            this.email = email;
            this.pass = pass;
        }

        @Override
        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String parameters="?bg="+bg+
                    "&name="+name+
                    "&age="+age+
                    "&gender="+gender+
                    "&city="+city+
                    "&ld="+ld+
                    "&pn="+pn+
                    "&address="+address+
                    "&email="+email+
                    "&pass="+pass+
                    "&ds="+dStatus;
            String su_url="http://zmdelivery.com/BeingHuman/signup.php";
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
            progressBar.setVisibility(View.GONE);
            Log.d("Sherazi","signup-"+s);
            if(s.equals("success"))
            {
                session.createUserLoginSession(name,email,String.valueOf(dStatus));
                gotoBaseActivity();

            }
            else if(s.equals("email"))
            {
                Toast.makeText(getBaseContext(), "Account exists", Toast.LENGTH_SHORT).show();
            }
            userSignupTask=null;
        }

        @Override
        protected void onCancelled() {
            userSignupTask=null;

        }
    }
    private void writeNewUser(String userId,String nam, String bloodgroup,String city,String address, String age,String lastdonation,String Phonenum, String email, String password,boolean active ) {
        UserSignup user = new UserSignup(password,email,nam,bloodgroup,age,city,lastdonation,Phonenum,address,active);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selected = adapterView.getItemAtPosition(i).toString();
        System.out.println("Spinner Text : " + selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void gotoBaseActivity()
    {
        Intent intent=new Intent(getBaseContext(),BaseActivity.class);
        startActivity(intent);
        finish();
    }
    private boolean isOnline()
    {
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
}



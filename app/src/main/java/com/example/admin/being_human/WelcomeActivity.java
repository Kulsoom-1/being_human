package com.example.admin.being_human;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                StorageManager storageManager= new StorageManager(getBaseContext());
//                if(storageManager.getData(Constant.LOGSTATUS)=="true"){
//                    Intent in = new Intent(getBaseContext(), BaseActivity.class);
//                    startActivity(in);
//                }
//                else {
                    Intent in = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(in);
//                }

            }
        }, 400);
    }
}

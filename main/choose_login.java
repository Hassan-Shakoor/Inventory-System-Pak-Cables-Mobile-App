package com.mamoo.istproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class choose_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);
        Button admin=findViewById(R.id.btn_admin);
        Button user=findViewById(R.id.btn_user);

        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_PORTRAIT){
            admin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),loginActivity.class);
                    intent.putExtra("choose","admin");
                    startActivity(intent);

                }
            });
            user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),loginActivity.class);
                    intent.putExtra("choose","user");
                    startActivity(intent);
                }
            });

        }
    }
}
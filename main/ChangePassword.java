package com.mamoo.istproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        EditText et_current=findViewById(R.id.et_current_password);
        EditText et_new_pin=findViewById(R.id.et_new_pin);


    }
}
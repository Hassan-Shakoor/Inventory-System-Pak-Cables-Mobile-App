package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUser extends AppCompatActivity {
    ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Button adduser=findViewById(R.id.btn_add_user);

        EditText et_username=findViewById(R.id.et_add_username);
        EditText et_password=findViewById(R.id.et_add_password);

        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_PORTRAIT){
            adduser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username=et_username.getText().toString().trim().toLowerCase();
                    String password=et_password.getText().toString().trim().toLowerCase();


                    if(!username.matches("") && !password.matches("")){
                        progressDialog2 = new ProgressDialog(AddUser.this);
                        progressDialog2.setMessage("Adding New User"); // Setting Message
                        progressDialog2.setTitle("Add User Screen"); // Setting Title
                        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog2.setCanceledOnTouchOutside(false);
                        progressDialog2.show();
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

                        DatabaseReference userdbs = FirebaseDatabase.getInstance().getReference().
                                child("Users").child(username);
                        userdbs.child("password").setValue(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });


                        et_username.setText("");

                        et_password.setText("");
                        progressDialog2.dismiss();
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                        Toast.makeText(AddUser.this, "New User Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        et_username.setError("username and password are mandatory");
                        et_password.setError("username and password are mandatory");
                        et_username.setText("");

                        et_password.setText("");

                    }


                }
            });
        }
    }
}
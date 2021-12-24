package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class loginActivity extends AppCompatActivity {
    ProgressDialog progressDialog2;
    String choose="";
    String check_name="";
    String check_pass="";
    int found=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login Screen");
        EditText et_username=findViewById(R.id.et_username);
        EditText et_password=findViewById(R.id.et_password);




        Button login=findViewById(R.id.btn_login);
        Button forgot=findViewById(R.id.btn_forgot_password);

        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_PORTRAIT){


            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog2 = new ProgressDialog(loginActivity.this);
                    progressDialog2.setMessage("Signing in"); // Setting Message
                    progressDialog2.setTitle("Login Screen"); // Setting Title
                    progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog2.setCanceledOnTouchOutside(false);
                    progressDialog2.show();

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
                    String username=et_username.getText().toString().trim().toLowerCase();
                    String password=et_password.getText().toString().trim().toLowerCase();


                        DatabaseReference admin_dbs = FirebaseDatabase.getInstance().getReference();
                        admin_dbs.child("Users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot node: snapshot.getChildren()){
                                    Log.d("hassan","node: "+node);
                                    if(node.getKey().matches(username)){
                                        check_name=node.getKey();
                                        Log.d("hassan","check name: "+check_name);
                                        for(DataSnapshot node1: node.getChildren()){
                                            Log.d("hassan","node1: "+node1);
                                            if(node1.getKey().matches("password")){

                                                if(node1.getValue().toString().matches(password)){
                                                    Log.d("hassan","password: "+password);
                                                    Log.d("hassan","value: "+node1.getValue());
                                                    Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                    et_username.setText("");
                                                    et_password.setText("");
                                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                                    if(username.trim().matches("irfan")){
                                                        intent.putExtra("choose","admin");
                                                    }
                                                    else{
                                                        intent.putExtra("choose","user");
                                                    }


                                                    startActivity(intent);
                                                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                                                    progressDialog2.dismiss();
                                                    finish();
                                                }
                                                else{
                                                    progressDialog2.dismiss();
                                                    et_username.setText("");
                                                    et_password.setText("");

                                                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);



                                                    et_password.setError("Incorrect Password");
                                                    Toast.makeText(loginActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    }


                                }
                                 if(check_name.matches("")){
                                    progressDialog2.dismiss();
                                    et_username.setText("");
                                    et_password.setText("");

                                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                                    et_username.setError("username or password is incorrect");
                                    et_password.setError("username or password is incorrect");
                                    Toast.makeText(loginActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                }
            });
            forgot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this);
                    builder.setTitle("Forgot Password");
                    final EditText input0 = new EditText(loginActivity.this);
                    input0.setHint("Enter the username you want to change the password for");

                    input0.setInputType(InputType.TYPE_CLASS_TEXT);


                    final EditText input = new EditText(loginActivity.this);
                    input.setHint("Name the company who developed this software");


                    input.setInputType(InputType.TYPE_CLASS_TEXT);


                    final EditText input1 = new EditText(loginActivity.this);
                    input1.setHint("Name the developer who you interacted");

                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    LinearLayout ll = new LinearLayout(loginActivity.this);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.addView(input0);
                    ll.addView(input);
                    ll.addView(input1);

                    builder.setView(ll);





                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String inputt1 = input.getText().toString().trim().toLowerCase();
                           String inputt2=input1.getText().toString().trim().toLowerCase();
                           String inputt0=input0.getText().toString().trim().toLowerCase();

                            DatabaseReference admin_dbs = FirebaseDatabase.getInstance().getReference();
                            admin_dbs.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot node: snapshot.getChildren()){
                                        if(node.getKey().matches(inputt0)){
                                            found=1;
                                        }
                                    }
                                    if(inputt1.matches("softex solutions") && inputt2.matches("hassan")
                                            && !inputt0.matches("") && found==1){
                                        Intent intent=new Intent(getApplicationContext(),forgot_password.class);
                                        intent.putExtra("username",inputt0);
                                        startActivity(intent);

                                    }
                                    else{
                                        Toast.makeText(loginActivity.this, "information is not correct", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });



                           ///

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            });
        }
        if(orientation1==Configuration.ORIENTATION_LANDSCAPE){


            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog2 = new ProgressDialog(loginActivity.this);
                    progressDialog2.setMessage("Signing in"); // Setting Message
                    progressDialog2.setTitle("Login Screen"); // Setting Title
                    progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog2.setCanceledOnTouchOutside(false);
                    progressDialog2.show();

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
                    String username=et_username.getText().toString().trim().toLowerCase();
                    String password=et_password.getText().toString().trim().toLowerCase();


                    DatabaseReference admin_dbs = FirebaseDatabase.getInstance().getReference();
                    admin_dbs.child("Users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot node: snapshot.getChildren()){
                                Log.d("hassan","node: "+node);
                                if(node.getKey().matches(username)){
                                    check_name=node.getKey();
                                    Log.d("hassan","check name: "+check_name);
                                    for(DataSnapshot node1: node.getChildren()){
                                        Log.d("hassan","node1: "+node1);
                                        if(node1.getKey().matches("password")){

                                            if(node1.getValue().toString().matches(password)){
                                                Log.d("hassan","password: "+password);
                                                Log.d("hassan","value: "+node1.getValue());
                                                Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                et_username.setText("");
                                                et_password.setText("");
                                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                                if(username.trim().matches("irfan")){
                                                    intent.putExtra("choose","admin");
                                                }
                                                else{
                                                    intent.putExtra("choose","user");
                                                }


                                                startActivity(intent);
                                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                                                progressDialog2.dismiss();
                                                finish();
                                            }
                                            else{
                                                progressDialog2.dismiss();
                                                et_username.setText("");
                                                et_password.setText("");

                                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);



                                                et_password.setError("Incorrect Password");
                                                Toast.makeText(loginActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }


                            }
                            if(check_name.matches("")){
                                progressDialog2.dismiss();
                                et_username.setText("");
                                et_password.setText("");

                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                                et_username.setError("username or password is incorrect");
                                et_password.setError("username or password is incorrect");
                                Toast.makeText(loginActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            });
            forgot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this);
                    builder.setTitle("Forgot Password");
                    final EditText input0 = new EditText(loginActivity.this);
                    input0.setHint("Enter the username you want to change the password for");

                    input0.setInputType(InputType.TYPE_CLASS_TEXT);


                    final EditText input = new EditText(loginActivity.this);
                    input.setHint("Name the company who developed this software");


                    input.setInputType(InputType.TYPE_CLASS_TEXT);


                    final EditText input1 = new EditText(loginActivity.this);
                    input1.setHint("Name the developer who you interacted");

                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    LinearLayout ll = new LinearLayout(loginActivity.this);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.addView(input0);
                    ll.addView(input);
                    ll.addView(input1);

                    builder.setView(ll);





                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String inputt1 = input.getText().toString().trim().toLowerCase();
                            String inputt2=input1.getText().toString().trim().toLowerCase();
                            String inputt0=input0.getText().toString().trim().toLowerCase();

                            DatabaseReference admin_dbs = FirebaseDatabase.getInstance().getReference();
                            admin_dbs.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot node: snapshot.getChildren()){
                                        if(node.getKey().matches(inputt0)){
                                            found=1;
                                        }
                                    }
                                    if(inputt1.matches("softex solutions") && inputt2.matches("hassan")
                                            && !inputt0.matches("") && found==1){
                                        Intent intent=new Intent(getApplicationContext(),forgot_password.class);
                                        intent.putExtra("username",inputt0);
                                        startActivity(intent);

                                    }
                                    else{
                                        Toast.makeText(loginActivity.this, "information is not correct", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });



                            ///

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            });
        }

    }
}
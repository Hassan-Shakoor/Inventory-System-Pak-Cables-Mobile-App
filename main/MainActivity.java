package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private boolean table_flg=true;
    String choose="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        choose=intent.getStringExtra("choose");
        Log.d("choose","choose: "+choose);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       if(choose.matches("admin")){
           getMenuInflater().inflate(R.menu.menubar, menu);
           ColorDrawable colorDrawable= new ColorDrawable(Color.parseColor("#ff0000"));
           ActionBar actionBar=getSupportActionBar();
           actionBar.setBackgroundDrawable(colorDrawable);
           getSupportActionBar().setTitle("");
       }
       else if(choose.matches("user")){
           getMenuInflater().inflate(R.menu.user_menu, menu);
           ColorDrawable colorDrawable= new ColorDrawable(Color.parseColor("#ff0000"));
           ActionBar actionBar=getSupportActionBar();
           actionBar.setBackgroundDrawable(colorDrawable);
           //getSupportActionBar().setTitle("");
       }


        // Set BackgroundDrawable
        //actionBar.setTitle(Html.fromHtml("<font color='#000080'>Thug Chats </font>"));
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(choose.matches("admin")){

                if(id==R.id.item_name){
                    Intent intent=new Intent(getApplicationContext(),itemcoding.class);
                    startActivity(intent);

                }
                else if(id==R.id.partycoding){
                    Intent intent=new Intent(getApplicationContext(),partycoding.class);
                    startActivity(intent);
                }
                else if(id==R.id.btn_sale){
                    Intent intent=new Intent(getApplicationContext(),Sale.class);
                    startActivity(intent);
                }
                else if(id==R.id.input){
                    Intent intent=new Intent(getApplicationContext(),Input.class);
                    startActivity(intent);
                }
                else if(id==R.id.stock){
                    Intent intent=new Intent(getApplicationContext(),before_stock.class);
                    startActivity(intent);
                }

                else if(id==R.id.inputsummary){
                    Intent intent=new Intent(getApplicationContext(),InputSummary.class);
                    startActivity(intent);
                }
                else if(id==R.id.outputsumm){
                    Intent intent=new Intent(getApplicationContext(),outputactivity.class);
                    startActivity(intent);
                }
                else if(id==R.id.itemrate){
                    Intent intent=new Intent(getApplicationContext(),ItemRate.class);
                    startActivity(intent);
                }
                else if(id==R.id.stock_valueeee){
                    Intent intent=new Intent(getApplicationContext(),stock_value.class);
                    startActivity(intent);
                }
                else if(id==R.id.add_user){
                    Intent intent=new Intent(getApplicationContext(),AddUser.class);
                    startActivity(intent);
                }
                else if(id==R.id.change_password){
                    //Intent intent=new Intent(getApplicationContext(),ChangePassword.class);
                    //startActivity(intent);
                }
        }
        else if(choose.matches("user")){


            if(id==R.id.user_btn_sale){
                Intent intent=new Intent(getApplicationContext(),Sale.class);
                startActivity(intent);
            }
            else if(id==R.id.user_input){
                Intent intent=new Intent(getApplicationContext(),Input.class);
                startActivity(intent);
            }
            else if(id==R.id.user_stock){
                Intent intent=new Intent(getApplicationContext(),before_stock.class);
                startActivity(intent);
            }

            else if(id==R.id.user_inputsummary){
                Intent intent=new Intent(getApplicationContext(),InputSummary.class);
                startActivity(intent);
            }
            else if(id==R.id.user_outputsumm){
                Intent intent=new Intent(getApplicationContext(),outputactivity.class);
                startActivity(intent);
            }

            else if(id==R.id.user_stock_valueeee){
                Intent intent=new Intent(getApplicationContext(),stock_value.class);
                startActivity(intent);
            }

        }





        return super.onOptionsItemSelected(item);

    }

}
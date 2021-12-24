package com.mamoo.istproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class table_layout extends AppCompatActivity {
    TableLayout table;
    TableRow row;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_layout);

        table = findViewById(R.id.tablee);
      row = new TableRow(this);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            //    String headings[] = {"Item Code" , "Red" , "Black" , "Yellow" , "Blue" , "Green" , "White" , "Other" , "Total" , "2 Core" , "3 Core" , "4 Core" , "5 Core" , "6 Core"};
//    int color[] = {0xE2E892 , 0xE67F7F , 0x050505 , 0xFDD835 , 0x1E88E5 , 0x43A047 ,0xFFFFFF , 0xDBDF9C , 0xDBDF9C};

            TextView tv = new TextView(this);
            tv.setText(" Item Code ");
            tv.setWidth(600);
            tv.setBackgroundResource(R.drawable.border_item);
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(30);
            //tv.setBackgroundColor(Color.parseColor("#E2E892"));
            row.addView(tv);

            TextView tv1 = new TextView(this);
            tv1.setText(" Red ");
            tv1.setWidth(300);
            tv1.setBackgroundResource(R.drawable.border_red);
            tv1.setTextColor(Color.parseColor("#D1C7C7"));
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextSize(30);
            //tv1.setBackgroundColor(Color.parseColor("#E67F7F"));
            row.addView(tv1);

            TextView tv2 = new TextView(this);
            tv2.setText(" Black ");
            tv2.setWidth(300);
            tv2.setBackgroundResource(R.drawable.border_black);
            tv2.setTextColor(Color.parseColor("#7CB342"));
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextSize(30);
            //tv2.setBackgroundColor(Color.parseColor("#050505"));
            row.addView(tv2);

            TextView tv3 = new TextView(this);
            tv3.setText(" Yellow ");
            tv3.setWidth(350);
            tv3.setBackgroundResource(R.drawable.border_yellow);
            tv3.setTextColor(Color.BLACK);
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextSize(30);
            //tv3.setBackgroundColor(Color.parseColor("#FDD835"));
            row.addView(tv3);

            TextView tv4 = new TextView(this);
            tv4.setText(" Blue ");
            tv4.setWidth(300);
            tv4.setBackgroundResource(R.drawable.border_blue);
            tv4.setTextColor(Color.BLACK);
            tv4.setGravity(Gravity.CENTER);
            tv4.setTextSize(30);
            //tv4.setBackgroundColor(Color.parseColor("#1E88E5"));
            row.addView(tv4);

            TextView tv5 = new TextView(this);
            tv5.setText(" Green ");
            tv5.setWidth(350);
            tv5.setBackgroundResource(R.drawable.border_green);
            tv5.setTextColor(Color.BLACK);
            tv5.setGravity(Gravity.CENTER);
            tv5.setTextSize(30);
            //tv5.setBackgroundColor(Color.parseColor("#43A047"));
            row.addView(tv5);

            TextView tv6 = new TextView(this);
            tv6.setText(" White ");
            tv6.setWidth(300);
            tv6.setBackgroundResource(R.drawable.border_white);
            tv6.setTextColor(Color.BLACK);
            tv6.setGravity(Gravity.CENTER);
            tv6.setTextSize(30);
            //tv6.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv6);

            TextView tv7 = new TextView(this);
            tv7.setText(" Other ");
            tv7.setWidth(300);
            tv7.setBackgroundResource(R.drawable.border_other);
            tv7.setTextColor(Color.BLACK);
            tv7.setGravity(Gravity.CENTER);
            tv7.setTextSize(30);
            //tv7.setBackgroundColor(Color.parseColor("#DBDF9C"));
            row.addView(tv7);

            TextView tv8 = new TextView(this);
            tv8.setText(" Total ");
            tv8.setWidth(300);
            tv8.setBackgroundResource(R.drawable.border_white);
            tv8.setTextColor(Color.BLACK);
            tv8.setGravity(Gravity.CENTER);
            tv8.setTextSize(30);
            //tv8.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv8);

            TextView tv9 = new TextView(this);
            tv9.setText(" 2 Core ");
            tv9.setWidth(400);
            tv9.setBackgroundResource(R.drawable.border_white);
            tv9.setTextColor(Color.BLACK);
            tv9.setGravity(Gravity.CENTER);
            tv9.setTextSize(30);
            //tv9.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv9);

            TextView tv10 = new TextView(this);
            tv10.setText(" 3 Core ");
            tv10.setWidth(400);
            tv10.setBackgroundResource(R.drawable.border_white);
            tv10.setTextColor(Color.BLACK);
            tv10.setGravity(Gravity.CENTER);
            tv10.setTextSize(30);
            //tv10.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv10);

            TextView tv11 = new TextView(this);
            tv11.setText(" 4 Core ");
            tv11.setWidth(400);
            tv11.setBackgroundResource(R.drawable.border_white);
            tv11.setTextColor(Color.BLACK);
            tv11.setGravity(Gravity.CENTER);
            tv11.setTextSize(30);
            //tv11.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv11);

            TextView tv12 = new TextView(this);
            tv12.setText(" 5 Core ");
            tv12.setWidth(400);
            tv12.setBackgroundResource(R.drawable.border_white);
            tv12.setTextColor(Color.BLACK);
            tv12.setGravity(Gravity.CENTER);
            tv12.setTextSize(30);
            //tv12.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv12);

            TextView tv13 = new TextView(this);
            tv13.setText(" 6 Core ");
            tv13.setWidth(400);
            tv13.setBackgroundResource(R.drawable.border_white);
            tv13.setTextColor(Color.BLACK);
            tv13.setGravity(Gravity.CENTER);
            tv13.setTextSize(30);
            //tv13.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv13);

            table.addView(row);

            for (int i = 0; i < 10; i++) {
                TableRow row1 = new TableRow(this);
                for (int j = 0; j < 14; j++) {
                    EditText et = new EditText(this);
                    et.setText(" ");
                    et.setWidth(400);
                    et.setBackgroundResource(R.drawable.border_white);
                    et.setTextColor(Color.BLACK);
                    et.setGravity(Gravity.CENTER);
                    et.setTextSize(18);
                    et.setHeight(140);
                    row1.addView(et);
                }
                table.addView(row1);
            }
          //  TableRow nrow = (TableRow) table.getChildAt(1);
            //EditText itemcode = (EditText) nrow.getChildAt(0);

           // String item_code = itemcode.getText().toString();

        }
    }
}
//
//    String headings[] = {"Item Code" , "Red" , "Black" , "Yellow" , "Blue" , "Green" , "White" , "Other" , "Total" , "2 Core" , "3 Core" , "4 Core" , "5 Core" , "6 Core"};
//    int color[] = {0xE2E892 , 0xE67F7F , 0x050505 , 0xFDD835 , 0x1E88E5 , 0x43A047 ,0xFFFFFF , 0xDBDF9C , 0xDBDF9C};

//
//for (int i = 0; i < headings.length; i++) {
//        row = new TableRow(this);
//        tv = new TextView(this);
//        if (i < 8) {
//        tv.setBackgroundColor(color[i]);
//        } else {
//        tv.setBackgroundColor(0xFFFFFF);
//        }
//        tv.setText(headings[i]);
//        if (i == 2) {
//        tv.setTextColor(0x7CB342);
//        } else {
//        tv.setTextColor(Color.BLACK);
//        }
//        tv.setGravity(Gravity.CENTER);
//        row.addView(tv);
//        table.addView(row);
//        }
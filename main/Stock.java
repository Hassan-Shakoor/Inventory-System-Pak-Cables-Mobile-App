package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Stock extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> date_list=new ArrayList<String >();

    ArrayList<ArrayList<String>> rows_list = new ArrayList<ArrayList<String>>();
    public ArrayList<Integer> core_data=new ArrayList<Integer>();
    ArrayList<Integer> menu_array=new ArrayList<Integer>();
    ProgressDialog progressDialog;
    DatabaseReference root;
    public TableRow row;
    TableLayout table;
    TextView ab;
    public TableRow row1;


    public stock_data obj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        Intent intent=getIntent();
       String date=intent.getStringExtra("date");


        table = findViewById(R.id.ledger_table);
        row = new TableRow(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String millisInString  = dateFormat.format(new Date());


        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            TextView tv = new TextView(getApplicationContext());
            tv.setText(" Item Code ");
            tv.setWidth(300);
            tv.setBackgroundResource(R.drawable.border_item);
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(20);

            row.addView(tv);

            TextView tv1 = new TextView(getApplicationContext());
            tv1.setText(" Red ");
            tv1.setWidth(200);
            tv1.setBackgroundResource(R.drawable.border_red);
            tv1.setTextColor(Color.parseColor("#D1C7C7"));
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextSize(20);
            //tv1.setBackgroundColor(Color.parseColor("#E67F7F"));
            row.addView(tv1);

            TextView tv2 = new TextView(getApplicationContext());
            tv2.setText(" Black ");
            tv2.setWidth(200);
            tv2.setBackgroundResource(R.drawable.border_black);
            tv2.setTextColor(Color.parseColor("#7CB342"));
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextSize(20);
            //tv2.setBackgroundColor(Color.parseColor("#050505"));
            row.addView(tv2);

            TextView tv3 = new TextView(getApplicationContext());
            tv3.setText(" Yellow ");
            tv3.setWidth(200);
            tv3.setBackgroundResource(R.drawable.border_yellow);
            tv3.setTextColor(Color.BLACK);
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextSize(20);
            //tv3.setBackgroundColor(Color.parseColor("#FDD835"));
            row.addView(tv3);

            TextView tv4 = new TextView(getApplicationContext());
            tv4.setText(" Blue ");
            tv4.setWidth(200);
            tv4.setBackgroundResource(R.drawable.border_blue);
            tv4.setTextColor(Color.BLACK);
            tv4.setGravity(Gravity.CENTER);
            tv4.setTextSize(20);

            row.addView(tv4);

            TextView tv5 = new TextView(getApplicationContext());
            tv5.setText(" Green ");
            tv5.setWidth(200);
            tv5.setBackgroundResource(R.drawable.border_green);
            tv5.setTextColor(Color.BLACK);
            tv5.setGravity(Gravity.CENTER);
            tv5.setTextSize(20);
            //tv5.setBackgroundColor(Color.parseColor("#43A047"));
            row.addView(tv5);

            TextView tv6 = new TextView(getApplicationContext());
            tv6.setText(" White ");
            tv6.setWidth(200);
            tv6.setBackgroundResource(R.drawable.border_white);
            tv6.setTextColor(Color.BLACK);
            tv6.setGravity(Gravity.CENTER);
            tv6.setTextSize(20);
            //tv6.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv6);

            TextView tv7 = new TextView(getApplicationContext());
            tv7.setText(" Other ");
            tv7.setWidth(200);
            tv7.setBackgroundResource(R.drawable.border_other);
            tv7.setTextColor(Color.BLACK);
            tv7.setGravity(Gravity.CENTER);
            tv7.setTextSize(20);
            //tv7.setBackgroundColor(Color.parseColor("#DBDF9C"));
            row.addView(tv7);

            TextView tv8 = new TextView(getApplicationContext());
            tv8.setText(" Total ");
            tv8.setWidth(200);
            tv8.setBackgroundResource(R.drawable.border_white);
            tv8.setTextColor(Color.BLACK);
            tv8.setGravity(Gravity.CENTER);
            tv8.setTextSize(20);
            //tv8.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv8);

            TextView tv9 = new TextView(getApplicationContext());
            tv9.setText(" 2 Core ");
            tv9.setWidth(200);
            tv9.setBackgroundResource(R.drawable.border_white);
            tv9.setTextColor(Color.BLACK);
            tv9.setGravity(Gravity.CENTER);
            tv9.setTextSize(20);
            //tv9.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv9);

            TextView tv10 = new TextView(getApplicationContext());
            tv10.setText(" 3 Core ");
            tv10.setWidth(200);
            tv10.setBackgroundResource(R.drawable.border_white);
            tv10.setTextColor(Color.BLACK);
            tv10.setGravity(Gravity.CENTER);
            tv10.setTextSize(20);
            //tv10.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv10);

            TextView tv11 = new TextView(getApplicationContext());
            tv11.setText(" 4 Core ");
            tv11.setWidth(200);
            tv11.setBackgroundResource(R.drawable.border_white);
            tv11.setTextColor(Color.BLACK);
            tv11.setGravity(Gravity.CENTER);
            tv11.setTextSize(20);
            //tv11.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv11);

            TextView tv12 = new TextView(getApplicationContext());
            tv12.setText(" 5 Core ");
            tv12.setWidth(200);
            tv12.setBackgroundResource(R.drawable.border_white);
            tv12.setTextColor(Color.BLACK);
            tv12.setGravity(Gravity.CENTER);
            tv12.setTextSize(20);
            //tv12.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv12);

            TextView tv13 = new TextView(getApplicationContext());
            tv13.setText(" 6 Core ");
            tv13.setWidth(200);
            tv13.setBackgroundResource(R.drawable.border_white);
            tv13.setTextColor(Color.BLACK);
            tv13.setGravity(Gravity.CENTER);
            tv13.setTextSize(20);
            //tv13.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv13);


            table.addView(row);

            DatabaseReference date_root = FirebaseDatabase.getInstance().getReference().child("Stock");
            date_root.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int count=0;
                    if (snapshot.exists()) {
                        for (DataSnapshot data : snapshot.getChildren()) {

                            String datee = data.getKey();
                            Log.d("hash", "date: " + datee);
                            date_list.add(datee);
                            count++;

                        }
                    }
                    Collections.sort(date_list, new Comparator<String>() {
                        @Override
                        public int compare(String date1, String date2) {

                            String day1 = date1.toString().substring(0, 2);
                            String month1 = date1.toString().substring(3, 5);
                            String year1 = date1.toString().substring(6);

                            String day2 = date2.toString().substring(0, 2);
                            String month2 = date2.toString().substring(3, 5);
                            String year2 = date2.toString().substring(6);

                            // Condition to check the year
                            if (year2.compareTo(year1) > 0)
                                return -1;
                            else if (year2.compareTo(year1) < 0)
                                return 1;

                                // Condition to check the month
                            else if (month2.compareTo(month1) > 0)
                                return -1;
                            else if (month2.compareTo(month1) < 0)
                                return 1;

                                // Condition to check the day
                            else if (day2.compareTo(day1) > 0)
                                return -1;
                            else
                                return 1;
                        }


                    });

                    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Stock").child(date);
                    root.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            if (snapshot.exists()) {

                                for (DataSnapshot nodes : snapshot.getChildren()) {
                                    String itemcodeees = String.valueOf(nodes.getKey());
                                    arrayList.add(itemcodeees);

                                    for (DataSnapshot nodes1 : nodes.getChildren()) {
                                        if(nodes1.getKey().equals("Data")){

                                            obj=nodes1.getValue(stock_data.class);


                                            arrayList.add(nodes1.getValue(Tablerow.class).getCol1());
                                            arrayList.add(nodes1.getValue(Tablerow.class).getCol2());
                                            arrayList.add(nodes1.getValue(Tablerow.class).getCol3());
                                            arrayList.add(nodes1.getValue(Tablerow.class).getCol4());
                                            arrayList.add(nodes1.getValue(Tablerow.class).getCol5());
                                            arrayList.add(nodes1.getValue(Tablerow.class).getCol6());
                                            arrayList.add(nodes1.getValue(Tablerow.class).getCol7());
                                            arrayList.add(nodes1.getValue(Tablerow.class).getCol8());
                                            arrayList.add(nodes1.getValue(Tablerow.class).getCol9());
                                            arrayList.add(nodes1.getValue(Tablerow.class).getCol10());
                                            arrayList.add(nodes1.getValue(Tablerow.class).getCol11());
                                            arrayList.add(nodes1.getValue(Tablerow.class).getCol12());
                                            arrayList.add(nodes1.getValue(Tablerow.class).getCol13());

                                            rows_list.add(arrayList);

                                        }


                                    }
                                    Log.d("gat","sze: "+rows_list.size());

                                    int z = 0;
                                    for (int i = 0; i <rows_list.size(); i++) {
                                        row1 = new TableRow(getApplicationContext());
                                        for (int j = 0; j < 14; j++) {
                                            ab = new TextView(getApplicationContext());

                                            if (j==8){
                                                int sum =string_to_integer(rows_list.get(i).get(1))+
                                                        string_to_integer(rows_list.get(i).get(2))+
                                                        string_to_integer(rows_list.get(i).get(3))+
                                                        string_to_integer(rows_list.get(i).get(4))+
                                                        string_to_integer(rows_list.get(i).get(5))+
                                                        string_to_integer(rows_list.get(i).get(6))+
                                                        string_to_integer(rows_list.get(i).get(7));
                                                ab.setText(sum+"");
                                                ab.setWidth(200);
                                                ab.setBackgroundResource(R.drawable.border_white);
                                                ab.setTextColor(Color.BLACK);
                                                ab.setGravity(Gravity.CENTER);
                                                ab.setTextSize(16);
                                                View vi=table.getChildAt(0);
                                                TableRow ro = (TableRow) vi;
                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                ab.setTag(rows_list.get(0).get(0)+":"+item_code_boxx.getText().toString());

                                                ab.setOnClickListener(Stock.this::onClick);
                                                ab.setHeight(115);


                                            }
                                            else{
                                                if (j==0){
                                                    String n=rows_list.get(i).get(j);
                                                    n=n.replace("-","/");
                                                    n=n.replace("o",".");


                                                    n=n.replace('h','#');
                                                    n=n.replace('d','$');
                                                    n=n.replace('s','[');

                                                    n=n.replace('e',']');
                                                    n=n.replace('t','*');
                                                    Log.d("Mam",n);
                                                    ab.setText(n);
                                                    //ab.setText(rows_list.get(i).get(j));
                                                    ab.setWidth(300);
                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                    ab.setTextColor(Color.BLACK);
                                                    ab.setGravity(Gravity.CENTER);

                                                    ab.setTextSize(16);
                                                    ab.setHeight(115);



                                                }
                                                if(j>=9){
                                                    //split(rows_list.get(i).get(j));
                                                    ab.setText(""+split(rows_list.get(i).get(j)));
                                                    ab.setWidth(200);
                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                    ab.setTextColor(Color.BLACK);
                                                    ab.setGravity(Gravity.CENTER);
                                                    ab.setOnClickListener(Stock.this::onClick);
                                                    ab.setTextSize(16);
                                                    View vi=table.getChildAt(0);
                                                    TableRow ro = (TableRow) vi;
                                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                    ab.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                                                    ab.setHeight(115);
                                                }



                                                else{

                                                    ab.setText(rows_list.get(i).get(j));
                                                    ab.setWidth(200);
                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                    ab.setTextColor(Color.BLACK);
                                                    ab.setGravity(Gravity.CENTER);
                                                    ab.setOnClickListener(Stock.this::onClick);
                                                    ab.setTextSize(16);
                                                    View vi=table.getChildAt(0);
                                                    TableRow ro = (TableRow) vi;
                                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                    ab.setTag(rows_list.get(0).get(0)+":"+item_code_boxx.getText().toString());
                                                    ab.setHeight(115);
                                                }

                                            }






                                            row1.addView(ab);

                                        }
                                        z++;



                                        table.addView(row1);
                                    }
                                    arrayList.clear();
                                    rows_list.clear();

                                }




                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }

                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //Hassna shakooor rana




        }


    }

    int string_to_integer(String s){

        if (s.matches("")){
            return 0;
        }
        else{
            return Integer.parseInt(s);
        }
    }

    @Override
    public void onClick(View v) {
        menu_array.clear();

        TextView vv=(TextView)v;
        TextView gg= findViewById(R.id.ledger_textview);

        String a= vv.getTag().toString();
        String [] split_string_list=a.split(":");
        String aaa=split_string_list[split_string_list.length-1].trim();
        Log.d("tag","string aaa: "+aaa);

        String number="";
        if(aaa.contains("90")||(aaa.contains("180"))){
        for (int i=0;i<aaa.length();i++){
            Character value=aaa.charAt(i);
            Log.d("tag","value: "+value);
            if("0123456789".indexOf(value) != -1){
                number+=value;
            }
            else{

                menu_array.add(Integer.valueOf(number));
                number="";
            }

        }
        menu_array.add(Integer.valueOf(number));



        PopupMenu menu = new PopupMenu(getApplicationContext(), v);
        for(int x=0; x< menu_array.size(); x++){
            menu.getMenu().add(String.valueOf(menu_array.get(x)));
        }

        menu.show();}
        gg.setText(""+vv.getTag());




    }



    int split(String aaa){
        core_data.clear();
        String number="";
        int sum_of_number=0;
        ///core_data.add(3);
        if(aaa.matches("")){
            return 0;
        }

        for (int i=0;i<aaa.length();i++){
            Character value=aaa.charAt(i);
            if("0123456789".indexOf(value) != -1){
                number+=value;
            }
            else{

                core_data.add(Integer.valueOf(number));
                number="";
            }
        }
        core_data.add(Integer.valueOf(number));
        for(Integer data: core_data){
            sum_of_number+=data;
        }
        //Log.d("tag","funtion call "+core_data.size());
        return sum_of_number;


    }
}




















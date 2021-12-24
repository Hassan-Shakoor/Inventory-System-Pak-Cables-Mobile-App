package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Date;

public class change_ledger extends AppCompatActivity {
    DatabaseReference database;
    public ArrayList<Integer> core_data=new ArrayList<Integer>();
    DatabaseReference database1;
    ArrayList<Summary> summary;
    ArrayList<String> itemcodes;
    String new_date="";
    TextView ab;
    String party="";
    String core="";

    ArrayList<ArrayList<String>> rows_list = new ArrayList<ArrayList<String>>();
    String ledger_itemcode;
    String ledger_item;
    ArrayList<Integer> menu_array=new ArrayList<Integer>();
    ArrayList<String> dates_list=new ArrayList<String>();
    public static int count_dates=0;

    int rows , snap , count;
    TableLayout table;
    TableRow row1;

    TextView DateFrom , DateTo;
    Button generate;
    String date1="";
    String date2="";
    public int height=90;
    public int font=11;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ledger);
        database = FirebaseDatabase.getInstance().getReference();

        Intent item=getIntent();
        ledger_item=item.getStringExtra("item");

        ledger_itemcode=item.getStringExtra("ledgerItem");


       ledger_itemcode = ledger_itemcode.replace("/", "slash");
        ledger_itemcode = ledger_itemcode.replace(".", "dot");



        ledger_itemcode=ledger_itemcode.replace("#","hash");
        ledger_itemcode=ledger_itemcode.replace("$","dollar");
        ledger_itemcode=ledger_itemcode.replace("[","openbrace");
        ledger_itemcode=ledger_itemcode.replace("]","closebrace");
        ledger_itemcode=ledger_itemcode.replace("*","star");
        date1=item.getStringExtra("from");
        date2=item.getStringExtra("to");

        Log.d("faiz", "itemcode: "+ledger_itemcode);
        Log.d("faiz", "item: "+ledger_item);
        database1 = FirebaseDatabase.getInstance().getReference();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String millisInString = dateFormat.format(new Date());
        summary = new ArrayList<Summary>();
        itemcodes = new ArrayList<String>();
        rows = 0;
        snap = 0;
        count = 0;

        table = findViewById(R.id.change_ledger_table);
        row1 = new TableRow(this);
        row1.setBaselineAligned(false);





        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_PORTRAIT){



            TableRow row = new TableRow(change_ledger.this);
            TextView tv0 = new TextView(getApplicationContext());
            tv0.setText(" No");
            tv0.setWidth(120);
            tv0.setBackgroundResource(R.drawable.border_item);
            tv0.setTextColor(Color.BLACK);
            tv0.setGravity(Gravity.CENTER);
            tv0.setTextSize(font);

            row.addView(tv0);


            TextView tv = new TextView(getApplicationContext());
            tv.setText(" Type ");
            tv.setWidth(120);
            tv.setBackgroundResource(R.drawable.border_item);
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(font);

            row.addView(tv);

            TextView tv1 = new TextView(getApplicationContext());
            tv1.setText(" Item ");

            tv1.setWidth(200);
            tv1.setBackgroundResource(R.drawable.border_red);
            tv1.setTextColor(Color.parseColor("#D1C7C7"));
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextSize(font);
            //tv1.setBackgroundColor(Color.parseColor("#E67F7F"));
            row.addView(tv1);

            TextView tv2 = new TextView(getApplicationContext());
            tv2.setText(" Dealer ");

            tv2.setWidth(250);
            tv2.setBackgroundResource(R.drawable.border_black);
            tv2.setTextColor(Color.parseColor("#7CB342"));
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextSize(font);
            //tv2.setBackgroundColor(Color.parseColor("#050505"));
            row.addView(tv2);

            TextView tv3 = new TextView(getApplicationContext());
            tv3.setText(" Date ");
            tv3.setWidth(300);
            tv3.setBackgroundResource(R.drawable.border_yellow);
            tv3.setTextColor(Color.BLACK);
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextSize(font);
            row.addView(tv3);

            table.addView(row);
            String datefrom = date1;
            Log.d("zeesh", "datefrommmm: " + datefrom);
            String dateto = date2;
            database1.child("Billno").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot billno_node: snapshot.getChildren()){
                        for(DataSnapshot itemcode_node: billno_node.getChildren()){
                            for(DataSnapshot date_data:itemcode_node.getChildren()){
                                if(date_data.getKey().matches("Date")){
                                    String date= String.valueOf(date_data.getValue());
                                    Log.d("change", "date: "+date_data.getValue());
                                    if ((date.compareTo(datefrom) >= 0 && date.compareTo(dateto) <= 0) ||
                                            ((date.compareTo(datefrom) == 0) && (date.compareTo(dateto) == 0))){
                                        count_dates+=1;
                                    }

                                }
                            }
                        }
                    }

                    Log.d("change", "count 1:"+ count_dates);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            database.child("Billno").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot billno_node: snapshot.getChildren()){
                        ArrayList<String> arrayList = new ArrayList<>();
                        String bill=billno_node.getKey();

                        for(DataSnapshot itemcode_node:billno_node.getChildren()) {

                            if(itemcode_node.getKey().matches(ledger_itemcode)){
                                Log.d("change", "here in Date and node : "+ itemcode_node.getKey());
                                String itemcode = itemcode_node.getKey();
                                arrayList.add(bill);
                                arrayList.add("Input");

                                for (DataSnapshot data_date : itemcode_node.getChildren()) {
                                    Log.d("change", "here in Date and node : "+ data_date.getKey());
                                    if (data_date.getKey().matches("Data")) {
                                        Log.d("change", "here in Date: ");
                                        if(ledger_item.matches("Red")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol1());
                                        }
                                        else if(ledger_item.matches("Black")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol2());
                                        }
                                        else if(ledger_item.matches("Yellow")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol3());
                                        }
                                        else if(ledger_item.matches("Blue")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol4());
                                        }
                                        else if(ledger_item.matches("Green")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol5());
                                        }
                                        else if(ledger_item.matches("White")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol6());
                                        }
                                        else if(ledger_item.matches("Other")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol7());
                                        }
                                        else if(ledger_item.matches("Total")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol8());
                                        }
                                        else if(ledger_item.matches("2 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol9());
                                        }
                                        else if(ledger_item.matches("3 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol10());
                                        }
                                        else if(ledger_item.matches("4 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol11());
                                        }
                                        else if(ledger_item.matches("5 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol12());
                                        }
                                        else if(ledger_item.matches("6 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol13());
                                        }


                                    } if (data_date.getKey().matches("Date")) {
                                        Log.d("change", "here in Date: ");
                                        String date = String.valueOf(data_date.getValue());

                                        party="";
                                        arrayList.add(party);
                                        arrayList.add(String.valueOf(data_date.getValue()));
                                        if ((date.compareTo(datefrom) >= 0 && date.compareTo(dateto) <= 0) ||
                                                ((date.compareTo(datefrom) == 0) && (date.compareTo(dateto) == 0))) { Log.d("change", "here: ");
                                            // if (rows_list.size()==1){


                                            //  rows_list.set(0,arrayList);


                                            //  }
                                            //  else{

                                            rows_list.add(arrayList);
                                            // }

                                        } else {
                                            Log.d("change", "here: ");
                                            arrayList.clear();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Log.d("faiz", "list size: "+ rows_list.size());
                    for(int a=0; a<rows_list.size(); a++){
                        Log.d("faiz", "list: "+ rows_list.get(a));
                    }

                    /// change


                    int z = 0;
                    for (int i = 0; i <rows_list.size(); i++) {
                        row1 = new TableRow(getApplicationContext());
                        for (int j = 0; j < 5; j++) {
                            ab = new TextView(getApplicationContext());
                            if(j==4){
                                ab.setText(rows_list.get(i).get(j));

                                ab.setWidth(300);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(font);
                                ab.setHeight(height);
                                View vi=table.getChildAt(0);
                                TableRow ro = (TableRow) vi;
                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                ab.setTag(rows_list.get(i).get(0)+":"+rows_list.get(i).get(1)+":"+item_code_boxx.getText().toString());

                                ab.setOnClickListener(change_ledger.this::onClick);
                            }
                            if(j==2){
                                if(ledger_item.matches("2 Core")){
                                    menu_array.clear();

                                    String number="";
                                    int output=0;
                                    String num=rows_list.get(i).get(j);
                                    //split(rows_list.get(i).get(j));

                                   ab.setWidth(200);
                                    ab.setBackgroundResource(R.drawable.border_white);
                                    ab.setTextColor(Color.BLACK);
                                    ab.setGravity(Gravity.CENTER);
                                    ab.setOnClickListener(change_ledger.this::onClick);
                                    ab.setTextSize(font);
                                    View vi=table.getChildAt(0);
                                    TableRow ro = (TableRow) vi;
                                    Log.d("kaali","num: "+num);
                                  if(!num.matches("")){
                                      core=num;
                                      for (int a=0;a<num.length();a++){
                                          Character value=num.charAt(a);
                                          Log.d("tag","value: "+value);
                                          if("0123456789".indexOf(value) != -1){
                                              number+=value;
                                          }
                                          else{
                                              if(number.equals("")!=true){
                                                  output+=Integer.parseInt(number);
                                              }



                                              number="";
                                          }

                                      }
                                      if(number.equals("")!=true){
                                          output=output+Integer.parseInt(number);
                                      }

                                      Log.d("mtag","sum: "+output);
                                  }

                                    ab.setText(String.valueOf(output));
                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                    Log.d("kaali","core: "+core);

                                    ab.setTag(rows_list.get(i).get(0) + ":" + item_code_boxx.getText().toString()+":"+core);


                                    ab.setHeight(height);
                                }
                                else if(ledger_item.matches("3 Core")){
                                    menu_array.clear();

                                    String number="";
                                    int output=0;
                                    String num=rows_list.get(i).get(j);
                                    //split(rows_list.get(i).get(j));

                                    ab.setWidth(200);
                                    ab.setBackgroundResource(R.drawable.border_white);
                                    ab.setTextColor(Color.BLACK);
                                    ab.setGravity(Gravity.CENTER);
                                    ab.setOnClickListener(change_ledger.this::onClick);
                                    ab.setTextSize(font);
                                    View vi=table.getChildAt(0);
                                    TableRow ro = (TableRow) vi;
                                    Log.d("kaali","num: "+num);
                                    if(!num.matches("")){
                                        core=num;
                                        for (int a=0;a<num.length();a++){
                                            Character value=num.charAt(a);
                                            Log.d("tag","value: "+value);
                                            if("0123456789".indexOf(value) != -1){
                                                number+=value;
                                            }
                                            else{
                                                if(number.equals("")!=true){
                                                    output+=Integer.parseInt(number);
                                                }



                                                number="";
                                            }

                                        }
                                        if(number.equals("")!=true){
                                            output=output+Integer.parseInt(number);
                                        }

                                        Log.d("mtag","sum: "+output);
                                    }

                                    ab.setText(String.valueOf(output));
                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                    Log.d("kaali","core: "+core);

                                    ab.setTag(rows_list.get(i).get(0) + ":" + item_code_boxx.getText().toString()+":"+core);


                                    ab.setHeight(height);
                                }
                                else if(ledger_item.matches("4 Core")){
                                    menu_array.clear();

                                    String number="";
                                    int output=0;
                                    String num=rows_list.get(i).get(j);
                                    //split(rows_list.get(i).get(j));

                                    ab.setWidth(200);
                                    ab.setBackgroundResource(R.drawable.border_white);
                                    ab.setTextColor(Color.BLACK);
                                    ab.setGravity(Gravity.CENTER);
                                    ab.setOnClickListener(change_ledger.this::onClick);
                                    ab.setTextSize(font);
                                    View vi=table.getChildAt(0);
                                    TableRow ro = (TableRow) vi;
                                    Log.d("kaali","num: "+num);
                                    if(!num.matches("")){
                                        core=num;
                                        for (int a=0;a<num.length();a++){
                                            Character value=num.charAt(a);
                                            Log.d("tag","value: "+value);
                                            if("0123456789".indexOf(value) != -1){
                                                number+=value;
                                            }
                                            else{
                                                if(number.equals("")!=true){
                                                    output+=Integer.parseInt(number);
                                                }



                                                number="";
                                            }

                                        }
                                        if(number.equals("")!=true){
                                            output=output+Integer.parseInt(number);
                                        }

                                        Log.d("mtag","sum: "+output);
                                    }

                                    ab.setText(String.valueOf(output));
                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                    Log.d("kaali","core: "+core);

                                    ab.setTag(rows_list.get(i).get(0) + ":" + item_code_boxx.getText().toString()+":"+core);


                                    ab.setHeight(height);
                                }
                                else if(ledger_item.matches("5 Core")){
                                    menu_array.clear();

                                    String number="";
                                    int output=0;
                                    String num=rows_list.get(i).get(j);
                                    //split(rows_list.get(i).get(j));

                                    ab.setWidth(200);
                                    ab.setBackgroundResource(R.drawable.border_white);
                                    ab.setTextColor(Color.BLACK);
                                    ab.setGravity(Gravity.CENTER);
                                    ab.setOnClickListener(change_ledger.this::onClick);
                                    ab.setTextSize(font);
                                    View vi=table.getChildAt(0);
                                    TableRow ro = (TableRow) vi;
                                    Log.d("kaali","num: "+num);
                                    if(!num.matches("")){
                                        core=num;
                                        for (int a=0;a<num.length();a++){
                                            Character value=num.charAt(a);
                                            Log.d("tag","value: "+value);
                                            if("0123456789".indexOf(value) != -1){
                                                number+=value;
                                            }
                                            else{
                                                if(number.equals("")!=true){
                                                    output+=Integer.parseInt(number);
                                                }



                                                number="";
                                            }

                                        }
                                        if(number.equals("")!=true){
                                            output=output+Integer.parseInt(number);
                                        }

                                        Log.d("mtag","sum: "+output);
                                    }

                                    ab.setText(String.valueOf(output));
                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                    Log.d("kaali","core: "+core);

                                    ab.setTag(rows_list.get(i).get(0) + ":" + item_code_boxx.getText().toString()+":"+core);


                                    ab.setHeight(height);
                                }
                               else if(ledger_item.matches("6 Core")){
                                    menu_array.clear();

                                    String number="";
                                    int output=0;
                                    String num=rows_list.get(i).get(j);
                                    //split(rows_list.get(i).get(j));

                                    ab.setWidth(200);
                                    ab.setBackgroundResource(R.drawable.border_white);
                                    ab.setTextColor(Color.BLACK);
                                    ab.setGravity(Gravity.CENTER);
                                    ab.setOnClickListener(change_ledger.this::onClick);
                                    ab.setTextSize(font);
                                    View vi=table.getChildAt(0);
                                    TableRow ro = (TableRow) vi;
                                    Log.d("kaali","num: "+num);
                                    if(!num.matches("")){
                                        core=num;
                                        for (int a=0;a<num.length();a++){
                                            Character value=num.charAt(a);
                                            Log.d("tag","value: "+value);
                                            if("0123456789".indexOf(value) != -1){
                                                number+=value;
                                            }
                                            else{
                                                if(number.equals("")!=true){
                                                    output+=Integer.parseInt(number);
                                                }



                                                number="";
                                            }

                                        }
                                        if(number.equals("")!=true){
                                            output=output+Integer.parseInt(number);
                                        }

                                        Log.d("mtag","sum: "+output);
                                    }

                                    ab.setText(String.valueOf(output));
                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                    Log.d("kaali","core: "+core);

                                    ab.setTag(rows_list.get(i).get(0) + ":" + item_code_boxx.getText().toString()+":"+core);


                                    ab.setHeight(height);
                                }
                                else{
                                    ab.setText(rows_list.get(i).get(j));

                                    ab.setWidth(400);
                                    ab.setBackgroundResource(R.drawable.border_white);
                                    ab.setTextColor(Color.BLACK);
                                    ab.setGravity(Gravity.CENTER);
                                    ab.setTextSize(16);
                                    ab.setHeight(115);
                                    View vi=table.getChildAt(0);
                                    TableRow ro = (TableRow) vi;
                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                    ab.setTag(rows_list.get(i).get(0)+":"+rows_list.get(i).get(1)+":"+item_code_boxx.getText().toString());

                                    ab.setOnClickListener(change_ledger.this::onClick);
                                }



                            }
                            else if(j==3){
                                ab.setText(rows_list.get(i).get(j));
                                ab.setWidth(250);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(font);
                                ab.setHeight(height);
                                View vi=table.getChildAt(0);
                                TableRow ro = (TableRow) vi;
                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                ab.setTag(rows_list.get(i).get(0)+":"+rows_list.get(i).get(1)+":"+item_code_boxx.getText().toString());

                                ab.setOnClickListener(change_ledger.this::onClick);
                            }
                            else{
                                ab.setText(rows_list.get(i).get(j));
                                ab.setWidth(120);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(font);
                                ab.setHeight(height);
                                View vi=table.getChildAt(0);
                                TableRow ro = (TableRow) vi;
                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                ab.setTag(rows_list.get(i).get(0)+":"+rows_list.get(i).get(1)+":"+item_code_boxx.getText().toString());

                                ab.setOnClickListener(change_ledger.this::onClick);
                            }


                            row1.addView(ab);

                        }
                        z++;



                        table.addView(row1);

                    }
                    rows_list.clear();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            database.child("SaleBill").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot billno_node: snapshot.getChildren()){
                        ArrayList<String> arrayList = new ArrayList<>();
                        String bill=billno_node.getKey();

                        for(DataSnapshot itemcode_node:billno_node.getChildren()) {

                            if(itemcode_node.getKey().matches(ledger_itemcode)){
                                Log.d("change", "here in Date and node : "+ itemcode_node.getKey());
                                String itemcode = itemcode_node.getKey();
                                arrayList.add(bill);
                                arrayList.add("output");

                                for (DataSnapshot data_date : itemcode_node.getChildren()) {
                                    Log.d("change", "here in Date and node : "+ data_date.getKey());
                                    if (data_date.getKey().matches("Data")) {
                                        Log.d("change", "here in Date: ");
                                        if(ledger_item.matches("Red")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol1());
                                        }
                                        else if(ledger_item.matches("Black")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol2());
                                        }
                                        else if(ledger_item.matches("Yellow")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol3());
                                        }
                                        else if(ledger_item.matches("Blue")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol4());
                                        }
                                        else if(ledger_item.matches("Green")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol5());
                                        }
                                        else if(ledger_item.matches("White")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol6());
                                        }
                                        else if(ledger_item.matches("Other")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol7());
                                        }
                                        else if(ledger_item.matches("Total")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol8());
                                        }
                                        else if(ledger_item.matches("2 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol9());
                                        }
                                        else if(ledger_item.matches("3 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol10());
                                        }
                                        else if(ledger_item.matches("4 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol11());
                                        }
                                        else if(ledger_item.matches("5 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol12());
                                        }
                                        else if(ledger_item.matches("6 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol13());
                                        }


                                    }
                                    if(data_date.getKey().matches("Party")){
                                        String partyy = String.valueOf(data_date.getValue());
                                        arrayList.add(String.valueOf(partyy));
                                        arrayList.add(String.valueOf(new_date));


                                    }
                                    if (data_date.getKey().matches("Date")) {
                                        Log.d("change", "here in Date: ");
                                        new_date = String.valueOf(data_date.getValue());



                                        if ((new_date.compareTo(datefrom) >= 0 && new_date.compareTo(dateto) <= 0) ||
                                                ((new_date.compareTo(datefrom) == 0) && (new_date.compareTo(dateto) == 0))) { Log.d("change", "here: ");
                                            // if (rows_list.size()==1){


                                            //  rows_list.set(0,arrayList);


                                            //  }
                                            //  else{

                                            rows_list.add(arrayList);
                                            // }

                                        } else {
                                            Log.d("change", "here: ");
                                            arrayList.clear();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Log.d("faiz", "list size: "+ rows_list.size());
                    for(int a=0; a<rows_list.size(); a++){
                        Log.d("faiz", "list: "+ rows_list.get(a));
                    }

                    /// change


                    int z = 0;
                    for (int i = 0; i <rows_list.size(); i++) {
                        row1 = new TableRow(getApplicationContext());
                        for (int j = 0; j < 5; j++) {
                            ab = new TextView(getApplicationContext());
                            if(j==4){
                                ab.setText(rows_list.get(i).get(j));

                                ab.setWidth(400);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(16);
                                ab.setHeight(115);
                            }
                            if(j==2){
                                ab.setText(rows_list.get(i).get(j));

                                ab.setWidth(400);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(16);
                                ab.setHeight(115);
                            }
                            else{
                                ab.setText(rows_list.get(i).get(j));
                                ab.setWidth(200);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(16);
                                ab.setHeight(115);
                            }


                            View vi=table.getChildAt(0);
                            TableRow ro = (TableRow) vi;
                            TextView item_code_boxx = (TextView) ro.getChildAt(j);
                            ab.setTag(rows_list.get(i).get(0)+":"+rows_list.get(i).get(1)+":"+item_code_boxx.getText().toString());

                            ab.setOnClickListener(change_ledger.this::onClick);










                            row1.addView(ab);

                        }
                        z++;



                        table.addView(row1);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

                  /* database.child("OutputSummary").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot node: snapshot.getChildren()){
                                String date=node.getKey();

                                if ((date.compareTo(datefrom) >= 0 && date.compareTo(dateto) <= 0) ||
                                        ((date.compareTo(datefrom) == 0) && (date.compareTo(dateto) == 0))) {
                                    //Log.d("hello"," date"+ date);
                                    for(DataSnapshot node1: node.getChildren()){
                                        if(node1.getKey().matches(ledger_itemcode)){
                                            //Log.d("ban", "ledger item: "+ ledger_itemcode);
                                            String itemcodeees = String.valueOf(node1.getKey());

                                            arrayList.clear();
                                            arrayList.add(date);
                                            arrayList.add(itemcodeees);

                                            for (DataSnapshot nodes1 : node1.getChildren()) {
                                                if(nodes1.getKey().equals("Data")){

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

                                                    //
                                                    //rows_list.clear();

                                                    if (rows_list.size()==1){

                                                        //rows_list.clear();
                                                        //Log.d("hello"," date array"+date);
                                                        rows_list.set(0,arrayList);
                                                        //rows_list.set(0,arrayList);
                                                        //rows_list.add(arrayList);

                                                    }
                                                    else{

                                                        rows_list.add(arrayList);
                                                    }
                                                    Log.d("hello0"," date"+rows_list.get(0).get(0));



                                                    int z = 0;
                                                    for (int i = 0; i <rows_list.size(); i++) {
                                                        row1 = new TableRow(getApplicationContext());
                                                        for (int j = 0; j < 15; j++) {
                                                            ab = new TextView(getApplicationContext());


                                                            if (j==9){

                                                                int sum =string_to_integer(rows_list.get(i).get(2))+
                                                                        string_to_integer(rows_list.get(i).get(3))+
                                                                        string_to_integer(rows_list.get(i).get(4))+
                                                                        string_to_integer(rows_list.get(i).get(5))+
                                                                        string_to_integer(rows_list.get(i).get(6))+
                                                                        string_to_integer(rows_list.get(i).get(7))+
                                                                        string_to_integer(rows_list.get(i).get(8));
                                                                ab.setText(sum+"");
                                                                ab.setWidth(200);
                                                                ab.setBackgroundResource(R.drawable.border_white);
                                                                ab.setTextColor(Color.BLACK);
                                                                ab.setGravity(Gravity.CENTER);
                                                                ab.setTextSize(16);
                                                                //   View vi=table.getChildAt(0);
                                                                //   TableRow ro = (TableRow) vi;
                                                                //  TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                //  ab.setTag(rows_list.get(0).get(0)+":"+item_code_boxx.getText().toString());

                                                                // ab.setOnClickListener(Stock.this::onClick);
                                                                ab.setHeight(115);


                                                            }
                                                            else{
                                                                if(j==0){

                                                                    ab.setText(rows_list.get(i).get(j));
                                                                    Log.d("hello"," date array"+rows_list.get(0).get(0));
                                                                }
                                                                if (j==1){
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

                                                                if(j>=10){
                                                                    //split(rows_list.get(i).get(j));
                                                                    ab.setText(""+split(rows_list.get(i).get(j)));
                                                                    ab.setWidth(200);
                                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                                    ab.setTextColor(Color.BLACK);
                                                                    ab.setGravity(Gravity.CENTER);
                                                                    //  ab.setOnClickListener(Stock.this::onClick);
                                                                    ab.setTextSize(16);
                                                                    // View vi=table.getChildAt(0);
                                                                    // TableRow ro = (TableRow) vi;
                                                                    // TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                    // ab.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                                                                    ab.setHeight(115);
                                                                }




                                                                else{

                                                                    ab.setText(rows_list.get(i).get(j));
                                                                    ab.setWidth(200);
                                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                                    ab.setTextColor(Color.BLACK);
                                                                    ab.setGravity(Gravity.CENTER);
                                                                    //  ab.setOnClickListener(Stock.this::onClick);
                                                                    ab.setTextSize(16);
                                                                    //  View vi=table.getChildAt(0);
                                                                    //  TableRow ro = (TableRow) vi;
                                                                    //  TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                    //  ab.setTag(rows_list.get(0).get(0)+":"+item_code_boxx.getText().toString());
                                                                    ab.setHeight(115);
                                                                }



                                                            }






                                                            row1.addView(ab);

                                                        }
                                                        z++;



                                                        table.addView(row1);
                                                    }

                                                    //Log.d("hello"," date array"+date);

                                                    //arrayList.clear();

                                                }


                                            }
                                        }
                                    }

                                }

                            }



                            //arrayList.clear();
                            //rows_list.clear();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/



            Log.d("TAG", "Count snap" + count + " " + snap);








        }
        if(orientation1==Configuration.ORIENTATION_LANDSCAPE){



            TableRow row = new TableRow(change_ledger.this);
            TextView tv0 = new TextView(getApplicationContext());
            tv0.setText(" No");
            tv0.setWidth(120);
            tv0.setBackgroundResource(R.drawable.border_item);
            tv0.setTextColor(Color.BLACK);
            tv0.setGravity(Gravity.CENTER);
            tv0.setTextSize(font);

            row.addView(tv0);


            TextView tv = new TextView(getApplicationContext());
            tv.setText(" Type ");
            tv.setWidth(120);
            tv.setBackgroundResource(R.drawable.border_item);
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(font);

            row.addView(tv);

            TextView tv1 = new TextView(getApplicationContext());
            tv1.setText(" Item ");

            tv1.setWidth(200);
            tv1.setBackgroundResource(R.drawable.border_red);
            tv1.setTextColor(Color.parseColor("#D1C7C7"));
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextSize(font);
            //tv1.setBackgroundColor(Color.parseColor("#E67F7F"));
            row.addView(tv1);

            TextView tv2 = new TextView(getApplicationContext());
            tv2.setText(" Dealer ");

            tv2.setWidth(250);
            tv2.setBackgroundResource(R.drawable.border_black);
            tv2.setTextColor(Color.parseColor("#7CB342"));
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextSize(font);
            //tv2.setBackgroundColor(Color.parseColor("#050505"));
            row.addView(tv2);

            TextView tv3 = new TextView(getApplicationContext());
            tv3.setText(" Date ");
            tv3.setWidth(300);
            tv3.setBackgroundResource(R.drawable.border_yellow);
            tv3.setTextColor(Color.BLACK);
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextSize(font);
            row.addView(tv3);

            table.addView(row);
            String datefrom = date1;
            Log.d("zeesh", "datefrommmm: " + datefrom);
            String dateto = date2;
            database1.child("Billno").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot billno_node: snapshot.getChildren()){
                        for(DataSnapshot itemcode_node: billno_node.getChildren()){
                            for(DataSnapshot date_data:itemcode_node.getChildren()){
                                if(date_data.getKey().matches("Date")){
                                    String date= String.valueOf(date_data.getValue());
                                    Log.d("change", "date: "+date_data.getValue());
                                    if ((date.compareTo(datefrom) >= 0 && date.compareTo(dateto) <= 0) ||
                                            ((date.compareTo(datefrom) == 0) && (date.compareTo(dateto) == 0))){
                                        count_dates+=1;
                                    }

                                }
                            }
                        }
                    }

                    Log.d("change", "count 1:"+ count_dates);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            database.child("Billno").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot billno_node: snapshot.getChildren()){
                        ArrayList<String> arrayList = new ArrayList<>();
                        String bill=billno_node.getKey();

                        for(DataSnapshot itemcode_node:billno_node.getChildren()) {

                            if(itemcode_node.getKey().matches(ledger_itemcode)){
                                Log.d("change", "here in Date and node : "+ itemcode_node.getKey());
                                String itemcode = itemcode_node.getKey();
                                arrayList.add(bill);
                                arrayList.add("Input");

                                for (DataSnapshot data_date : itemcode_node.getChildren()) {
                                    Log.d("change", "here in Date and node : "+ data_date.getKey());
                                    if (data_date.getKey().matches("Data")) {
                                        Log.d("change", "here in Date: ");
                                        if(ledger_item.matches("Red")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol1());
                                        }
                                        else if(ledger_item.matches("Black")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol2());
                                        }
                                        else if(ledger_item.matches("Yellow")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol3());
                                        }
                                        else if(ledger_item.matches("Blue")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol4());
                                        }
                                        else if(ledger_item.matches("Green")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol5());
                                        }
                                        else if(ledger_item.matches("White")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol6());
                                        }
                                        else if(ledger_item.matches("Other")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol7());
                                        }
                                        else if(ledger_item.matches("Total")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol8());
                                        }
                                        else if(ledger_item.matches("2 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol9());
                                        }
                                        else if(ledger_item.matches("3 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol10());
                                        }
                                        else if(ledger_item.matches("4 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol11());
                                        }
                                        else if(ledger_item.matches("5 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol12());
                                        }
                                        else if(ledger_item.matches("6 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol13());
                                        }


                                    } if (data_date.getKey().matches("Date")) {
                                        Log.d("change", "here in Date: ");
                                        String date = String.valueOf(data_date.getValue());

                                        party="";
                                        arrayList.add(party);
                                        arrayList.add(String.valueOf(data_date.getValue()));
                                        if ((date.compareTo(datefrom) >= 0 && date.compareTo(dateto) <= 0) ||
                                                ((date.compareTo(datefrom) == 0) && (date.compareTo(dateto) == 0))) { Log.d("change", "here: ");
                                            // if (rows_list.size()==1){


                                            //  rows_list.set(0,arrayList);


                                            //  }
                                            //  else{

                                            rows_list.add(arrayList);
                                            // }

                                        } else {
                                            Log.d("change", "here: ");
                                            arrayList.clear();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Log.d("faiz", "list size: "+ rows_list.size());
                    for(int a=0; a<rows_list.size(); a++){
                        Log.d("faiz", "list: "+ rows_list.get(a));
                    }

                    /// change


                    int z = 0;
                    for (int i = 0; i <rows_list.size(); i++) {
                        row1 = new TableRow(getApplicationContext());
                        for (int j = 0; j < 5; j++) {
                            ab = new TextView(getApplicationContext());
                            if(j==4){
                                ab.setText(rows_list.get(i).get(j));

                                ab.setWidth(300);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(font);
                                ab.setHeight(height);
                                View vi=table.getChildAt(0);
                                TableRow ro = (TableRow) vi;
                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                ab.setTag(rows_list.get(i).get(0)+":"+rows_list.get(i).get(1)+":"+item_code_boxx.getText().toString());

                                ab.setOnClickListener(change_ledger.this::onClick);
                            }
                            if(j==2){
                                if(ledger_item.matches("2 Core")){
                                    menu_array.clear();

                                    String number="";
                                    int output=0;
                                    String num=rows_list.get(i).get(j);
                                    //split(rows_list.get(i).get(j));

                                    ab.setWidth(200);
                                    ab.setBackgroundResource(R.drawable.border_white);
                                    ab.setTextColor(Color.BLACK);
                                    ab.setGravity(Gravity.CENTER);
                                    ab.setOnClickListener(change_ledger.this::onClick);
                                    ab.setTextSize(font);
                                    View vi=table.getChildAt(0);
                                    TableRow ro = (TableRow) vi;
                                    Log.d("kaali","num: "+num);
                                    if(!num.matches("")){
                                        core=num;
                                        for (int a=0;a<num.length();a++){
                                            Character value=num.charAt(a);
                                            Log.d("tag","value: "+value);
                                            if("0123456789".indexOf(value) != -1){
                                                number+=value;
                                            }
                                            else{
                                                if(number.equals("")!=true){
                                                    output+=Integer.parseInt(number);
                                                }



                                                number="";
                                            }

                                        }
                                        if(number.equals("")!=true){
                                            output=output+Integer.parseInt(number);
                                        }

                                        Log.d("mtag","sum: "+output);
                                    }

                                    ab.setText(String.valueOf(output));
                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                    Log.d("kaali","core: "+core);

                                    ab.setTag(rows_list.get(i).get(0) + ":" + item_code_boxx.getText().toString()+":"+core);


                                    ab.setHeight(height);
                                }
                                else if(ledger_item.matches("3 Core")){
                                    menu_array.clear();

                                    String number="";
                                    int output=0;
                                    String num=rows_list.get(i).get(j);
                                    //split(rows_list.get(i).get(j));

                                    ab.setWidth(200);
                                    ab.setBackgroundResource(R.drawable.border_white);
                                    ab.setTextColor(Color.BLACK);
                                    ab.setGravity(Gravity.CENTER);
                                    ab.setOnClickListener(change_ledger.this::onClick);
                                    ab.setTextSize(font);
                                    View vi=table.getChildAt(0);
                                    TableRow ro = (TableRow) vi;
                                    Log.d("kaali","num: "+num);
                                    if(!num.matches("")){
                                        core=num;
                                        for (int a=0;a<num.length();a++){
                                            Character value=num.charAt(a);
                                            Log.d("tag","value: "+value);
                                            if("0123456789".indexOf(value) != -1){
                                                number+=value;
                                            }
                                            else{
                                                if(number.equals("")!=true){
                                                    output+=Integer.parseInt(number);
                                                }



                                                number="";
                                            }

                                        }
                                        if(number.equals("")!=true){
                                            output=output+Integer.parseInt(number);
                                        }

                                        Log.d("mtag","sum: "+output);
                                    }

                                    ab.setText(String.valueOf(output));
                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                    Log.d("kaali","core: "+core);

                                    ab.setTag(rows_list.get(i).get(0) + ":" + item_code_boxx.getText().toString()+":"+core);


                                    ab.setHeight(height);
                                }
                                else if(ledger_item.matches("4 Core")){
                                    menu_array.clear();

                                    String number="";
                                    int output=0;
                                    String num=rows_list.get(i).get(j);
                                    //split(rows_list.get(i).get(j));

                                    ab.setWidth(200);
                                    ab.setBackgroundResource(R.drawable.border_white);
                                    ab.setTextColor(Color.BLACK);
                                    ab.setGravity(Gravity.CENTER);
                                    ab.setOnClickListener(change_ledger.this::onClick);
                                    ab.setTextSize(font);
                                    View vi=table.getChildAt(0);
                                    TableRow ro = (TableRow) vi;
                                    Log.d("kaali","num: "+num);
                                    if(!num.matches("")){
                                        core=num;
                                        for (int a=0;a<num.length();a++){
                                            Character value=num.charAt(a);
                                            Log.d("tag","value: "+value);
                                            if("0123456789".indexOf(value) != -1){
                                                number+=value;
                                            }
                                            else{
                                                if(number.equals("")!=true){
                                                    output+=Integer.parseInt(number);
                                                }



                                                number="";
                                            }

                                        }
                                        if(number.equals("")!=true){
                                            output=output+Integer.parseInt(number);
                                        }

                                        Log.d("mtag","sum: "+output);
                                    }

                                    ab.setText(String.valueOf(output));
                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                    Log.d("kaali","core: "+core);

                                    ab.setTag(rows_list.get(i).get(0) + ":" + item_code_boxx.getText().toString()+":"+core);


                                    ab.setHeight(height);
                                }
                                else if(ledger_item.matches("5 Core")){
                                    menu_array.clear();

                                    String number="";
                                    int output=0;
                                    String num=rows_list.get(i).get(j);
                                    //split(rows_list.get(i).get(j));

                                    ab.setWidth(200);
                                    ab.setBackgroundResource(R.drawable.border_white);
                                    ab.setTextColor(Color.BLACK);
                                    ab.setGravity(Gravity.CENTER);
                                    ab.setOnClickListener(change_ledger.this::onClick);
                                    ab.setTextSize(font);
                                    View vi=table.getChildAt(0);
                                    TableRow ro = (TableRow) vi;
                                    Log.d("kaali","num: "+num);
                                    if(!num.matches("")){
                                        core=num;
                                        for (int a=0;a<num.length();a++){
                                            Character value=num.charAt(a);
                                            Log.d("tag","value: "+value);
                                            if("0123456789".indexOf(value) != -1){
                                                number+=value;
                                            }
                                            else{
                                                if(number.equals("")!=true){
                                                    output+=Integer.parseInt(number);
                                                }



                                                number="";
                                            }

                                        }
                                        if(number.equals("")!=true){
                                            output=output+Integer.parseInt(number);
                                        }

                                        Log.d("mtag","sum: "+output);
                                    }

                                    ab.setText(String.valueOf(output));
                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                    Log.d("kaali","core: "+core);

                                    ab.setTag(rows_list.get(i).get(0) + ":" + item_code_boxx.getText().toString()+":"+core);


                                    ab.setHeight(height);
                                }
                                else if(ledger_item.matches("6 Core")){
                                    menu_array.clear();

                                    String number="";
                                    int output=0;
                                    String num=rows_list.get(i).get(j);
                                    //split(rows_list.get(i).get(j));

                                    ab.setWidth(200);
                                    ab.setBackgroundResource(R.drawable.border_white);
                                    ab.setTextColor(Color.BLACK);
                                    ab.setGravity(Gravity.CENTER);
                                    ab.setOnClickListener(change_ledger.this::onClick);
                                    ab.setTextSize(font);
                                    View vi=table.getChildAt(0);
                                    TableRow ro = (TableRow) vi;
                                    Log.d("kaali","num: "+num);
                                    if(!num.matches("")){
                                        core=num;
                                        for (int a=0;a<num.length();a++){
                                            Character value=num.charAt(a);
                                            Log.d("tag","value: "+value);
                                            if("0123456789".indexOf(value) != -1){
                                                number+=value;
                                            }
                                            else{
                                                if(number.equals("")!=true){
                                                    output+=Integer.parseInt(number);
                                                }



                                                number="";
                                            }

                                        }
                                        if(number.equals("")!=true){
                                            output=output+Integer.parseInt(number);
                                        }

                                        Log.d("mtag","sum: "+output);
                                    }

                                    ab.setText(String.valueOf(output));
                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                    Log.d("kaali","core: "+core);

                                    ab.setTag(rows_list.get(i).get(0) + ":" + item_code_boxx.getText().toString()+":"+core);


                                    ab.setHeight(height);
                                }
                                else{
                                    ab.setText(rows_list.get(i).get(j));

                                    ab.setWidth(400);
                                    ab.setBackgroundResource(R.drawable.border_white);
                                    ab.setTextColor(Color.BLACK);
                                    ab.setGravity(Gravity.CENTER);
                                    ab.setTextSize(16);
                                    ab.setHeight(115);
                                    View vi=table.getChildAt(0);
                                    TableRow ro = (TableRow) vi;
                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                    ab.setTag(rows_list.get(i).get(0)+":"+rows_list.get(i).get(1)+":"+item_code_boxx.getText().toString());

                                    ab.setOnClickListener(change_ledger.this::onClick);
                                }



                            }
                            else if(j==3){
                                ab.setText(rows_list.get(i).get(j));
                                ab.setWidth(250);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(font);
                                ab.setHeight(height);
                                View vi=table.getChildAt(0);
                                TableRow ro = (TableRow) vi;
                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                ab.setTag(rows_list.get(i).get(0)+":"+rows_list.get(i).get(1)+":"+item_code_boxx.getText().toString());

                                ab.setOnClickListener(change_ledger.this::onClick);
                            }
                            else{
                                ab.setText(rows_list.get(i).get(j));
                                ab.setWidth(120);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(font);
                                ab.setHeight(height);
                                View vi=table.getChildAt(0);
                                TableRow ro = (TableRow) vi;
                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                ab.setTag(rows_list.get(i).get(0)+":"+rows_list.get(i).get(1)+":"+item_code_boxx.getText().toString());

                                ab.setOnClickListener(change_ledger.this::onClick);
                            }


                            row1.addView(ab);

                        }
                        z++;



                        table.addView(row1);

                    }
                    rows_list.clear();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            database.child("SaleBill").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot billno_node: snapshot.getChildren()){
                        ArrayList<String> arrayList = new ArrayList<>();
                        String bill=billno_node.getKey();

                        for(DataSnapshot itemcode_node:billno_node.getChildren()) {

                            if(itemcode_node.getKey().matches(ledger_itemcode)){
                                Log.d("change", "here in Date and node : "+ itemcode_node.getKey());
                                String itemcode = itemcode_node.getKey();
                                arrayList.add(bill);
                                arrayList.add("output");

                                for (DataSnapshot data_date : itemcode_node.getChildren()) {
                                    Log.d("change", "here in Date and node : "+ data_date.getKey());
                                    if (data_date.getKey().matches("Data")) {
                                        Log.d("change", "here in Date: ");
                                        if(ledger_item.matches("Red")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol1());
                                        }
                                        else if(ledger_item.matches("Black")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol2());
                                        }
                                        else if(ledger_item.matches("Yellow")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol3());
                                        }
                                        else if(ledger_item.matches("Blue")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol4());
                                        }
                                        else if(ledger_item.matches("Green")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol5());
                                        }
                                        else if(ledger_item.matches("White")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol6());
                                        }
                                        else if(ledger_item.matches("Other")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol7());
                                        }
                                        else if(ledger_item.matches("Total")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol8());
                                        }
                                        else if(ledger_item.matches("2 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol9());
                                        }
                                        else if(ledger_item.matches("3 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol10());
                                        }
                                        else if(ledger_item.matches("4 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol11());
                                        }
                                        else if(ledger_item.matches("5 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol12());
                                        }
                                        else if(ledger_item.matches("6 Core")){
                                            arrayList.add(data_date.getValue(Tablerow.class).getCol13());
                                        }


                                    }
                                    if(data_date.getKey().matches("Party")){
                                        String partyy = String.valueOf(data_date.getValue());
                                        arrayList.add(String.valueOf(partyy));
                                        arrayList.add(String.valueOf(new_date));


                                    }
                                    if (data_date.getKey().matches("Date")) {
                                        Log.d("change", "here in Date: ");
                                        new_date = String.valueOf(data_date.getValue());



                                        if ((new_date.compareTo(datefrom) >= 0 && new_date.compareTo(dateto) <= 0) ||
                                                ((new_date.compareTo(datefrom) == 0) && (new_date.compareTo(dateto) == 0))) { Log.d("change", "here: ");
                                            // if (rows_list.size()==1){


                                            //  rows_list.set(0,arrayList);


                                            //  }
                                            //  else{

                                            rows_list.add(arrayList);
                                            // }

                                        } else {
                                            Log.d("change", "here: ");
                                            arrayList.clear();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Log.d("faiz", "list size: "+ rows_list.size());
                    for(int a=0; a<rows_list.size(); a++){
                        Log.d("faiz", "list: "+ rows_list.get(a));
                    }

                    /// change


                    int z = 0;
                    for (int i = 0; i <rows_list.size(); i++) {
                        row1 = new TableRow(getApplicationContext());
                        for (int j = 0; j < 5; j++) {
                            ab = new TextView(getApplicationContext());
                            if(j==4){
                                ab.setText(rows_list.get(i).get(j));

                                ab.setWidth(400);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(16);
                                ab.setHeight(115);
                            }
                            if(j==2){
                                ab.setText(rows_list.get(i).get(j));

                                ab.setWidth(400);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(16);
                                ab.setHeight(115);
                            }
                            else{
                                ab.setText(rows_list.get(i).get(j));
                                ab.setWidth(200);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(16);
                                ab.setHeight(115);
                            }


                            View vi=table.getChildAt(0);
                            TableRow ro = (TableRow) vi;
                            TextView item_code_boxx = (TextView) ro.getChildAt(j);
                            ab.setTag(rows_list.get(i).get(0)+":"+rows_list.get(i).get(1)+":"+item_code_boxx.getText().toString());

                            ab.setOnClickListener(change_ledger.this::onClick);










                            row1.addView(ab);

                        }
                        z++;



                        table.addView(row1);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

                  /* database.child("OutputSummary").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot node: snapshot.getChildren()){
                                String date=node.getKey();

                                if ((date.compareTo(datefrom) >= 0 && date.compareTo(dateto) <= 0) ||
                                        ((date.compareTo(datefrom) == 0) && (date.compareTo(dateto) == 0))) {
                                    //Log.d("hello"," date"+ date);
                                    for(DataSnapshot node1: node.getChildren()){
                                        if(node1.getKey().matches(ledger_itemcode)){
                                            //Log.d("ban", "ledger item: "+ ledger_itemcode);
                                            String itemcodeees = String.valueOf(node1.getKey());

                                            arrayList.clear();
                                            arrayList.add(date);
                                            arrayList.add(itemcodeees);

                                            for (DataSnapshot nodes1 : node1.getChildren()) {
                                                if(nodes1.getKey().equals("Data")){

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

                                                    //
                                                    //rows_list.clear();

                                                    if (rows_list.size()==1){

                                                        //rows_list.clear();
                                                        //Log.d("hello"," date array"+date);
                                                        rows_list.set(0,arrayList);
                                                        //rows_list.set(0,arrayList);
                                                        //rows_list.add(arrayList);

                                                    }
                                                    else{

                                                        rows_list.add(arrayList);
                                                    }
                                                    Log.d("hello0"," date"+rows_list.get(0).get(0));



                                                    int z = 0;
                                                    for (int i = 0; i <rows_list.size(); i++) {
                                                        row1 = new TableRow(getApplicationContext());
                                                        for (int j = 0; j < 15; j++) {
                                                            ab = new TextView(getApplicationContext());


                                                            if (j==9){

                                                                int sum =string_to_integer(rows_list.get(i).get(2))+
                                                                        string_to_integer(rows_list.get(i).get(3))+
                                                                        string_to_integer(rows_list.get(i).get(4))+
                                                                        string_to_integer(rows_list.get(i).get(5))+
                                                                        string_to_integer(rows_list.get(i).get(6))+
                                                                        string_to_integer(rows_list.get(i).get(7))+
                                                                        string_to_integer(rows_list.get(i).get(8));
                                                                ab.setText(sum+"");
                                                                ab.setWidth(200);
                                                                ab.setBackgroundResource(R.drawable.border_white);
                                                                ab.setTextColor(Color.BLACK);
                                                                ab.setGravity(Gravity.CENTER);
                                                                ab.setTextSize(16);
                                                                //   View vi=table.getChildAt(0);
                                                                //   TableRow ro = (TableRow) vi;
                                                                //  TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                //  ab.setTag(rows_list.get(0).get(0)+":"+item_code_boxx.getText().toString());

                                                                // ab.setOnClickListener(Stock.this::onClick);
                                                                ab.setHeight(115);


                                                            }
                                                            else{
                                                                if(j==0){

                                                                    ab.setText(rows_list.get(i).get(j));
                                                                    Log.d("hello"," date array"+rows_list.get(0).get(0));
                                                                }
                                                                if (j==1){
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

                                                                if(j>=10){
                                                                    //split(rows_list.get(i).get(j));
                                                                    ab.setText(""+split(rows_list.get(i).get(j)));
                                                                    ab.setWidth(200);
                                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                                    ab.setTextColor(Color.BLACK);
                                                                    ab.setGravity(Gravity.CENTER);
                                                                    //  ab.setOnClickListener(Stock.this::onClick);
                                                                    ab.setTextSize(16);
                                                                    // View vi=table.getChildAt(0);
                                                                    // TableRow ro = (TableRow) vi;
                                                                    // TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                    // ab.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                                                                    ab.setHeight(115);
                                                                }




                                                                else{

                                                                    ab.setText(rows_list.get(i).get(j));
                                                                    ab.setWidth(200);
                                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                                    ab.setTextColor(Color.BLACK);
                                                                    ab.setGravity(Gravity.CENTER);
                                                                    //  ab.setOnClickListener(Stock.this::onClick);
                                                                    ab.setTextSize(16);
                                                                    //  View vi=table.getChildAt(0);
                                                                    //  TableRow ro = (TableRow) vi;
                                                                    //  TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                    //  ab.setTag(rows_list.get(0).get(0)+":"+item_code_boxx.getText().toString());
                                                                    ab.setHeight(115);
                                                                }



                                                            }






                                                            row1.addView(ab);

                                                        }
                                                        z++;



                                                        table.addView(row1);
                                                    }

                                                    //Log.d("hello"," date array"+date);

                                                    //arrayList.clear();

                                                }


                                            }
                                        }
                                    }

                                }

                            }



                            //arrayList.clear();
                            //rows_list.clear();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/



            Log.d("TAG", "Count snap" + count + " " + snap);








        }


    }


    public void onClick(View v) {
        menu_array.clear();

        TextView vv=(TextView)v;
        TextView gg= findViewById(R.id.tv_change_ledger_tag);

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
    int string_to_integer(String s){

        if (s.matches("")){
            return 0;
        }
        else{
            return Integer.parseInt(s);
        }
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
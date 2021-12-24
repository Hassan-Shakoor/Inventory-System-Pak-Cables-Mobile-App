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
import android.widget.Button;
import android.widget.DatePicker;
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

public class newledger extends AppCompatActivity {
    DatabaseReference database;
    public ArrayList<Integer> core_data=new ArrayList<Integer>();
    DatabaseReference database1;
    ArrayList<Summary> summary;
    ArrayList<String> itemcodes;
    TextView ab;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<ArrayList<String>> rows_list = new ArrayList<ArrayList<String>>();
    String ledger_itemcode;
    ArrayList<Integer> menu_array=new ArrayList<Integer>();
    ArrayList<String> dates_list=new ArrayList<String>();
  public static int count_dates=0;

    int rows , snap , count;
    TableLayout table;
    TableRow row1;

    TextView DateFrom , DateTo;
    Button generate;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newledger);
        database = FirebaseDatabase.getInstance().getReference();

        Intent item=getIntent();
        ledger_itemcode=item.getStringExtra("ledgerItem");
        Log.d("ban", "itemcode: "+ledger_itemcode);
        database1 = FirebaseDatabase.getInstance().getReference();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String millisInString = dateFormat.format(new Date());
        summary = new ArrayList<Summary>();
        itemcodes = new ArrayList<String>();
        rows = 0;
        snap = 0;
        count = 0;

        table = findViewById(R.id.new_ledger_table);
        row1 = new TableRow(this);

        DateFrom = findViewById(R.id.ledger_from);
        DateTo = findViewById(R.id.ledger_to);

        generate = findViewById(R.id.ledger_generate);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_PORTRAIT){
            DateFrom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            newledger.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            });
            setListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                    month = month + 1;
                    String m = "", day = "";
                    if (month < 10) {
                        m = "0" + month;
                    }
                    if (dayofMonth < 10) {
                        day = "0" + dayofMonth;
                    }
                    if (month >= 10) {
                        m = "" + month;
                    }
                    if (dayofMonth >= 10) {
                        day = "" + dayofMonth;
                    }

                    String date = day + "-" + m + "-" + year;
                    DateFrom.setText(date);
                    Log.d("zeesh", "date" + date + "day: " + day);
                }
            };
            DateTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            newledger.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month + 1;
                            String m = "", d = "";
                            if (month < 10) {
                                m = "0" + month;
                            }
                            if (day < 10) {
                                d = "0" + day;
                            }
                            if (month >= 10) {
                                m = "" + month;
                            }
                            if (day >= 10) {
                                d = "" + day;
                            }
                            String date = d + "-" + m + "-" + year;
                            DateTo.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            });



            generate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (table.getChildCount() != 0) {
                        Log.d("table", "TABLE");
                        for (int i = 0; i < table.getChildCount(); i++) {
                            table.removeAllViews();
                        }

                    }
                    TableRow row = new TableRow(newledger.this);
                    TextView tv0 = new TextView(getApplicationContext());
                    tv0.setText(" Date");
                    tv0.setWidth(300);
                    tv0.setBackgroundResource(R.drawable.border_item);
                    tv0.setTextColor(Color.BLACK);
                    tv0.setGravity(Gravity.CENTER);
                    tv0.setTextSize(20);

                    row.addView(tv0);


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
                    //Log.d("TAG", "Summary = " + summary.toString());
                    table.addView(row);









                    ////
                    //arrayList.clear();
                    String datefrom = DateFrom.getText().toString();
                    Log.d("zeesh", "datefrommmm: " + datefrom);
                    String dateto = DateTo.getText().toString();
                    database1.child("OutputSummary").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot node: snapshot.getChildren()){
                                String date = node.getKey();
                                if ((date.compareTo(datefrom) >= 0 && date.compareTo(dateto) <= 0) ||
                                        ((date.compareTo(datefrom) == 0) && (date.compareTo(dateto) == 0))) {
                                    for(DataSnapshot node1: node.getChildren()){
                                        count_dates+=1;
                                    }
                                }
                            }
                            Log.d("ban", "count:"+ count_dates);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    database.child("OutputSummary").addValueEventListener(new ValueEventListener() {
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
                    });



                    Log.d("TAG", "Count snap" + count + " " + snap);


                }
            });


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
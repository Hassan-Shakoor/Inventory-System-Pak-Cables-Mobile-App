package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ledger extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference database;
    ArrayList<Summary> summary;
    ArrayList<String> itemcodes;
    public new_input_class col_0_obj1;
    ArrayList<Integer> menu_array=new ArrayList<Integer>();
    ArrayList<Integer> menu_array2=new ArrayList<Integer>();
    ArrayList<String> date_list  = new ArrayList<>();

    int rows , snap , count;
    TableLayout table;
    TableRow row1;

    TextView DateFrom , DateTo;
    Button generate;
    String final_date;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger);
        Intent intent=getIntent();
        String final_date=intent.getStringExtra("date");
        Log.d("lasttt","intent: "+final_date);

        database = FirebaseDatabase.getInstance().getReference();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String millisInString = dateFormat.format(new Date());
        summary = new ArrayList<Summary>();
        itemcodes = new ArrayList<String>();
        rows = 0;
        snap = 0;
        count = 0;

        table = findViewById(R.id.ledger_table);
        row1 = new TableRow(this);



        getSupportActionBar().setTitle("STOCK");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatabaseReference newroot = FirebaseDatabase.getInstance().getReference().child("StockFull");
        newroot.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot nodes : snapshot.getChildren()) {
                        Log.d("tag", "new node:" + nodes.getKey());
                        String n = nodes.getKey().replace("-", "/");
                        n = n.replace("o", ".");


                        n=n.replace('h','#');
                        n=n.replace('d','$');
                        n=n.replace('s','[');

                        n=n.replace('e',']');
                        n=n.replace('t','*');


                        for (DataSnapshot nodes1 : nodes.getChildren()) {
                            if (nodes1.getKey().equals("Data")) {


                                col_0_obj1 = nodes1.getValue(new_input_class.class);
                                col_0_obj1.setCol0(n.toString());

                            }
                        }

                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_PORTRAIT){


            DatabaseReference date_root = FirebaseDatabase.getInstance().getReference().child("Stock");
            date_root.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot data : snapshot.getChildren()) {

                            String datee = data.getKey();
                            Log.d("hash", "date: " + datee);
                            date_list.add(datee);

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
                    Log.d("lasttt","date list: "+date_list.get(0));
                    Log.d("lasttt","final date: "+final_date);

                    database.child("Stock").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            summary.clear();
                            count = 0;
                            snap = 0;
                            rows = 0;


                            for (DataSnapshot data : snapshot.getChildren()) {
                                Log.d("TAG", "outer count = " + count);
                                String date = data.getKey();

                                //Log.d("osamag", "DATE = " + date);




                                if ((date.compareTo(date_list.get(0)) >= 0 && date.compareTo(final_date) <= 0) ||
                                        ((date.compareTo( date_list.get(0)) == 0) && (date.compareTo(final_date) == 0))) {


                                    FirebaseDatabase.getInstance().getReference().child("OutputSummary").child(date).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            snap += snapshot.getChildrenCount();
                                            for (DataSnapshot data : snapshot.getChildren()) {
                                                Log.d("SNAPSHOT", "" + snapshot.getChildrenCount());

                                                String itemcode = data.getKey();
                                                String n = itemcode.replace("-", "/");
                                                n = n.replace("o", ".");


                                                n=n.replace('h','#');
                                                n=n.replace('d','$');
                                                n=n.replace('s','[');

                                                n=n.replace('e',']');
                                                n=n.replace('t','*');
                                                if(itemcodes.contains(n)!=true){
                                                    itemcodes.add(n);
                                                }
                                                for (int i=0; i< itemcodes.size();i++){
                                                    Log.d("mtag","checking: "+itemcodes);
                                                }


                                                Log.d("TAG", "ItemCode = " + itemcode);

                                                FirebaseDatabase.getInstance().getReference().child("Stock").child(date).child(itemcode).child("Data").addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        count++;
                                                        stock_data Data = snapshot.getValue(stock_data.class);
                                                        //  Log.d("TAG", "Stock Data = " + itemcode + " " + Data.getCol1() + " " + Data.getCol2());
                                                        summary.add(new Summary(itemcode, Data));
                                                        Log.d("TAG", "Summary 149 = " + summary);
                                                        Log.d("CHECK", "Count = " + count + " " + snap);
                                                        if (count == snap) {

                                                            database.child("Stock").child(date).addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int summm = 0;
                                                                    for (DataSnapshot data : snapshot.getChildren()) {
                                                                        String item = data.getKey();
                                                                        String n = item.replace("-", "/");
                                                                        n = n.replace("o", ".");


                                                                        n=n.replace('h','#');
                                                                        n=n.replace('d','$');
                                                                        n=n.replace('s','[');

                                                                        n=n.replace('e',']');
                                                                        n=n.replace('t','*');

                                                                        itemcodes.add(n);
                                                                        Log.d("TAG", "Stock Item = " + item);
                                                                        rows += 1;
                                                                    }
                                                                    Log.d("TAG", "Rows = " + rows);

                                                                    TableRow row = new TableRow(ledger.this);
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
                                                                    Log.d("TAG", "Summary = " + summary.toString());
                                                                    table.addView(row);
                                                                    //if (summary != null){
                                                                    for (int i = 0; i < rows; i++) {
                                                                        int sum = 0;
                                                                        String num ="";
                                                                        String num10="";
                                                                        String num11 ="";
                                                                        String num12 ="";
                                                                        String num13 ="";
                                                                        row1 = new TableRow(getApplicationContext());
                                                                        for (int j = 0; j < 14; j++) {
                                                                            TextView textView = new TextView(getApplicationContext());
                                                                            // hsh - data -> itemnew    // hsh
                                                                            for (int k = 0; k < summary.size(); k++) {
                                                                                Log.d("TAG", "itemcode loop executed");
                                                                                if (itemcodes.get(i).equals(summary.get(k).getItemcode())) {
                                                                                    Log.d("LOG", itemcodes.get(i) + " " + summary.get(k).getItemcode());

                                                                                    if (!(summary.get(k) == null)) {
                                                                                        switch (j) {
                                                                                            case 1:

                                                                                                if (summary.get(k).getData().getCol1().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol1());
                                                                                                break;
                                                                                            case 2:
                                                                                                if (summary.get(k).getData().getCol2().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol2());
                                                                                                break;
                                                                                            case 3:
                                                                                                if (summary.get(k).getData().getCol3().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol3());
                                                                                                break;
                                                                                            case 4:
                                                                                                if (summary.get(k).getData().getCol4().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol4());
                                                                                                break;
                                                                                            case 5:
                                                                                                if (summary.get(k).getData().getCol5().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol5());
                                                                                                break;
                                                                                            case 6:
                                                                                                if (summary.get(k).getData().getCol6().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol6());
                                                                                                break;
                                                                                            case 7:
                                                                                                if (summary.get(k).getData().getCol7().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol7());
                                                                                                break;
                                                                                            case 8:

                                                                                                summm = string_to_integer(summary.get(k).getData().getCol7()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol7()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol6()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol5()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol4()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol3()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol2()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol1());


                                                                                                sum += summm;


                                                                                                break;
                                                                                            case 9:

                                                                                                if (summary.get(k).getData().getCol9().equals("") == false)


                                                                                                    num=num+"+"+summary.get(k).getData().getCol9();



                                                                                                break;
                                                                                            case 10:
                                                                                                if (summary.get(k).getData().getCol10().equals("") == false)
                                                                                                    num10=num10+"+"+summary.get(k).getData().getCol10();
                                                                                                break;
                                                                                            case 11:
                                                                                                if (summary.get(k).getData().getCol11().equals("") == false)
                                                                                                    num11=num11+"+"+summary.get(k).getData().getCol11();
                                                                                                break;
                                                                                            case 12:
                                                                                                if (summary.get(k).getData().getCol12().equals("") == false)
                                                                                                    num12=num12+"+"+summary.get(k).getData().getCol12();
                                                                                                break;
                                                                                            case 13:
                                                                                                if (summary.get(k).getData().getCol13().equals("") == false)
                                                                                                    num13=num13+"+"+summary.get(k).getData().getCol13();
                                                                                                break;

                                                                                        }
                                                                                    }
                                                                                }
                                                                                //Log.d("TAG", "Sum inner = " + sum);
                                                                            }
                                                                            //Log.d("TAG", "Sum = " + itemcodes.get(i) + " " + sum);

                                                                            if (j == 0) {
                                                                                textView.setText(itemcodes.get(i));
                                                                                //textView.setTag(itemcodes.get(i));
                                                                                textView.setWidth(300);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setTextSize(16);
                                                                                textView.setHeight(115);


                                                                            } else {
                                                                                textView.setText("" + sum);
                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setTextSize(16);
                                                                                textView.setHeight(115);
                                                                                View vi = table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString());
                                                                                textView.setOnClickListener(ledger.this::onClick);


                                                                            }
                                                                            if (j == 8) {
                                                                                textView.setText("" + summm);
                                                                                textView.setTag(itemcodes.get(i));
                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setTextSize(16);
                                                                                textView.setHeight(115);

                                                                                View vi = table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString());
                                                                                //ab.setTag(rows_list.get(0).get(0)+":"+item_code_boxx.getText().toString());


                                                                                textView.setOnClickListener(ledger.this::onClick);
                                                                            }
                                                                            if(j==9){
                                                                                menu_array.clear();

                                                                                String number="";
                                                                                int output=0;
                                                                                //split(rows_list.get(i).get(j));

                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setOnClickListener(ledger.this::onClick);
                                                                                textView.setTextSize(16);
                                                                                View vi=table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
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

                                                                                textView.setText(String.valueOf(output));
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString()+":"+num);
                                                                                // textView.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                                                                                textView.setHeight(115);
                                                                            }
                                                                            if(j==10){
                                                                                menu_array.clear();
                                                                                String number="";
                                                                                int output=0;
                                                                                //split(rows_list.get(i).get(j));

                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setOnClickListener(ledger.this::onClick);
                                                                                textView.setTextSize(16);
                                                                                View vi=table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
                                                                                for (int a=0;a<num10.length();a++){
                                                                                    Character value=num10.charAt(a);
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

                                                                                textView.setText(String.valueOf(output));
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString()+":"+num10);
                                                                                // textView.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                                                                                textView.setHeight(115);
                                                                            }
                                                                            if(j==11){
                                                                                menu_array.clear();
                                                                                String number="";
                                                                                int output=0;
                                                                                //split(rows_list.get(i).get(j));

                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setOnClickListener(ledger.this::onClick);
                                                                                textView.setTextSize(16);
                                                                                View vi=table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
                                                                                for (int a=0;a<num11.length();a++){
                                                                                    Character value=num11.charAt(a);
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

                                                                                textView.setText(String.valueOf(output));
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString()+":"+num11);
                                                                                // textView.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                                                                                textView.setHeight(115);
                                                                            }
                                                                            if(j==12){
                                                                                menu_array.clear();
                                                                                String number="";
                                                                                int output=0;
                                                                                //split(rows_list.get(i).get(j));

                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setOnClickListener(ledger.this::onClick);
                                                                                textView.setTextSize(16);
                                                                                View vi=table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
                                                                                for (int a=0;a<num12.length();a++){
                                                                                    Character value=num12.charAt(a);
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

                                                                                textView.setText(String.valueOf(output));
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString()+":"+num12);
                                                                                // textView.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                                                                                textView.setHeight(115);
                                                                            }
                                                                            if (j ==13) {
                                                                                menu_array.clear();
                                                                                String number="";
                                                                                int output=0;
                                                                                //split(rows_list.get(i).get(j));

                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setOnClickListener(ledger.this::onClick);
                                                                                textView.setTextSize(16);
                                                                                View vi=table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
                                                                                for (int a=0;a<num13.length();a++){
                                                                                    Character value=num13.charAt(a);
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

                                                                                textView.setText(String.valueOf(output));
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString()+":"+num13);
                                                                                // textView.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                                                                                textView.setHeight(115);
                                                                            }

                                                                            row1.addView(textView);
                                                                            sum = 0;
                                                                        }
                                                                        //Log.d("CHECK" , "running.....");
                                                                        table.addView(row1);

                                                                    }
                                                                    //}

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {
                                                                }
                                                            });
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }

                                    });
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





        }

        if (orientation1 == Configuration.ORIENTATION_LANDSCAPE){


            DatabaseReference date_root = FirebaseDatabase.getInstance().getReference().child("Stock");
            date_root.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot data : snapshot.getChildren()) {

                            String datee = data.getKey();
                            Log.d("hash", "date: " + datee);
                            date_list.add(datee);

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
                    Log.d("lasttt","date list: "+date_list.get(0));
                    Log.d("lasttt","final date: "+final_date);

                    database.child("Stock").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            summary.clear();
                            count = 0;
                            snap = 0;
                            rows = 0;


                            for (DataSnapshot data : snapshot.getChildren()) {
                                Log.d("TAG", "outer count = " + count);
                                String date = data.getKey();

                                //Log.d("osamag", "DATE = " + date);




                                if ((date.compareTo(date_list.get(0)) >= 0 && date.compareTo(final_date) <= 0) ||
                                        ((date.compareTo( date_list.get(0)) == 0) && (date.compareTo(final_date) == 0))) {


                                    FirebaseDatabase.getInstance().getReference().child("OutputSummary").child(date).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            snap += snapshot.getChildrenCount();
                                            for (DataSnapshot data : snapshot.getChildren()) {
                                                Log.d("SNAPSHOT", "" + snapshot.getChildrenCount());

                                                String itemcode = data.getKey();
                                                String n = itemcode.replace("-", "/");
                                                n = n.replace("o", ".");


                                                n=n.replace('h','#');
                                                n=n.replace('d','$');
                                                n=n.replace('s','[');

                                                n=n.replace('e',']');
                                                n=n.replace('t','*');
                                                if(itemcodes.contains(n)!=true){
                                                    itemcodes.add(n);
                                                }
                                                for (int i=0; i< itemcodes.size();i++){
                                                    Log.d("mtag","checking: "+itemcodes);
                                                }


                                                Log.d("TAG", "ItemCode = " + itemcode);

                                                FirebaseDatabase.getInstance().getReference().child("Stock").child(date).child(itemcode).child("Data").addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        count++;
                                                        stock_data Data = snapshot.getValue(stock_data.class);
                                                        //  Log.d("TAG", "Stock Data = " + itemcode + " " + Data.getCol1() + " " + Data.getCol2());
                                                        summary.add(new Summary(itemcode, Data));
                                                        Log.d("TAG", "Summary 149 = " + summary);
                                                        Log.d("CHECK", "Count = " + count + " " + snap);
                                                        if (count == snap) {

                                                            database.child("Stock").child(date).addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int summm = 0;
                                                                    for (DataSnapshot data : snapshot.getChildren()) {
                                                                        String item = data.getKey();
                                                                        String n = item.replace("-", "/");
                                                                        n = n.replace("o", ".");


                                                                        n=n.replace('h','#');
                                                                        n=n.replace('d','$');
                                                                        n=n.replace('s','[');

                                                                        n=n.replace('e',']');
                                                                        n=n.replace('t','*');

                                                                        itemcodes.add(n);
                                                                        Log.d("TAG", "Stock Item = " + item);
                                                                        rows += 1;
                                                                    }
                                                                    Log.d("TAG", "Rows = " + rows);

                                                                    TableRow row = new TableRow(ledger.this);
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
                                                                    Log.d("TAG", "Summary = " + summary.toString());
                                                                    table.addView(row);
                                                                    //if (summary != null){
                                                                    for (int i = 0; i < rows; i++) {
                                                                        int sum = 0;
                                                                        String num ="";
                                                                        String num10="";
                                                                        String num11 ="";
                                                                        String num12 ="";
                                                                        String num13 ="";
                                                                        row1 = new TableRow(getApplicationContext());
                                                                        for (int j = 0; j < 14; j++) {
                                                                            TextView textView = new TextView(getApplicationContext());
                                                                            // hsh - data -> itemnew    // hsh
                                                                            for (int k = 0; k < summary.size(); k++) {
                                                                                Log.d("TAG", "itemcode loop executed");
                                                                                if (itemcodes.get(i).equals(summary.get(k).getItemcode())) {
                                                                                    Log.d("LOG", itemcodes.get(i) + " " + summary.get(k).getItemcode());

                                                                                    if (!(summary.get(k) == null)) {
                                                                                        switch (j) {
                                                                                            case 1:

                                                                                                if (summary.get(k).getData().getCol1().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol1());
                                                                                                break;
                                                                                            case 2:
                                                                                                if (summary.get(k).getData().getCol2().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol2());
                                                                                                break;
                                                                                            case 3:
                                                                                                if (summary.get(k).getData().getCol3().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol3());
                                                                                                break;
                                                                                            case 4:
                                                                                                if (summary.get(k).getData().getCol4().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol4());
                                                                                                break;
                                                                                            case 5:
                                                                                                if (summary.get(k).getData().getCol5().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol5());
                                                                                                break;
                                                                                            case 6:
                                                                                                if (summary.get(k).getData().getCol6().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol6());
                                                                                                break;
                                                                                            case 7:
                                                                                                if (summary.get(k).getData().getCol7().equals("") == false)
                                                                                                    sum += Integer.parseInt(summary.get(k).getData().getCol7());
                                                                                                break;
                                                                                            case 8:

                                                                                                summm = string_to_integer(summary.get(k).getData().getCol7()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol7()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol6()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol5()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol4()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol3()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol2()) +
                                                                                                        string_to_integer(summary.get(k).getData().getCol1());


                                                                                                sum += summm;


                                                                                                break;
                                                                                            case 9:

                                                                                                if (summary.get(k).getData().getCol9().equals("") == false)


                                                                                                    num=num+"+"+summary.get(k).getData().getCol9();



                                                                                                break;
                                                                                            case 10:
                                                                                                if (summary.get(k).getData().getCol10().equals("") == false)
                                                                                                    num10=num10+"+"+summary.get(k).getData().getCol10();
                                                                                                break;
                                                                                            case 11:
                                                                                                if (summary.get(k).getData().getCol11().equals("") == false)
                                                                                                    num11=num11+"+"+summary.get(k).getData().getCol11();
                                                                                                break;
                                                                                            case 12:
                                                                                                if (summary.get(k).getData().getCol12().equals("") == false)
                                                                                                    num12=num12+"+"+summary.get(k).getData().getCol12();
                                                                                                break;
                                                                                            case 13:
                                                                                                if (summary.get(k).getData().getCol13().equals("") == false)
                                                                                                    num13=num13+"+"+summary.get(k).getData().getCol13();
                                                                                                break;

                                                                                        }
                                                                                    }
                                                                                }
                                                                                //Log.d("TAG", "Sum inner = " + sum);
                                                                            }
                                                                            //Log.d("TAG", "Sum = " + itemcodes.get(i) + " " + sum);

                                                                            if (j == 0) {
                                                                                textView.setText(itemcodes.get(i));
                                                                                //textView.setTag(itemcodes.get(i));
                                                                                textView.setWidth(300);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setTextSize(16);
                                                                                textView.setHeight(115);


                                                                            } else {
                                                                                textView.setText("" + sum);
                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setTextSize(16);
                                                                                textView.setHeight(115);
                                                                                View vi = table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString());
                                                                                textView.setOnClickListener(ledger.this::onClick);


                                                                            }
                                                                            if (j == 8) {
                                                                                textView.setText("" + summm);
                                                                                textView.setTag(itemcodes.get(i));
                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setTextSize(16);
                                                                                textView.setHeight(115);

                                                                                View vi = table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString());
                                                                                //ab.setTag(rows_list.get(0).get(0)+":"+item_code_boxx.getText().toString());


                                                                                textView.setOnClickListener(ledger.this::onClick);
                                                                            }
                                                                            if(j==9){
                                                                                menu_array.clear();

                                                                                String number="";
                                                                                int output=0;
                                                                                //split(rows_list.get(i).get(j));

                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setOnClickListener(ledger.this::onClick);
                                                                                textView.setTextSize(16);
                                                                                View vi=table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
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

                                                                                textView.setText(String.valueOf(output));
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString()+":"+num);
                                                                                // textView.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                                                                                textView.setHeight(115);
                                                                            }
                                                                            if(j==10){
                                                                                menu_array.clear();
                                                                                String number="";
                                                                                int output=0;
                                                                                //split(rows_list.get(i).get(j));

                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setOnClickListener(ledger.this::onClick);
                                                                                textView.setTextSize(16);
                                                                                View vi=table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
                                                                                for (int a=0;a<num10.length();a++){
                                                                                    Character value=num10.charAt(a);
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

                                                                                textView.setText(String.valueOf(output));
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString()+":"+num10);
                                                                                // textView.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                                                                                textView.setHeight(115);
                                                                            }
                                                                            if(j==11){
                                                                                menu_array.clear();
                                                                                String number="";
                                                                                int output=0;
                                                                                //split(rows_list.get(i).get(j));

                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setOnClickListener(ledger.this::onClick);
                                                                                textView.setTextSize(16);
                                                                                View vi=table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
                                                                                for (int a=0;a<num11.length();a++){
                                                                                    Character value=num11.charAt(a);
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

                                                                                textView.setText(String.valueOf(output));
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString()+":"+num11);
                                                                                // textView.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                                                                                textView.setHeight(115);
                                                                            }
                                                                            if(j==12){
                                                                                menu_array.clear();
                                                                                String number="";
                                                                                int output=0;
                                                                                //split(rows_list.get(i).get(j));

                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setOnClickListener(ledger.this::onClick);
                                                                                textView.setTextSize(16);
                                                                                View vi=table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
                                                                                for (int a=0;a<num12.length();a++){
                                                                                    Character value=num12.charAt(a);
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

                                                                                textView.setText(String.valueOf(output));
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString()+":"+num12);
                                                                                // textView.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                                                                                textView.setHeight(115);
                                                                            }
                                                                            if (j ==13) {
                                                                                menu_array.clear();
                                                                                String number="";
                                                                                int output=0;
                                                                                //split(rows_list.get(i).get(j));

                                                                                textView.setWidth(200);
                                                                                textView.setBackgroundResource(R.drawable.border_white);
                                                                                textView.setTextColor(Color.BLACK);
                                                                                textView.setGravity(Gravity.CENTER);
                                                                                textView.setOnClickListener(ledger.this::onClick);
                                                                                textView.setTextSize(16);
                                                                                View vi=table.getChildAt(0);
                                                                                TableRow ro = (TableRow) vi;
                                                                                for (int a=0;a<num13.length();a++){
                                                                                    Character value=num13.charAt(a);
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

                                                                                textView.setText(String.valueOf(output));
                                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                                textView.setTag(itemcodes.get(i) + ":" + item_code_boxx.getText().toString()+":"+num13);
                                                                                // textView.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                                                                                textView.setHeight(115);
                                                                            }

                                                                            row1.addView(textView);
                                                                            sum = 0;
                                                                        }
                                                                        //Log.d("CHECK" , "running.....");
                                                                        table.addView(row1);

                                                                    }
                                                                    //}

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {
                                                                }
                                                            });
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }

                                    });
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


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {

        menu_array.clear();
        PopupMenu menu = new PopupMenu(getApplicationContext(), v);
        menu.getMenu().clear();



        TextView vv=(TextView) v;

        String a= vv.getTag().toString();
        String [] split_string_list=a.split(":");
        String aaa=split_string_list[split_string_list.length-1];


        String number="";
        if(aaa.contains("90")||aaa.contains("180")){



            for (int i=0;i<aaa.length();i++){
                Character value=aaa.charAt(i);

                if("0123456789".indexOf(value) != -1){
                    number+=value;
                }
                else{
                    if(number.equals("")!=true){
                        menu_array.add(Integer.valueOf(number));
                    }


                    number="";
                }

            }
            if(number.equals("")!=true){
                menu_array.add(Integer.valueOf(number));
            }





            for(int x=0; x< menu_array.size(); x++){
                menu.getMenu().add(String.valueOf(menu_array.get(x)));
            }


            menu.show();}
        TextView gg=findViewById(R.id.ledger_textview);
        gg.setText(vv.getTag().toString());


    }
}
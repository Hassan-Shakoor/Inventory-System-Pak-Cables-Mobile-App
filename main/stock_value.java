package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
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

import android.widget.Toast;

public class stock_value extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> date  = new ArrayList<>();
    ArrayList<Integer> menu_array=new ArrayList<Integer>();
    ArrayList<String> arrayList2 = new ArrayList<>();
    ArrayList<ArrayList<String>> rows_list = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> rows_list2 = new ArrayList<ArrayList<String>>();
    ProgressDialog progressDialog2;
    DatabaseReference root;
    String last_date;
    int column=0;
    public new_input_class col_0_obj;
    public new_input_class col_0_obj1;
    public int height=90;
    public int font=11;

    public TableRow row;
    TableLayout table;
    TextView ab;
    public TableRow row1;
    public stock_data obj;
    int countt=1;
    int summ=0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseReference.goOffline();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DatabaseReference.goOffline();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_value);
        getSupportActionBar().setTitle("Stock Value");
        progressDialog2 = new ProgressDialog(this);
        progressDialog2.setMessage("Loading"); // Setting Message
        progressDialog2.setTitle("Stock Value"); // Setting Title
        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog2.setCanceledOnTouchOutside(false);
        progressDialog2.show();



        table = findViewById(R.id.stock_value_table);
        row = new TableRow(this);
        row.setBaselineAligned(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String millisInString  = dateFormat.format(new Date());


        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            TextView tv = new TextView(getApplicationContext());
            tv.setText(" Item Code ");
            tv.setWidth(200);
            tv.setBackgroundResource(R.drawable.border_item);
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(font);

            row.addView(tv);

            TextView tv1 = new TextView(getApplicationContext());
            tv1.setText(" Red ");
            tv1.setWidth(120);
            tv1.setBackgroundResource(R.drawable.border_red);
            tv1.setTextColor(Color.parseColor("#D1C7C7"));
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextSize(font);
            row.addView(tv1);

            TextView tv2 = new TextView(getApplicationContext());
            tv2.setText(" Black ");
            tv2.setWidth(120);
            tv2.setBackgroundResource(R.drawable.border_black);
            tv2.setTextColor(Color.parseColor("#7CB342"));
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextSize(font);
            //tv2.setBackgroundColor(Color.parseColor("#050505"));
            row.addView(tv2);

            TextView tv3 = new TextView(getApplicationContext());
            tv3.setText(" Yellow ");
            tv3.setWidth(120);
            tv3.setBackgroundResource(R.drawable.border_yellow);
            tv3.setTextColor(Color.BLACK);
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextSize(font);
            //tv3.setBackgroundColor(Color.parseColor("#FDD835"));
            row.addView(tv3);

            TextView tv4 = new TextView(getApplicationContext());
            tv4.setText(" Blue ");
            tv4.setWidth(120);
            tv4.setBackgroundResource(R.drawable.border_blue);
            tv4.setTextColor(Color.BLACK);
            tv4.setGravity(Gravity.CENTER);
            tv4.setTextSize(font);

            row.addView(tv4);

            TextView tv5 = new TextView(getApplicationContext());
            tv5.setText(" Green ");
            tv5.setWidth(120);
            tv5.setBackgroundResource(R.drawable.border_green);
            tv5.setTextColor(Color.BLACK);
            tv5.setGravity(Gravity.CENTER);
            tv5.setTextSize(font);
            //tv5.setBackgroundColor(Color.parseColor("#43A047"));
            row.addView(tv5);

            TextView tv6 = new TextView(getApplicationContext());
            tv6.setText(" White ");
            tv6.setWidth(120);
            tv6.setBackgroundResource(R.drawable.border_white);
            tv6.setTextColor(Color.BLACK);
            tv6.setGravity(Gravity.CENTER);
            tv6.setTextSize(font);
            //tv6.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv6);

            TextView tv7 = new TextView(getApplicationContext());
            tv7.setText(" Other ");
            tv7.setWidth(120);
            tv7.setBackgroundResource(R.drawable.border_other);
            tv7.setTextColor(Color.BLACK);
            tv7.setGravity(Gravity.CENTER);
            tv7.setTextSize(font);
            //tv7.setBackgroundColor(Color.parseColor("#DBDF9C"));
            row.addView(tv7);

            TextView tv8 = new TextView(getApplicationContext());
            tv8.setText(" Total ");
            tv8.setWidth(120);
            tv8.setBackgroundResource(R.drawable.border_white);
            tv8.setTextColor(Color.BLACK);
            tv8.setGravity(Gravity.CENTER);
            tv8.setTextSize(font);
            //tv8.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv8);

            TextView tv9 = new TextView(getApplicationContext());
            tv9.setText(" 2 Core ");
            tv9.setWidth(120);
            tv9.setBackgroundResource(R.drawable.border_white);
            tv9.setTextColor(Color.BLACK);
            tv9.setGravity(Gravity.CENTER);
            tv9.setTextSize(font);
            //tv9.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv9);

            TextView tv10 = new TextView(getApplicationContext());
            tv10.setText(" 3 Core ");
            tv10.setWidth(120);
            tv10.setBackgroundResource(R.drawable.border_white);
            tv10.setTextColor(Color.BLACK);
            tv10.setGravity(Gravity.CENTER);
            tv10.setTextSize(font);
            //tv10.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv10);

            TextView tv11 = new TextView(getApplicationContext());
            tv11.setText(" 4 Core ");
            tv11.setWidth(120);
            tv11.setBackgroundResource(R.drawable.border_white);
            tv11.setTextColor(Color.BLACK);
            tv11.setGravity(Gravity.CENTER);
            tv11.setTextSize(font);
            //tv11.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv11);

            TextView tv12 = new TextView(getApplicationContext());
            tv12.setText(" 5 Core ");
            tv12.setWidth(120);
            tv12.setBackgroundResource(R.drawable.border_white);
            tv12.setTextColor(Color.BLACK);
            tv12.setGravity(Gravity.CENTER);
            tv12.setTextSize(font);
            //tv12.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv12);

            TextView tv13 = new TextView(getApplicationContext());
            tv13.setText(" 6 Core ");
            tv13.setWidth(160);
            tv13.setBackgroundResource(R.drawable.border_white);
            tv13.setTextColor(Color.BLACK);
            tv13.setGravity(Gravity.CENTER);
            tv13.setTextSize(font);
            //tv13.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv13);


            table.addView(row);
            DatabaseReference date_root = FirebaseDatabase.getInstance().getReference().child("Stock");
            date_root.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot data : snapshot.getChildren()) {

                            String datee = data.getKey();
                            Log.d("hash", "date: " + datee);
                            date.add(datee);

                        }
                    }
                    Collections.sort(date, new Comparator<String>() {
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
                    if (date.size() > 0) {


                        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Stock").child(String.valueOf(date.get(date.size() - 1)));
                        root.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                if (snapshot.exists()) {

                                    for (DataSnapshot nodes : snapshot.getChildren()) {
                                        String itemcodeees = String.valueOf(nodes.getKey());




                                        ///
                                        String n = itemcodeees  .replace("slash", "/");
                                        n = n.replace("dot", ".");

                                        n=n.replace("hash","#");
                                        n=n.replace("dollar","$");
                                        n=n.replace("openbrace","[");

                                        n=n.replace("closebrace","]");
                                        n=n.replace("star","*");

                                        arrayList.add(n);

                                        for (DataSnapshot nodes1 : nodes.getChildren()) {
                                            if (nodes1.getKey().equals("Data")) {

                                                obj = nodes1.getValue(stock_data.class);
                                                //arrayList.add(itemcode);

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

                                        int z = 0;
                                        for (int i = 0; i < rows_list.size(); i++) {
                                            row1 = new TableRow(getApplicationContext());
                                            row1.setBaselineAligned(false);

                                            for (int j = 0; j < 14; j++) {
                                                ab = new TextView(getApplicationContext());

                                                if (j == 8) {

                                                    ///
                                                    int sum = string_to_integer(rows_list.get(i).get(1)) +
                                                            string_to_integer(rows_list.get(i).get(2)) +
                                                            string_to_integer(rows_list.get(i).get(3)) +
                                                            string_to_integer(rows_list.get(i).get(4)) +
                                                            string_to_integer(rows_list.get(i).get(5)) +
                                                            string_to_integer(rows_list.get(i).get(6)) +
                                                            string_to_integer(rows_list.get(i).get(7));
                                                    ab.setText(sum + "");
                                                    ab.setWidth(120);
                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                    ab.setTextColor(Color.BLACK);
                                                    ab.setGravity(Gravity.CENTER);
                                                    ab.setTextSize(font);
                                                    ab.setHeight(height);
                                                    ab.setOnClickListener(stock_value.this::onClick);
                                                    ab.setTag(rows_list.get(0).get(0));
                                                }
                                                if (j == 0) {
                                                    ab.setText(rows_list.get(i).get(j));
                                                    ab.setWidth(200);
                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                    ab.setTextColor(Color.BLACK);
                                                    ab.setGravity(Gravity.CENTER);
                                                    ab.setTextSize(font);
                                                    ab.setHeight(height);
                                                    ab.setTypeface(Typeface.DEFAULT_BOLD);

                                                }
                                                if(j==13){
                                                    ab.setText(rows_list.get(i).get(j));
                                                    ab.setWidth(160);
                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                    ab.setTextColor(Color.BLACK);
                                                    ab.setGravity(Gravity.CENTER);
                                                    ab.setTextSize(font);
                                                    ab.setHeight(height);
                                                    ab.setTypeface(Typeface.DEFAULT_BOLD);


                                                }

                                                else {
                                                    ab.setText(rows_list.get(i).get(j));
                                                    ab.setWidth(120);
                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                    ab.setTextColor(Color.BLACK);
                                                    ab.setGravity(Gravity.CENTER);
                                                    ab.setTextSize(font);
                                                    ab.setTag(rows_list.get(0).get(0));
                                                    ab.setOnClickListener(stock_value.this::onClick);
                                                    ab.setHeight(height);
                                                }


                                                row1.addView(ab);

                                            }
                                            z++;


                                            table.addView(row1);
                                        }
                                        arrayList.clear();
                                        rows_list.clear();

                                    }
                                    if (snapshot.getChildrenCount() != 1) {
                                        countt += 1;
                                    }

                                    Log.d("hash", "outside if");
                                    Log.d("hash", "COUNT:" + countt);
                                    Log.d("hash", "snapshot: " + snapshot.getChildrenCount());
///////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    if (countt == snapshot.getChildrenCount()) {
                                        Log.d("hash", "inside if");
                                        DatabaseReference roott = FirebaseDatabase.getInstance().getReference().child("ItemRate");
                                        roott.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot nodes : snapshot.getChildren()) {
                                                    String itemcodeees = String.valueOf(nodes.getKey());

                                                    arrayList2.add(itemcodeees);

                                                    for (DataSnapshot nodes1 : nodes.getChildren()) {
                                                        if (nodes1.getKey().equals("Rate")) {

                                                            obj = nodes1.getValue(stock_data.class);
                                                            //arrayList.add(itemcode);

                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol1());
                                                            Log.d("hash", "value: " + nodes1.getValue(Tablerow.class).getCol1());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol2());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol3());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol4());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol5());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol6());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol7());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol8());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol9());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol10());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol11());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol12());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol13());

                                                            rows_list2.add(arrayList2);

                                                        }


                                                    }
                                                    Log.d("hash", "size: " + rows_list2.size());

                                                    int z = 0;
                                                    for (int i = 1; i < rows_list2.size() + 1; i++) {
                                                        Log.d("neww", "kdkd");

                                                        View rowww = table.getChildAt(i);

                                                        for (int j = 1; j < 14; j++) {
                                                            TableRow abb = (TableRow) rowww;
                                                            abb.setBaselineAligned(false);

                                                            TextView ab = (TextView) abb.getChildAt(j);


                                                            if (j == 8) {
                                                                Log.d("hash", "j=8: " + rows_list2.size());

                                                                int sum = string_to_integer(rows_list2.get(i - 1).get(1)) +
                                                                        string_to_integer(rows_list2.get(i - 1).get(2)) +
                                                                        string_to_integer(rows_list2.get(i - 1).get(3)) +
                                                                        string_to_integer(rows_list2.get(i - 1).get(4)) +
                                                                        string_to_integer(rows_list2.get(i - 1).get(5)) +
                                                                        string_to_integer(rows_list2.get(i - 1).get(6)) +
                                                                        string_to_integer(rows_list2.get(i - 1).get(7));
                                                                Log.d("tag", "hello: " + string_to_integer(rows_list2.get(i - 1).get(1)));

                                                                ab.setText(summ + "");
                                                                ab.setWidth(120);
                                                                ab.setBackgroundResource(R.drawable.border_white);
                                                                ab.setTextColor(Color.BLACK);
                                                                ab.setGravity(Gravity.CENTER);
                                                                ab.setTextSize(font);
                                                                ab.setHeight(height);
                                                                View vi = table.getChildAt(0);
                                                                TableRow ro = (TableRow) vi;
                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                ab.setTag(rows_list2.get(i - 1).get(0) + ":" + item_code_boxx.getText().toString());

                                                            }
                                                            if (j >= 9) {
                                                                ab.setText("");
                                                                View vi = table.getChildAt(0);
                                                                TableRow ro = (TableRow) vi;
                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                ab.setTag(rows_list2.get(i - 1).get(0) + ":" + item_code_boxx.getText().toString());

                                                                /////////////////////////////


                                                                DatabaseReference newroot = FirebaseDatabase.getInstance().getReference().child("Stock");
                                                                newroot.addValueEventListener(new ValueEventListener() {
                                                                    @RequiresApi(api = Build.VERSION_CODES.N)
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                        if (snapshot.exists()) {
                                                                            for (DataSnapshot node0 : snapshot.getChildren()) {
                                                                                for (DataSnapshot nodes : node0.getChildren()) {
                                                                                    Log.d("tag", "new node:" + nodes.getKey());


                                                                                    String n = nodes.getKey() .replace("slash", "/");
                                                                                    n = n.replace("o", ".");

                                                                                    n = n.replace("dot", ".");

                                                                                    n=n.replace("hash","#");
                                                                                    n=n.replace("dollar","$");
                                                                                    n=n.replace("openbrace","[");

                                                                                    n=n.replace("closebrace","]");
                                                                                    n=n.replace("star","*");


                                                                                    for (DataSnapshot nodes1 : nodes.getChildren()) {
                                                                                        if (nodes1.getKey().equals("Data")) {


                                                                                            col_0_obj1 = nodes1.getValue(new_input_class.class);
                                                                                            col_0_obj1.setCol0(n.toString());

                                                                                        }
                                                                                    }
                                                                                    Log.d("tag", "children= " + table.getChildCount());

                                                                                    for (int i = 1; i < table.getChildCount(); i++) {
                                                                                        TableRow nrow = (TableRow) table.getChildAt(i);


                                                                                        for (int j = 1; j < 14; j++) {

                                                                                            TextView itemcode = (TextView) nrow.getChildAt(0);
                                                                                            Log.d("tag", "Itemcode = " + itemcode.getText().toString());
                                                                                            Log.d("tag", "column 0 = " + col_0_obj1.getCol0());
                                                                                            String code = itemcode.getText().toString();
                                                                                            if (code.equals(col_0_obj1.getCol0())) {
                                                                                                Log.d("tag", "Itemcode matched");
                                                                                                column = 0;
                                                                                                if (j == 9) {
                                                                                                    column = 9;
                                                                                                    Log.d("tag", "column: " + column);

                                                                                                    TextView et_sale = (TextView) nrow.getChildAt(j);
                                                                                                    menu_array.clear();
                                                                                                    et_sale.setTag("2 core:" + col_0_obj1.getCol9());
                                                                                                    et_sale.setOnClickListener(stock_value.this::onClickk);


                                                                                                } else if (j == 10) {
                                                                                                    column = 10;
                                                                                                    TextView et_sale = (TextView) nrow.getChildAt(j);
                                                                                                    menu_array.clear();
                                                                                                    et_sale.setTag("3 core:" + col_0_obj1.getCol10());
                                                                                                    et_sale.setOnClickListener(stock_value.this::onClickk);


                                                                                                } else if (j == 11) {
                                                                                                    column = 11;
                                                                                                    TextView et_sale = (TextView) nrow.getChildAt(j);
                                                                                                    menu_array.clear();
                                                                                                    et_sale.setTag("4 core:" + col_0_obj1.getCol11());

                                                                                                    //et_sale.setOnFocusChangeListener(Sale.this::onFocusChange);
                                                                                                    et_sale.setOnClickListener(stock_value.this::onClickk);

                                                                                                } else if (j == 12) {
                                                                                                    column = 12;
                                                                                                    TextView et_sale = (TextView) nrow.getChildAt(j);
                                                                                                    menu_array.clear();
                                                                                                    et_sale.setTag("5 core:" + col_0_obj1.getCol12());

                                                                                                    et_sale.setOnClickListener(stock_value.this::onClickk);


                                                                                                } else if (j == 13) {
                                                                                                    column = 13;
                                                                                                    TextView et_sale = (TextView) nrow.getChildAt(j);
                                                                                                    menu_array.clear();
                                                                                                    et_sale.setTag("6 core:" + col_0_obj1.getCol13());


                                                                                                    et_sale.setOnClickListener(stock_value.this::onClickk);
                                                                                                    ;
                                                                                                }

                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }


                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                    }
                                                                });

                                                                ///////////////////////////////
                                                            } else {
                                                                if (rows_list2.get(i - 1).get(j) == null) {
                                                                    Log.d("hash", "I am ahere");
                                                                } else {
                                                                    View vi = table.getChildAt(0);
                                                                    TableRow ro = (TableRow) vi;
                                                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                    ab.setTag(rows_list2.get(i - 1).get(0) + ":" + item_code_boxx.getText().toString());
                                                                    int mul = string_to_integer(ab.getText().toString()) * string_to_integer(rows_list2.get(i - 1).get(j));

                                                                    if (mul == 0) {
                                                                        //ab.setText(mul+"");
                                                                    } else {

                                                                        ab.setText(mul + "");
                                                                    }
                                                                    summ += mul;
                                                                    ab.setWidth(120);
                                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                                    ab.setTextColor(Color.BLACK);
                                                                    ab.setGravity(Gravity.CENTER);
                                                                    ab.setTextSize(font);
                                                                    ab.setHeight(height);

                                                                }

                                                            }


                                                            //row1.addView(ab);

                                                        }
                                                        z++;


                                                        //table.addView(row1);
                                                    }
                                                    arrayList.clear();
                                                    rows_list.clear();

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
                    progressDialog2.dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);//



                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        }



        //int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            TextView tv = new TextView(getApplicationContext());
            tv.setText(" Item Code ");
            tv.setWidth(200);
            tv.setBackgroundResource(R.drawable.border_item);
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(font);

            row.addView(tv);

            TextView tv1 = new TextView(getApplicationContext());
            tv1.setText(" Red ");
            tv1.setWidth(120);
            tv1.setBackgroundResource(R.drawable.border_red);
            tv1.setTextColor(Color.parseColor("#D1C7C7"));
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextSize(font);
            row.addView(tv1);

            TextView tv2 = new TextView(getApplicationContext());
            tv2.setText(" Black ");
            tv2.setWidth(120);
            tv2.setBackgroundResource(R.drawable.border_black);
            tv2.setTextColor(Color.parseColor("#7CB342"));
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextSize(font);
            //tv2.setBackgroundColor(Color.parseColor("#050505"));
            row.addView(tv2);

            TextView tv3 = new TextView(getApplicationContext());
            tv3.setText(" Yellow ");
            tv3.setWidth(120);
            tv3.setBackgroundResource(R.drawable.border_yellow);
            tv3.setTextColor(Color.BLACK);
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextSize(font);
            //tv3.setBackgroundColor(Color.parseColor("#FDD835"));
            row.addView(tv3);

            TextView tv4 = new TextView(getApplicationContext());
            tv4.setText(" Blue ");
            tv4.setWidth(120);
            tv4.setBackgroundResource(R.drawable.border_blue);
            tv4.setTextColor(Color.BLACK);
            tv4.setGravity(Gravity.CENTER);
            tv4.setTextSize(font);

            row.addView(tv4);

            TextView tv5 = new TextView(getApplicationContext());
            tv5.setText(" Green ");
            tv5.setWidth(120);
            tv5.setBackgroundResource(R.drawable.border_green);
            tv5.setTextColor(Color.BLACK);
            tv5.setGravity(Gravity.CENTER);
            tv5.setTextSize(font);
            //tv5.setBackgroundColor(Color.parseColor("#43A047"));
            row.addView(tv5);

            TextView tv6 = new TextView(getApplicationContext());
            tv6.setText(" White ");
            tv6.setWidth(120);
            tv6.setBackgroundResource(R.drawable.border_white);
            tv6.setTextColor(Color.BLACK);
            tv6.setGravity(Gravity.CENTER);
            tv6.setTextSize(font);
            //tv6.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv6);

            TextView tv7 = new TextView(getApplicationContext());
            tv7.setText(" Other ");
            tv7.setWidth(120);
            tv7.setBackgroundResource(R.drawable.border_other);
            tv7.setTextColor(Color.BLACK);
            tv7.setGravity(Gravity.CENTER);
            tv7.setTextSize(font);
            //tv7.setBackgroundColor(Color.parseColor("#DBDF9C"));
            row.addView(tv7);

            TextView tv8 = new TextView(getApplicationContext());
            tv8.setText(" Total ");
            tv8.setWidth(120);
            tv8.setBackgroundResource(R.drawable.border_white);
            tv8.setTextColor(Color.BLACK);
            tv8.setGravity(Gravity.CENTER);
            tv8.setTextSize(font);
            //tv8.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv8);

            TextView tv9 = new TextView(getApplicationContext());
            tv9.setText(" 2 Core ");
            tv9.setWidth(120);
            tv9.setBackgroundResource(R.drawable.border_white);
            tv9.setTextColor(Color.BLACK);
            tv9.setGravity(Gravity.CENTER);
            tv9.setTextSize(font);
            //tv9.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv9);

            TextView tv10 = new TextView(getApplicationContext());
            tv10.setText(" 3 Core ");
            tv10.setWidth(120);
            tv10.setBackgroundResource(R.drawable.border_white);
            tv10.setTextColor(Color.BLACK);
            tv10.setGravity(Gravity.CENTER);
            tv10.setTextSize(font);
            //tv10.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv10);

            TextView tv11 = new TextView(getApplicationContext());
            tv11.setText(" 4 Core ");
            tv11.setWidth(120);
            tv11.setBackgroundResource(R.drawable.border_white);
            tv11.setTextColor(Color.BLACK);
            tv11.setGravity(Gravity.CENTER);
            tv11.setTextSize(font);
            //tv11.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv11);

            TextView tv12 = new TextView(getApplicationContext());
            tv12.setText(" 5 Core ");
            tv12.setWidth(120);
            tv12.setBackgroundResource(R.drawable.border_white);
            tv12.setTextColor(Color.BLACK);
            tv12.setGravity(Gravity.CENTER);
            tv12.setTextSize(font);
            //tv12.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv12);

            TextView tv13 = new TextView(getApplicationContext());
            tv13.setText(" 6 Core ");
            tv13.setWidth(160);
            tv13.setBackgroundResource(R.drawable.border_white);
            tv13.setTextColor(Color.BLACK);
            tv13.setGravity(Gravity.CENTER);
            tv13.setTextSize(font);
            //tv13.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.addView(tv13);


            table.addView(row);
            DatabaseReference date_root = FirebaseDatabase.getInstance().getReference().child("Stock");
            date_root.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot data : snapshot.getChildren()) {

                            String datee = data.getKey();
                            Log.d("hash", "date: " + datee);
                            date.add(datee);

                        }
                    }
                    Collections.sort(date, new Comparator<String>() {
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
                    if (date.size() > 0) {


                        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Stock").child(String.valueOf(date.get(date.size() - 1)));
                        root.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                if (snapshot.exists()) {

                                    for (DataSnapshot nodes : snapshot.getChildren()) {
                                        String itemcodeees = String.valueOf(nodes.getKey());




                                        ///
                                        String n = itemcodeees  .replace("slash", "/");
                                        n = n.replace("dot", ".");

                                        n=n.replace("hash","#");
                                        n=n.replace("dollar","$");
                                        n=n.replace("openbrace","[");

                                        n=n.replace("closebrace","]");
                                        n=n.replace("star","*");

                                        arrayList.add(n);

                                        for (DataSnapshot nodes1 : nodes.getChildren()) {
                                            if (nodes1.getKey().equals("Data")) {

                                                obj = nodes1.getValue(stock_data.class);
                                                //arrayList.add(itemcode);

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

                                        int z = 0;
                                        for (int i = 0; i < rows_list.size(); i++) {
                                            row1 = new TableRow(getApplicationContext());
                                            row1.setBaselineAligned(false);

                                            for (int j = 0; j < 14; j++) {
                                                ab = new TextView(getApplicationContext());

                                                if (j == 8) {

                                                    ///
                                                    int sum = string_to_integer(rows_list.get(i).get(1)) +
                                                            string_to_integer(rows_list.get(i).get(2)) +
                                                            string_to_integer(rows_list.get(i).get(3)) +
                                                            string_to_integer(rows_list.get(i).get(4)) +
                                                            string_to_integer(rows_list.get(i).get(5)) +
                                                            string_to_integer(rows_list.get(i).get(6)) +
                                                            string_to_integer(rows_list.get(i).get(7));
                                                    ab.setText(sum + "");
                                                    ab.setWidth(120);
                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                    ab.setTextColor(Color.BLACK);
                                                    ab.setGravity(Gravity.CENTER);
                                                    ab.setTextSize(font);
                                                    ab.setHeight(height);
                                                    ab.setOnClickListener(stock_value.this::onClick);
                                                    ab.setTag(rows_list.get(0).get(0));
                                                }
                                                if (j == 0) {
                                                    ab.setText(rows_list.get(i).get(j));
                                                    ab.setWidth(200);
                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                    ab.setTextColor(Color.BLACK);
                                                    ab.setGravity(Gravity.CENTER);
                                                    ab.setTextSize(font);
                                                    ab.setHeight(height);
                                                    ab.setTypeface(Typeface.DEFAULT_BOLD);

                                                }
                                                if(j==13){
                                                    ab.setText(rows_list.get(i).get(j));
                                                    ab.setWidth(160);
                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                    ab.setTextColor(Color.BLACK);
                                                    ab.setGravity(Gravity.CENTER);
                                                    ab.setTextSize(font);
                                                    ab.setHeight(height);
                                                    ab.setTypeface(Typeface.DEFAULT_BOLD);


                                                }

                                                else {
                                                    ab.setText(rows_list.get(i).get(j));
                                                    ab.setWidth(120);
                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                    ab.setTextColor(Color.BLACK);
                                                    ab.setGravity(Gravity.CENTER);
                                                    ab.setTextSize(font);
                                                    ab.setTag(rows_list.get(0).get(0));
                                                    ab.setOnClickListener(stock_value.this::onClick);
                                                    ab.setHeight(height);
                                                }


                                                row1.addView(ab);

                                            }
                                            z++;


                                            table.addView(row1);
                                        }
                                        arrayList.clear();
                                        rows_list.clear();

                                    }
                                    if (snapshot.getChildrenCount() != 1) {
                                        countt += 1;
                                    }

                                    Log.d("hash", "outside if");
                                    Log.d("hash", "COUNT:" + countt);
                                    Log.d("hash", "snapshot: " + snapshot.getChildrenCount());
///////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    if (countt == snapshot.getChildrenCount()) {
                                        Log.d("hash", "inside if");
                                        DatabaseReference roott = FirebaseDatabase.getInstance().getReference().child("ItemRate");
                                        roott.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot nodes : snapshot.getChildren()) {
                                                    String itemcodeees = String.valueOf(nodes.getKey());

                                                    arrayList2.add(itemcodeees);

                                                    for (DataSnapshot nodes1 : nodes.getChildren()) {
                                                        if (nodes1.getKey().equals("Rate")) {

                                                            obj = nodes1.getValue(stock_data.class);
                                                            //arrayList.add(itemcode);

                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol1());
                                                            Log.d("hash", "value: " + nodes1.getValue(Tablerow.class).getCol1());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol2());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol3());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol4());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol5());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol6());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol7());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol8());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol9());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol10());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol11());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol12());
                                                            arrayList2.add(nodes1.getValue(Tablerow.class).getCol13());

                                                            rows_list2.add(arrayList2);

                                                        }


                                                    }
                                                    Log.d("hash", "size: " + rows_list2.size());

                                                    int z = 0;
                                                    for (int i = 1; i < rows_list2.size() + 1; i++) {
                                                        Log.d("neww", "kdkd");

                                                        View rowww = table.getChildAt(i);

                                                        for (int j = 1; j < 14; j++) {
                                                            TableRow abb = (TableRow) rowww;
                                                            abb.setBaselineAligned(false);

                                                            TextView ab = (TextView) abb.getChildAt(j);


                                                            if (j == 8) {
                                                                Log.d("hash", "j=8: " + rows_list2.size());

                                                                int sum = string_to_integer(rows_list2.get(i - 1).get(1)) +
                                                                        string_to_integer(rows_list2.get(i - 1).get(2)) +
                                                                        string_to_integer(rows_list2.get(i - 1).get(3)) +
                                                                        string_to_integer(rows_list2.get(i - 1).get(4)) +
                                                                        string_to_integer(rows_list2.get(i - 1).get(5)) +
                                                                        string_to_integer(rows_list2.get(i - 1).get(6)) +
                                                                        string_to_integer(rows_list2.get(i - 1).get(7));
                                                                Log.d("tag", "hello: " + string_to_integer(rows_list2.get(i - 1).get(1)));

                                                                ab.setText(summ + "");
                                                                 ab.setWidth(120);
                                                                ab.setBackgroundResource(R.drawable.border_white);
                                                                ab.setTextColor(Color.BLACK);
                                                                ab.setGravity(Gravity.CENTER);
                                                                ab.setTextSize(font);
                                                                ab.setHeight(height);
                                                                View vi = table.getChildAt(0);
                                                                TableRow ro = (TableRow) vi;
                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                ab.setTag(rows_list2.get(i - 1).get(0) + ":" + item_code_boxx.getText().toString());

                                                            }
                                                            if (j >= 9) {
                                                                ab.setText("");
                                                                View vi = table.getChildAt(0);
                                                                TableRow ro = (TableRow) vi;
                                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                ab.setTag(rows_list2.get(i - 1).get(0) + ":" + item_code_boxx.getText().toString());

                                                                /////////////////////////////


                                                                DatabaseReference newroot = FirebaseDatabase.getInstance().getReference().child("Stock");
                                                                newroot.addValueEventListener(new ValueEventListener() {
                                                                    @RequiresApi(api = Build.VERSION_CODES.N)
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                        if (snapshot.exists()) {
                                                                            for (DataSnapshot node0 : snapshot.getChildren()) {
                                                                                for (DataSnapshot nodes : node0.getChildren()) {
                                                                                    Log.d("tag", "new node:" + nodes.getKey());


                                                                                    String n = nodes.getKey() .replace("slash", "/");
                                                                                    n = n.replace("o", ".");

                                                                                    n = n.replace("dot", ".");

                                                                                    n=n.replace("hash","#");
                                                                                    n=n.replace("dollar","$");
                                                                                    n=n.replace("openbrace","[");

                                                                                    n=n.replace("closebrace","]");
                                                                                    n=n.replace("star","*");


                                                                                    for (DataSnapshot nodes1 : nodes.getChildren()) {
                                                                                        if (nodes1.getKey().equals("Data")) {


                                                                                            col_0_obj1 = nodes1.getValue(new_input_class.class);
                                                                                            col_0_obj1.setCol0(n.toString());

                                                                                        }
                                                                                    }
                                                                                    Log.d("tag", "children= " + table.getChildCount());

                                                                                    for (int i = 1; i < table.getChildCount(); i++) {
                                                                                        TableRow nrow = (TableRow) table.getChildAt(i);


                                                                                        for (int j = 1; j < 14; j++) {

                                                                                            TextView itemcode = (TextView) nrow.getChildAt(0);
                                                                                            Log.d("tag", "Itemcode = " + itemcode.getText().toString());
                                                                                            Log.d("tag", "column 0 = " + col_0_obj1.getCol0());
                                                                                            String code = itemcode.getText().toString();
                                                                                            if (code.equals(col_0_obj1.getCol0())) {
                                                                                                Log.d("tag", "Itemcode matched");
                                                                                                column = 0;
                                                                                                if (j == 9) {
                                                                                                    column = 9;
                                                                                                    Log.d("tag", "column: " + column);

                                                                                                    TextView et_sale = (TextView) nrow.getChildAt(j);
                                                                                                    menu_array.clear();
                                                                                                    et_sale.setTag("2 core:" + col_0_obj1.getCol9());
                                                                                                    et_sale.setOnClickListener(stock_value.this::onClickk);


                                                                                                } else if (j == 10) {
                                                                                                    column = 10;
                                                                                                    TextView et_sale = (TextView) nrow.getChildAt(j);
                                                                                                    menu_array.clear();
                                                                                                    et_sale.setTag("3 core:" + col_0_obj1.getCol10());
                                                                                                    et_sale.setOnClickListener(stock_value.this::onClickk);


                                                                                                } else if (j == 11) {
                                                                                                    column = 11;
                                                                                                    TextView et_sale = (TextView) nrow.getChildAt(j);
                                                                                                    menu_array.clear();
                                                                                                    et_sale.setTag("4 core:" + col_0_obj1.getCol11());

                                                                                                    //et_sale.setOnFocusChangeListener(Sale.this::onFocusChange);
                                                                                                    et_sale.setOnClickListener(stock_value.this::onClickk);

                                                                                                } else if (j == 12) {
                                                                                                    column = 12;
                                                                                                    TextView et_sale = (TextView) nrow.getChildAt(j);
                                                                                                    menu_array.clear();
                                                                                                    et_sale.setTag("5 core:" + col_0_obj1.getCol12());

                                                                                                    et_sale.setOnClickListener(stock_value.this::onClickk);


                                                                                                } else if (j == 13) {
                                                                                                    column = 13;
                                                                                                    TextView et_sale = (TextView) nrow.getChildAt(j);
                                                                                                    menu_array.clear();
                                                                                                    et_sale.setTag("6 core:" + col_0_obj1.getCol13());


                                                                                                    et_sale.setOnClickListener(stock_value.this::onClickk);
                                                                                                    ;
                                                                                                }

                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }


                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                    }
                                                                });

                                                                ///////////////////////////////
                                                            } else {
                                                                if (rows_list2.get(i - 1).get(j) == null) {
                                                                    Log.d("hash", "I am ahere");
                                                                } else {
                                                                    View vi = table.getChildAt(0);
                                                                    TableRow ro = (TableRow) vi;
                                                                    TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                                    ab.setTag(rows_list2.get(i - 1).get(0) + ":" + item_code_boxx.getText().toString());
                                                                    int mul = string_to_integer(ab.getText().toString()) * string_to_integer(rows_list2.get(i - 1).get(j));

                                                                    if (mul == 0) {
                                                                        //ab.setText(mul+"");
                                                                    } else {

                                                                        ab.setText(mul + "");
                                                                    }
                                                                    summ += mul;
                                                                    ab.setWidth(120);
                                                                    ab.setBackgroundResource(R.drawable.border_white);
                                                                    ab.setTextColor(Color.BLACK);
                                                                    ab.setGravity(Gravity.CENTER);
                                                                    ab.setTextSize(font);
                                                                    ab.setHeight(height);

                                                                }

                                                            }


                                                            //row1.addView(ab);

                                                        }
                                                        z++;


                                                        //table.addView(row1);
                                                    }
                                                    arrayList.clear();
                                                    rows_list.clear();

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
                    progressDialog2.dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);//



                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        }



    }

    int string_to_integer(String s){

        if (s.matches("")|| s==null){
            return 0;
        }
        else{
            return Integer.parseInt(s);
        }
    }

    @Override
    public void onClick(View v) {
        TextView vv=(TextView)v;
        TextView gg= findViewById(R.id.textView5);
        gg.setText("Row: "+vv.getTag());

        Toast.makeText(getApplicationContext(),"Row: "+vv.getTag(),Toast.LENGTH_SHORT).show();
    }
    public void onClickk(View v) {

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

                    menu_array.add(Integer.valueOf(number));
                    number="";
                }

            }
            menu_array.add(Integer.valueOf(number));




            for(int x=0; x< menu_array.size(); x++){
                menu.getMenu().add(String.valueOf(menu_array.get(x)));
            }


            menu.show();}

        TextView gg= findViewById(R.id.textView5);
        gg.setText("Row: "+vv.getTag());

        Toast.makeText(getApplicationContext(),"Row: "+vv.getTag(),Toast.LENGTH_SHORT).show();


    }

}

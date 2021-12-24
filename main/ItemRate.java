package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class ItemRate extends AppCompatActivity implements View.OnFocusChangeListener {

    ArrayList<String> arrayList = new ArrayList<>();

    ArrayList<ArrayList<String>> rows_list = new ArrayList<ArrayList<String>>();

    ProgressDialog progressDialog2;
    String data="";


    DatabaseReference root;
    public TableRow row;
    TableLayout table;
    TextView ab;
    public TableRow row1;
    public rates obj;
    EditText button;


    ArrayList<Item_code_obj> list_of_item_code = new ArrayList<Item_code_obj>();
    ArrayList<Integer> list_of_sequence_number = new ArrayList<Integer>();
    public int height=90;
    public int font=11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_rate);
        getSupportActionBar().setTitle("Item Rate");
        progressDialog2 = new ProgressDialog(ItemRate.this);
        progressDialog2.setMessage("Loading"); // Setting Message
        progressDialog2.setTitle("Item Rates"); // Setting Title
        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog2.setCanceledOnTouchOutside(false);
        progressDialog2.show();



        table = findViewById(R.id.item_table);
        row = new TableRow(this);
        row.setBaselineAligned(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String millisInString = dateFormat.format(new Date());


        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
          /*  DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().child("ItemRate");
            root1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot nodes: snapshot.getChildren()){


                        String itemcodeees = String.valueOf(nodes.getKey());
                        String n = itemcodeees.replace("-", "/");
                        n = n.replace("o", ".");


                        n=n.replace('z','#');
                        n=n.replace('d','$');
                        n=n.replace('s','[');

                        n=n.replace('e',']');
                        n=n.replace('t','*');
                        Log.d("sat","nodes.value: "+nodes.getValue(Firebase_item_code.class).getSeqenceno());

                        list_of_item_code.add(new Item_code_obj(n, nodes.getValue(Firebase_item_code.class).getSeqenceno()));

                    }
                    Log.d("sat","size: "+list_of_item_code.size());
                    for(int i=0;i<list_of_item_code.size();i++){


                        for(int j=i+1;j<list_of_item_code.size();j++){

                            Item_code_obj tempI=list_of_item_code.get(i);
                            Item_code_obj tempJ=list_of_item_code.get(j);

                            if(Integer.parseInt(tempI.getSequence()) > Integer.parseInt(tempJ.getSequence())){
                                list_of_item_code.set(i, tempJ);
                                list_of_item_code.set(j,tempI);

                            }

                        }
                    }
                    for(int i=0; i< list_of_item_code.size(); i++){
                        Log.d("sat","Item_code_list: "+list_of_item_code.get(i).getName());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


*/



            DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("ItemRate");
            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        Log.d("sat","iam here");
                        table.removeAllViews();

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


                        for (DataSnapshot nodes : snapshot.getChildren()) {
                            String itemcodeees = String.valueOf(nodes.getKey());



                            String n = itemcodeees.replace("slash", "/");
                            n = n.replace("dot", ".");

                            n=n.replace("hash","#");
                            n=n.replace("dollar","$");
                            n=n.replace("openbrace","[");

                            n=n.replace("closebrace","]");
                            n=n.replace("star","*");
                            arrayList.add(n);
                            list_of_item_code.add(new Item_code_obj(n, nodes.getValue(Firebase_item_code.class).getSeqenceno()));


                            for (DataSnapshot nodes1 : nodes.getChildren()) {
                                if (nodes1.getKey().equals("Rate")) {

                                    obj = nodes1.getValue(rates.class);

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


                                    for (int s = 0; s < rows_list.size(); s++) {
                                        Log.d("yes", rows_list.get(s).toString());
                                    }

                                }


                            }
                            Log.d("sat","size of list: "+list_of_item_code.size());





                        }

                    }
                    for(int i=0;i<list_of_item_code.size();i++){


                        for(int j=i+1;j<list_of_item_code.size();j++){

                            Item_code_obj tempI=list_of_item_code.get(i);
                            Item_code_obj tempJ=list_of_item_code.get(j);

                            if(Integer.parseInt(tempI.getSequence()) > Integer.parseInt(tempJ.getSequence())){
                                list_of_item_code.set(i, tempJ);
                                list_of_item_code.set(j,tempI);

                            }

                        }
                    }

                    for (int i = 0; i < rows_list.size(); i++) {

                        row1 = new TableRow(getApplicationContext());
                        row1.setBaselineAligned(false);
                        for (int j = 0; j < 14; j++) {

                            if (j == 0) {

                                ab = new TextView(getApplicationContext());
                                //Log.d("check",rows_list.get(i).get(j));
                                ab.setText(list_of_item_code.get(i).getName());
                                //ab.setText(rows_list.get(i).get(j));


                                ab.setWidth(200);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(font);
                                ab.setHeight(height);
                                ab.setTypeface(Typeface.DEFAULT_BOLD);
                                row1.addView(ab);


                            }

                            else{
                                if(j>=8 && j!=13){
                                    TextView et = new TextView(getApplicationContext());
                                    et.setInputType(InputType.TYPE_CLASS_NUMBER);

                                    et.setText(rows_list.get(i).get(j));

                                    et.setOnFocusChangeListener(ItemRate.this::onFocusChange);
                                    et.setTag(rows_list.get(0).get(0));
                                    et.setWidth(120);
                                    et.setBackgroundResource(R.drawable.border_white);
                                    et.setTextColor(Color.BLACK);
                                    et.setGravity(Gravity.CENTER);
                                    et.setTextSize(font);
                                    et.setHeight(height);
                                    row1.addView(et);
                                }
                                else if(j==13){
                                    TextView et = new TextView(getApplicationContext());
                                    et.setInputType(InputType.TYPE_CLASS_NUMBER);

                                    et.setText(rows_list.get(i).get(j));

                                    et.setOnFocusChangeListener(ItemRate.this::onFocusChange);
                                    et.setTag(rows_list.get(0).get(0));
                                    et.setWidth(160);
                                    et.setBackgroundResource(R.drawable.border_white);
                                    et.setTextColor(Color.BLACK);
                                    et.setGravity(Gravity.CENTER);
                                    et.setTextSize(font);
                                    et.setHeight(height);
                                    row1.addView(et);
                                }

                                else{
                                    EditText et = new EditText(getApplicationContext());
                                    et.setInputType(InputType.TYPE_CLASS_NUMBER);

                                    et.setText(rows_list.get(i).get(j));

                                    et.setOnFocusChangeListener(ItemRate.this::onFocusChange);
                                    et.setTag(rows_list.get(0).get(0));
                                    et.setWidth(120);
                                    et.setBackgroundResource(R.drawable.border_white);
                                    et.setTextColor(Color.BLACK);
                                    et.setGravity(Gravity.CENTER);
                                    et.setTextSize(font);
                                    et.setHeight(height);
                                    row1.addView(et);
                                }

                            }



                        }
                        //z++;

                        table.addView(row1);
                    }
                    arrayList.clear();
                    rows_list.clear();
                    progressDialog2.dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });

            Button save=findViewById(R.id.item_save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 1; i < table.getChildCount(); i++) {
                        TableRow nrow = (TableRow) table.getChildAt(i);


                        for (int j = 1; j < 13; j++) {

                            TextView itemcode = (TextView) nrow.getChildAt(0);

                            String item_code = itemcode.getText().toString();





                            int col_data = 0;
                            String added = null;
                            if (obj != null){

                                if (j == 1) {
                                    button = (EditText) nrow.getChildAt(j);
                                    data = button.getText().toString();


                                    if (obj != null && !obj.getCol1().equals("")) {


                                        if (!data.equals("")) {



                                            added = data.toString();


                                        }
                                        else {

                                            added = obj.getCol1();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 2) {
                                   button = (EditText) nrow.getChildAt(j);
                                    data = button.getText().toString();

                                    if (obj != null && !obj.getCol2().equals("")) {

                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol2();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 3) {
                                     button = (EditText) nrow.getChildAt(j);
                                    data = button.getText().toString();


                                    if (obj != null && !obj.getCol3().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol3();

                                        }

                                    } else {
                                        added = data;
                                    }

                                }


                                if (j == 4) {
                                    button = (EditText) nrow.getChildAt(j);
                                    data = button.getText().toString();

                                    if (obj != null && !obj.getCol4().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol4();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 5) {
                                     button = (EditText) nrow.getChildAt(j);
                                    data = button.getText().toString();

                                    if (obj != null && !obj.getCol5().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol5();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 6) {
                                     button = (EditText) nrow.getChildAt(j);
                                     data = button.getText().toString();

                                    if (obj != null && !obj.getCol6().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol6();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 7) {
                                   button = (EditText) nrow.getChildAt(j);
                                   data = button.getText().toString();

                                    if (obj != null && !obj.getCol7().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol7();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 8) {
                                   TextView button = (TextView) nrow.getChildAt(j);
                                     data = button.getText().toString();

                                    if (obj != null && !obj.getCol8().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol8();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 9) {
                                    TextView button = (TextView) nrow.getChildAt(j);
                                     data = button.getText().toString();

                                    if (obj != null && !obj.getCol9().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol9();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 10) {
                                    TextView button = (TextView) nrow.getChildAt(j);
                                     data = button.getText().toString();
                                    if (obj != null && !obj.getCol10().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol10();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }

                                if (j == 11) {
                                    TextView button = (TextView) nrow.getChildAt(j);
                                     data = button.getText().toString();

                                    if (obj != null && !obj.getCol11().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();

                                        } else {

                                            added = obj.getCol11();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 12) {
                                    TextView button = (TextView) nrow.getChildAt(j);
                                     data = button.getText().toString();

                                    if (obj != null && !obj.getCol12().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol12();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }
                                if (j == 13) {
                                    TextView button = (TextView) nrow.getChildAt(j);
                                     data = button.getText().toString();

                                    if (obj != null && !obj.getCol12().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol12();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }

                            }
                            else{
                                added=data;
                            }


                            String n = item_code.replace("/", "slash");
                            n =n.replace(".", "dot");
                            n=n.replace("#","hanh");
                            n=n.replace("$","donlar");
                            n=n.replace("[","opnnbrace");
                            n=n.replace("]","clnsebrace");
                            n=n.replace("*","star");
                            DatabaseReference root = FirebaseDatabase.getInstance().getReference().
                                    child("ItemRate");

                            root.child(n).child("Rate").
                                    child("col" + j).setValue(added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        button.setText("");

                                    }
                                }
                            });



                        }

                    }

                    finish();
                }
            });
        }



        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
          /*  DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().child("ItemRate");
            root1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot nodes: snapshot.getChildren()){


                        String itemcodeees = String.valueOf(nodes.getKey());
                        String n = itemcodeees.replace("-", "/");
                        n = n.replace("o", ".");


                        n=n.replace('z','#');
                        n=n.replace('d','$');
                        n=n.replace('s','[');

                        n=n.replace('e',']');
                        n=n.replace('t','*');
                        Log.d("sat","nodes.value: "+nodes.getValue(Firebase_item_code.class).getSeqenceno());

                        list_of_item_code.add(new Item_code_obj(n, nodes.getValue(Firebase_item_code.class).getSeqenceno()));

                    }
                    Log.d("sat","size: "+list_of_item_code.size());
                    for(int i=0;i<list_of_item_code.size();i++){


                        for(int j=i+1;j<list_of_item_code.size();j++){

                            Item_code_obj tempI=list_of_item_code.get(i);
                            Item_code_obj tempJ=list_of_item_code.get(j);

                            if(Integer.parseInt(tempI.getSequence()) > Integer.parseInt(tempJ.getSequence())){
                                list_of_item_code.set(i, tempJ);
                                list_of_item_code.set(j,tempI);

                            }

                        }
                    }
                    for(int i=0; i< list_of_item_code.size(); i++){
                        Log.d("sat","Item_code_list: "+list_of_item_code.get(i).getName());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


*/



            DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("ItemRate");
            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        Log.d("sat","iam here");
                        table.removeAllViews();

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


                        for (DataSnapshot nodes : snapshot.getChildren()) {
                            String itemcodeees = String.valueOf(nodes.getKey());


                            ///
                            String n = itemcodeees.replace("slash", "/");
                            n = n.replace("dot", ".");

                            n=n.replace("hash","#");
                            n=n.replace("dollar","$");
                            n=n.replace("openbrace","[");

                            n=n.replace("closebrace","]");
                            n=n.replace("star","*");
                            arrayList.add(n);
                            list_of_item_code.add(new Item_code_obj(n, nodes.getValue(Firebase_item_code.class).getSeqenceno()));


                            for (DataSnapshot nodes1 : nodes.getChildren()) {
                                if (nodes1.getKey().equals("Rate")) {

                                    obj = nodes1.getValue(rates.class);

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


                                    for (int s = 0; s < rows_list.size(); s++) {
                                        Log.d("yes", rows_list.get(s).toString());
                                    }

                                }


                            }
                            Log.d("sat","size of list: "+list_of_item_code.size());





                        }

                    }
                    for(int i=0;i<list_of_item_code.size();i++){


                        for(int j=i+1;j<list_of_item_code.size();j++){

                            Item_code_obj tempI=list_of_item_code.get(i);
                            Item_code_obj tempJ=list_of_item_code.get(j);

                            if(Integer.parseInt(tempI.getSequence()) > Integer.parseInt(tempJ.getSequence())){
                                list_of_item_code.set(i, tempJ);
                                list_of_item_code.set(j,tempI);

                            }

                        }
                    }

                    for (int i = 0; i < rows_list.size(); i++) {

                        row1 = new TableRow(getApplicationContext());
                        row1.setBaselineAligned(false);
                        for (int j = 0; j < 14; j++) {

                            if (j == 0) {

                                ab = new TextView(getApplicationContext());
                                //Log.d("check",rows_list.get(i).get(j));
                                ab.setText(list_of_item_code.get(i).getName());
                                //ab.setText(rows_list.get(i).get(j));


                                ab.setWidth(200);
                                ab.setBackgroundResource(R.drawable.border_white);
                                ab.setTextColor(Color.BLACK);
                                ab.setGravity(Gravity.CENTER);
                                ab.setTextSize(font);
                                ab.setHeight(height);
                                ab.setTypeface(Typeface.DEFAULT_BOLD);
                                row1.addView(ab);


                            }

                            else{
                                if(j>=8 && j!=13){
                                    TextView et = new TextView(getApplicationContext());
                                    et.setInputType(InputType.TYPE_CLASS_NUMBER);

                                    et.setText(rows_list.get(i).get(j));

                                    et.setOnFocusChangeListener(ItemRate.this::onFocusChange);
                                    et.setTag(rows_list.get(0).get(0));
                                    et.setWidth(120);
                                    et.setBackgroundResource(R.drawable.border_white);
                                    et.setTextColor(Color.BLACK);
                                    et.setGravity(Gravity.CENTER);
                                    et.setTextSize(font);
                                    et.setHeight(height);
                                    row1.addView(et);
                                }
                                else if(j==13){
                                    TextView et = new TextView(getApplicationContext());
                                    et.setInputType(InputType.TYPE_CLASS_NUMBER);

                                    et.setText(rows_list.get(i).get(j));

                                    et.setOnFocusChangeListener(ItemRate.this::onFocusChange);
                                    et.setTag(rows_list.get(0).get(0));
                                    et.setWidth(160);
                                    et.setBackgroundResource(R.drawable.border_white);
                                    et.setTextColor(Color.BLACK);
                                    et.setGravity(Gravity.CENTER);
                                    et.setTextSize(font);
                                    et.setHeight(height);
                                    row1.addView(et);
                                }

                                else{
                                    EditText et = new EditText(getApplicationContext());
                                    et.setInputType(InputType.TYPE_CLASS_NUMBER);

                                    et.setText(rows_list.get(i).get(j));

                                    et.setOnFocusChangeListener(ItemRate.this::onFocusChange);
                                    et.setTag(rows_list.get(0).get(0));
                                    et.setWidth(120);
                                    et.setBackgroundResource(R.drawable.border_white);
                                    et.setTextColor(Color.BLACK);
                                    et.setGravity(Gravity.CENTER);
                                    et.setTextSize(font);
                                    et.setHeight(height);
                                    row1.addView(et);
                                }

                            }



                        }
                        //z++;

                        table.addView(row1);
                    }
                    arrayList.clear();
                    rows_list.clear();
                    progressDialog2.dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });

            Button save=findViewById(R.id.item_save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 1; i < table.getChildCount(); i++) {
                        TableRow nrow = (TableRow) table.getChildAt(i);


                        for (int j = 1; j < 13; j++) {

                            TextView itemcode = (TextView) nrow.getChildAt(0);

                            String item_code = itemcode.getText().toString();





                            int col_data = 0;
                            String added = null;
                            if (obj != null){

                                if (j == 1) {
                                    button = (EditText) nrow.getChildAt(j);
                                    data = button.getText().toString();


                                    if (obj != null && !obj.getCol1().equals("")) {


                                        if (!data.equals("")) {



                                            added = data.toString();


                                        }
                                        else {

                                            added = obj.getCol1();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 2) {
                                    button = (EditText) nrow.getChildAt(j);
                                    data = button.getText().toString();

                                    if (obj != null && !obj.getCol2().equals("")) {

                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol2();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 3) {
                                    button = (EditText) nrow.getChildAt(j);
                                    data = button.getText().toString();


                                    if (obj != null && !obj.getCol3().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol3();

                                        }

                                    } else {
                                        added = data;
                                    }

                                }


                                if (j == 4) {
                                    button = (EditText) nrow.getChildAt(j);
                                    data = button.getText().toString();

                                    if (obj != null && !obj.getCol4().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol4();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 5) {
                                    button = (EditText) nrow.getChildAt(j);
                                    data = button.getText().toString();

                                    if (obj != null && !obj.getCol5().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol5();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 6) {
                                    button = (EditText) nrow.getChildAt(j);
                                    data = button.getText().toString();

                                    if (obj != null && !obj.getCol6().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol6();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 7) {
                                    button = (EditText) nrow.getChildAt(j);
                                    data = button.getText().toString();

                                    if (obj != null && !obj.getCol7().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol7();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 8) {
                                    TextView button = (TextView) nrow.getChildAt(j);
                                    data = button.getText().toString();

                                    if (obj != null && !obj.getCol8().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol8();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 9) {
                                    TextView button = (TextView) nrow.getChildAt(j);
                                    data = button.getText().toString();

                                    if (obj != null && !obj.getCol9().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol9();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 10) {
                                    TextView button = (TextView) nrow.getChildAt(j);
                                    data = button.getText().toString();
                                    if (obj != null && !obj.getCol10().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol10();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }

                                if (j == 11) {
                                    TextView button = (TextView) nrow.getChildAt(j);
                                    data = button.getText().toString();

                                    if (obj != null && !obj.getCol11().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();

                                        } else {

                                            added = obj.getCol11();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 12) {
                                    TextView button = (TextView) nrow.getChildAt(j);
                                    data = button.getText().toString();

                                    if (obj != null && !obj.getCol12().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol12();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }
                                if (j == 13) {
                                    TextView button = (TextView) nrow.getChildAt(j);
                                    data = button.getText().toString();

                                    if (obj != null && !obj.getCol12().equals("")) {


                                        if (!data.equals("")) {

                                            added = data.toString();


                                        } else {

                                            added = obj.getCol12();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }

                            }
                            else{
                                added=data;
                            }


                            ///
                            String n = item_code.replace("/", "slash");
                            n = n.replace(".", "o");

                            n =n.replace(".", "dot");
                            n=n.replace("#","hanh");
                            n=n.replace("$","donlar");
                            n=n.replace("[","opnnbrace");
                            n=n.replace("]","clnsebrace");
                            n=n.replace("*","star");
                            DatabaseReference root = FirebaseDatabase.getInstance().getReference().
                                    child("ItemRate");

                            root.child(n).child("Rate").
                                    child("col" + j).setValue(added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        button.setText("");

                                    }
                                }
                            });



                        }

                    }

                    finish();
                }
            });
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText vv=(EditText)v;
        TextView gg= findViewById(R.id.textView6);
        gg.setText(""+vv.getTag());

       // Toast.makeText(getApplicationContext(),"Editing Row: "+vv.getTag(),Toast.LENGTH_SHORT).show();
    }
}

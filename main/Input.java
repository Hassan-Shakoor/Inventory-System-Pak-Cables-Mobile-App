package com.mamoo.istproject;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

import org.w3c.dom.Text;

public class Input extends AppCompatActivity implements View.OnFocusChangeListener {

    ArrayList<String> date_list=new ArrayList<String >();
    public new_input_class col_0_obj1;
    ArrayList<Integer> maxx=new ArrayList<Integer>();
    ArrayList<ArrayList<String>> rows_list = new ArrayList<ArrayList<String>>();

    ArrayList<String> seq_no = new ArrayList<>();
    ProgressDialog progressDialog;
    DatabaseReference root;
    String s;
    public TableRow row;
    ArrayList<String> bills=new ArrayList<String >();

    int countt=0;
    TableLayout table;
    public ArrayList<new_input_class> objArray=new ArrayList<new_input_class>();
    public ArrayList<stock_data> billobjArray=new ArrayList<stock_data>();
    public ArrayList<Integer> core_data=new ArrayList<Integer>();
    public ArrayList<Integer> bill_core_data=new ArrayList<Integer>();
    public ArrayList<new_input_class> pc_array=new ArrayList<new_input_class>();



    TextView ab;

    public TableRow row1;

    public new_input_class obj;
    public stock_data obj_1;

    public new_input_class col_0_obj;
    public stock_data obj1;
    public new_input_class obj11;
    public int height=90;
    public int font=11;
    ProgressDialog progressDialog2;
    String billnum;
    EditText et_billno;
    public int g=1;
    TextView DateFrom;
    DatePickerDialog.OnDateSetListener setListener;

    View checking_view=null;
    String m_Text = "";
    String aa;



    ArrayList<Item_code_obj> list_of_item_code= new ArrayList<Item_code_obj>();
    ArrayList<Integer> list_of_sequence_number= new ArrayList<Integer>();
    ArrayList<Integer> list_bill_no= new ArrayList<Integer>();
    ArrayList<String> item_codes_list= new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        DateFrom = findViewById(R.id.output_from);


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        progressDialog2 = new ProgressDialog(this);
        progressDialog2.setMessage("Loading"); // Setting Message
        progressDialog2.setTitle("Input Screen"); // Setting Title
        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog2.setCanceledOnTouchOutside(false);
        progressDialog2.show();




        obj=new new_input_class();
        obj1=new stock_data();

        table = findViewById(R.id.table);
        row = new TableRow(this);
        row.setBaselineAligned(false);
        TextView tv_date=findViewById(R.id.tv_input_date);
        et_billno=(EditText)findViewById(R.id.et_input_bill_number);
        et_billno.setInputType(InputType.TYPE_CLASS_PHONE);

        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Input.this , android.R.style.Theme_Holo_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                month = month + 1;
                String m = "" , day = "";
                if(month < 10){
                    m = "0"+month;
                }
                if(dayofMonth < 10){
                    day = "0"+dayofMonth;
                }
                if(month >=10){
                    m=""+month;
                }
                if(dayofMonth>=10){
                    day = ""+dayofMonth;
                }

                String date = day+"-"+m+"-"+year;
                tv_date.setText(date);
                Log.d("zeesh","date"+ date+ "day: "+day);
            }
        };





        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String millisInString  = dateFormat.format(new Date());
       tv_date.setText(millisInString.toString());
        DatabaseReference billno_dbs = FirebaseDatabase.getInstance()
                .getReference().child("Billno");
        billno_dbs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pc_array.clear();
                for(DataSnapshot node: snapshot.getChildren()){


                    billnum=node.getKey();
                    list_bill_no.add(Integer.parseInt(node.getKey()));

                    for(DataSnapshot node1: node.getChildren()){

                        for(DataSnapshot node2: node1.getChildren()){
                            Log.d("goshi","node1111 "+node2);
                            Log.d("goshi","bill key "+node.getKey());
                            if(node2.getKey().equals("Data")){

                                billobjArray.add(node2.getValue(stock_data.class));
                                obj11=node2.getValue(new_input_class.class);
                                obj11.setCol0(node.getKey());
                                pc_array.add(obj11);
                            }
                        }


                    }

                }

                et_billno.setText(billnum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            Button change = findViewById(R.id.btn_change_bill);
            change.setEnabled(false);
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.d("HASH", "bill: " + billnum);

                    if (billnum != "" && billnum != null) {
                        Log.d("HASH", "et: " + et_billno.getText().toString());
                        for (int a = 0; a < list_bill_no.size(); a++) {
                            if (list_bill_no.get(a) != Integer.parseInt(billnum)) {
                                if (list_bill_no.get(a) > Integer.parseInt(et_billno.getText().toString())) {
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    Query applesQuery = ref.child("Billno").child(String.valueOf(list_bill_no.get(a)));

                                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot GroupSnapshot : dataSnapshot.getChildren()) {
                                                GroupSnapshot.getRef().removeValue();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.e(TAG, "onCancelled", databaseError.toException());
                                        }
                                    });
                                }
                            }

                        }


                        DatabaseReference billno_dbs2 = FirebaseDatabase.getInstance().getReference().child("Billno");


                        billno_dbs2.child(billnum).get().addOnSuccessListener(dataSnapshot -> {
                            billno_dbs2.child(et_billno.getText().toString()).setValue(dataSnapshot.getValue());
                            billno_dbs2.child(billnum).removeValue();


                        });


                    } else {
                        Toast.makeText(Input.this, "Bill no cannot be empty", Toast.LENGTH_SHORT).show();
                    }

                }
            });


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
            //tv1.setBackgroundColor(Color.parseColor("#E67F7F"));
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



            et_billno.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(s.toString().equals("")!=true && s.toString()!=null && s.toString().equals(billnum)!=true){

                        Log.d("yasser","1");

                        DatabaseReference billno_dbs1 = FirebaseDatabase.getInstance().getReference().child("Billno").child(s.toString());
                        billno_dbs1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot node : snapshot.getChildren()) {
                                        ArrayList<String> arrayList = new ArrayList<>();
                                        Log.d("gat","node: "+node);
                                        String itemcodeees = String.valueOf(node.getKey());
                                        arrayList.add(itemcodeees);


                                        for (DataSnapshot nodes1 : node.getChildren()) {
                                            if (nodes1.getKey().equals("Data")) {

                                                Log.d("here","here");
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


                                            }



                                        }
                                        rows_list.add(arrayList);

                                    }
                                    int z = 0;
                                    for(int i=0;i<rows_list.size();i++){
                                        Log.d("gat","list: "+ rows_list.get(i));
                                    }
                                    Log.d("gat","table count: "+table.getChildCount());
                                    Log.d("gat","rows list size: "+rows_list.size());
                                    for (int i = 1; i < rows_list.size()+1; i++) {
                                        Log.d("yasser","I am here: "+rows_list.size());


                                        TableRow nrow = (TableRow) table.getChildAt(i);
                                        for (int j = 1; j < 14; j++) {
                                            if(nrow!=null){


                                                if (j == 8) {
                                                    EditText button = (EditText) nrow.getChildAt(j);
                                                    button.setInputType(InputType.TYPE_CLASS_PHONE);
                                                    int sum = string_to_integer(rows_list.get(i-1).get(1)) +
                                                            string_to_integer(rows_list.get(i-1).get(2)) +
                                                            string_to_integer(rows_list.get(i-1).get(3)) +
                                                            string_to_integer(rows_list.get(i-1).get(4)) +
                                                            string_to_integer(rows_list.get(i-1).get(5)) +
                                                            string_to_integer(rows_list.get(i-1).get(6)) +
                                                            string_to_integer(rows_list.get(i-1).get(7));
                                                    button.setText(sum + "");


                                                }
                                                else{

                                                    if(j>=9 && j<14){
                                                        TextView button = (TextView) nrow.getChildAt(j);
                                                        //split(rows_list.get(i).get(j));
                                                        button.setText((rows_list.get(i-1).get(j)));
                                                    }
                                                    else{
                                                        EditText button = (EditText) nrow.getChildAt(j);
                                                        //split(rows_list.get(i).get(j));
                                                        button.setText(rows_list.get(i-1).get(j));


                                                    }

                                                }
                                            }




                                        }
                                        z++;


                                    }

                                }

                                rows_list.clear();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Log.d("last", "not equal");

                    }
                    else if(s.toString().equals(billnum)==true){

                        Log.d("yasser","1");

                        DatabaseReference billno_dbs1 = FirebaseDatabase.getInstance().getReference().child("Billno").child(s.toString());
                        billno_dbs1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot node : snapshot.getChildren()) {
                                        ArrayList<String> arrayList = new ArrayList<>();
                                        Log.d("gat","node: "+node);
                                        String itemcodeees = String.valueOf(node.getKey());
                                        arrayList.add(itemcodeees);



                                        for (DataSnapshot nodes1 : node.getChildren()) {
                                            if (nodes1.getKey().equals("Data")) {

                                                Log.d("here","here");
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


                                            }



                                        }
                                        rows_list.add(arrayList);

                                    }
                                    int z = 0;
                                    for(int i=0;i<rows_list.size();i++){
                                        Log.d("gat","list: "+ rows_list.get(i));
                                    }
                                    Log.d("gat","table count: "+table.getChildCount());
                                    Log.d("gat","rows list size: "+rows_list.size());
                                    for (int i = 1; i < rows_list.size()+1; i++) {
                                        Log.d("yasser","I am here: "+rows_list.size());


                                        TableRow nrow = (TableRow) table.getChildAt(i);
                                        for (int j = 1; j < 14; j++) {
                                            if(nrow!=null){

                                                if (j == 8) {
                                                    EditText button = (EditText) nrow.getChildAt(j);
                                                    button.setInputType(InputType.TYPE_CLASS_PHONE);
                                                    int sum = string_to_integer(rows_list.get(i-1).get(1)) +
                                                            string_to_integer(rows_list.get(i-1).get(2)) +
                                                            string_to_integer(rows_list.get(i-1).get(3)) +
                                                            string_to_integer(rows_list.get(i-1).get(4)) +
                                                            string_to_integer(rows_list.get(i-1).get(5)) +
                                                            string_to_integer(rows_list.get(i-1).get(6)) +
                                                            string_to_integer(rows_list.get(i-1).get(7));
                                                    button.setText(sum + "");


                                                }
                                                else{

                                                    if(j>=9 && j<14){
                                                        TextView button = (TextView) nrow.getChildAt(j);
                                                        //split(rows_list.get(i).get(j));
                                                        button.setText((rows_list.get(i-1).get(j)));
                                                    }
                                                    else{
                                                        EditText button = (EditText) nrow.getChildAt(j);
                                                        //split(rows_list.get(i).get(j));
                                                        button.setText(rows_list.get(i-1).get(j));


                                                    }

                                                }

                                            }





                                        }
                                        z++;


                                    }

                                }

                                rows_list.clear();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Log.d("last", "not equal");

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });






                root = FirebaseDatabase.getInstance().getReference().child("StockFull");
                root.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        objArray.clear();
                        if (snapshot.exists()) {
                            // for(DataSnapshot node0: snapshot.getChildren()){

                            for (DataSnapshot nodes : snapshot.getChildren()) {
                                item_codes_list.add(nodes.getKey());
                                Log.d("date","ITE CODE: "+nodes.getKey());

                                ////
                                String n = nodes.getKey().replace("slash", "/");


                                n = n.replace("dot", ".");

                                n=n.replace("hash","#");
                                n=n.replace("dollar","$");
                                n=n.replace("openbrace","[");

                                n=n.replace("closebrace","]");
                                n=n.replace("star","*");


                                list_of_item_code.add(new Item_code_obj(n, nodes.getValue(Firebase_item_code.class).getSeqenceno()));

                                for (DataSnapshot nodes1 : nodes.getChildren()) {
                                    if (nodes1.getKey().equals("Data")) {
                                        col_0_obj = nodes1.getValue(new_input_class.class);
                                        col_0_obj.setCol0(n.toString());
                                        objArray.add(col_0_obj);
                                        Log.d("test", "testing: " + col_0_obj.getCol0());

                                    } else if (nodes1.getKey().equals("Seqenceno")) {
                                        seq_no.add(nodes1.getValue().toString());

                                    }
                                }
                            }
                            //}
                            Log.d("list","list of itemcode: "+list_of_item_code);
                            Collections.sort(list_of_item_code, new Comparator<Item_code_obj>() {
                                public int compare(Item_code_obj obj1, Item_code_obj obj2) {
                                    return obj1.sequence.compareToIgnoreCase(obj2.sequence);

                                }
                            });

                            if (list_of_item_code.size() == snapshot.getChildrenCount()) {
                                //////
                                int max_value_no_of_rows=0;
                                for(int i=0;i < list_of_item_code.size();i++){
                                    if(Integer.valueOf(list_of_item_code.get(i).getSequence())>max_value_no_of_rows){
                                        max_value_no_of_rows=Integer.valueOf(list_of_item_code.get(i).getSequence());
                                    }
                                }
                                //max_value_no_of_rows = Integer.valueOf(list_of_item_code.get(list_of_item_code.size() - 1).getSequence());
                                Log.d("list","max rows: "+max_value_no_of_rows);
                                ////
                                for (Item_code_obj j : list_of_item_code) {
                                    list_of_sequence_number.add(Integer.valueOf(j.getSequence()));
                                }
                                HashMap<Integer, Integer> repeated = new HashMap<>();
                                for (Integer r : list_of_sequence_number) {
                                    if (repeated.containsKey(r)) {
                                        repeated.put(r, repeated.get(r) + 1);
                                    } else {
                                        repeated.put(r, 1);
                                    }
                                }
                                for (Map.Entry<Integer, Integer> entry : repeated.entrySet()) {
                                    if (entry.getValue() > 1) {
                                        max_value_no_of_rows += (entry.getValue() - 1);
                                    }

                                }

                                int z = 0;
                                Log.d("list","max rows: "+max_value_no_of_rows);

                                for (int i = 0; i < max_value_no_of_rows; i++) {
                                    row1 = new TableRow(getApplicationContext());
                                    row1.setBaselineAligned(false);
                                    for (int j = 0; j < 14; j++) {
                                        if (j == 0) {
                                            ab = new TextView(getApplicationContext());
                                            // ab.setText(list_of_item_code.get(0).getName());
                                            //Log.d("TAG", "ARRAY: " + list_of_item_code.get(z).getName());
                                            ab.setWidth(200);
                                            ab.setBackgroundResource(R.drawable.border_white);
                                            ab.setTextColor(Color.BLACK);
                                            ab.setGravity(Gravity.CENTER);
                                            ab.setTextSize(font);
                                            ab.setHeight(height);
                                            ab.setTypeface(Typeface.DEFAULT_BOLD);
                                            row1.addView(ab);
                                        }
                                        else if(j==13){
                                            EditText et = new EditText(getApplicationContext());
                                            et.setInputType(InputType.TYPE_CLASS_PHONE);
                                            et.setText("");
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
                                            et.setInputType(InputType.TYPE_CLASS_PHONE);
                                            et.setText("");
                                            et.setWidth(120);
                                            et.setBackgroundResource(R.drawable.border_white);
                                            et.setTextColor(Color.BLACK);
                                            et.setGravity(Gravity.CENTER);
                                            et.setTextSize(font);
                                            et.setHeight(height);
                                            row1.addView(et);
                                        }


                                    }
                                    z++;

                                    table.addView(row1);

                                }
                                int counta =0;
                                int size=table.getChildCount();
                                Log.d("list","size: "+size);
                                for(int i=0; i<list_of_item_code.size(); i++){
                                    Log.d("list","list: "+list_of_item_code.get(i));
                                }
                                for (Item_code_obj jj : list_of_item_code) {
                                    counta = Integer.valueOf(jj.getSequence());
                                    Log.d("list","sequ: "+counta);


                                    while (true) {
                                        Log.d("new", "counta: " + counta);
                                        View view = table.getChildAt(counta);
                                        TableRow r = (TableRow) view;
                                        if (counta <size) {


                                        TextView item_code_box = (TextView) r.getChildAt(0);
                                        if (item_code_box.length() == 0) {
                                            item_code_box.setText(jj.getName());
                                            break;
                                        } else {
                                            Log.d("new", "counta else: " + counta);
                                            counta += 1;
                                        }
                                    }
                                        else{
                                            break;
                                        }
                                    }
                                }
                                for (int i = 1; i < max_value_no_of_rows + 1; i++) {
                                    View view = table.getChildAt(i);
                                    TableRow rowsss = (TableRow) view;
                                    TextView item_code_box = (TextView) rowsss.getChildAt(0);
                                    Log.d("tag", " row: " + item_code_box.getText().toString());
                                    for (Item_code_obj obj : list_of_item_code) {

                                        if (obj.getName().matches(item_code_box.getText().toString())) {
                                            Log.d("tag", " obj name: " + item_code_box.getText().toString());

                                            for (int j = 1; j < 14; j++) {
                                                EditText itemm_code_box = (EditText) rowsss.getChildAt(j);


                                                View vi = table.getChildAt(0);
                                                TableRow ro = (TableRow) vi;
                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                                //Log.d("TAG"," hollla"+item_code_boxx.getText().toString());


                                                itemm_code_box.setTag(obj.getName() + ":" + item_code_boxx.getText().toString());

                                                itemm_code_box.setOnFocusChangeListener(Input.this::onFocusChange);
                                            }
                                        }
                                    }

                                }

                            }
                        }
                        //Log.d("TAG","SIZE OF ARRAY: "+objArray.size());
                        progressDialog2.dismiss();
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






            int count=table.getChildCount();

            Button btn_save = findViewById(R.id.btn_input_save);
            Log.d("TAG","size: "+objArray.size());
            for(int a=0; a<objArray.size(); a++){
                Log.d("TAG","elem: "+ objArray.get(a));
            }


            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog = new ProgressDialog(Input.this);
                    progressDialog.setMessage("Adding Data"); // Setting Message
                    progressDialog.setTitle("Save Button"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog2.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    obj=null;
                    obj11=null;
                    int z = 0;
                    Log.d("gat","New: "+table.getChildCount());
                    for (int i = 1; i < table.getChildCount(); i++) {
                        TableRow nrow = (TableRow) table.getChildAt(i);
                        TextView itemcode = (TextView) nrow.getChildAt(0);
                        if (itemcode.getText().toString().length() > 0) {
                            for (int x = 0; x < objArray.size(); x++) {

                                if (objArray.get(x).getCol0().equals(itemcode.getText())) {
                                    obj = objArray.get(x);
                                    break;
                                }

                            }
                        }
                        if (itemcode.getText().toString().length() > 0) {
                            for (int x = 0; x < pc_array.size(); x++) {

                                if (pc_array.get(x).getCol0().equals(itemcode.getText())) {
                                    obj11 = pc_array.get(x);
                                    break;
                                }

                            }
                        }
                        String item_code = itemcode.getText().toString();

                        if (item_code.length() > 0) {


                        for (int j = 1; j < 14; j++) {


                            // TextView itemcode = (TextView) nrow.getChildAt(0);
                            //String item_code = itemcode.getText().toString();
                            Log.d("Itag", "itemcode: " + item_code);
                            EditText button = (EditText) nrow.getChildAt(j);
                            String data = button.getText().toString();
                            DatabaseReference root = FirebaseDatabase.getInstance().getReference().
                                    child("InputSummary").
                                    child(tv_date.getText().toString());
                            int col_data = 0;
                            String added = null;
                            String bill_added = null;

                            if (obj != null){

                                if (j == 1) {
                                    if (obj != null && !obj.getCol1().equals("")) {
                                        if (!data.equals("")) {
                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol1());
                                            added = String.valueOf(col_data);
                                        } else {
                                            added = obj.getCol1();
                                        }
                                    } else {
                                        added = data;
                                    }
                                }

                                if (j == 2) {

                                    if (obj != null && !obj.getCol2().equals("")) {

                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol2());
                                            added = String.valueOf(col_data);


                                        } else {

                                            added = obj.getCol2();

                                        }

                                    } else {
                                        added = data;

                                    }

                                }


                                if (j == 3) {


                                    if (obj != null && !obj.getCol3().equals("")) {


                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol3());
                                            added = String.valueOf(col_data);


                                        } else {

                                            added = obj.getCol3();

                                        }

                                    } else {
                                        added = data;
                                    }

                                }


                                if (j == 4) {

                                    if (obj != null && !obj.getCol4().equals("")) {


                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol4());
                                            added = String.valueOf(col_data);


                                        } else {

                                            added = obj.getCol4();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 5) {

                                    if (obj != null && !obj.getCol5().equals("")) {


                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol5());
                                            added = String.valueOf(col_data);


                                        } else {

                                            added = obj.getCol5();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 6) {

                                    if (obj != null && !obj.getCol6().equals("")) {


                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol6());
                                            added = String.valueOf(col_data);


                                        } else {

                                            added = obj.getCol6();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 7) {

                                    if (obj != null && !obj.getCol7().equals("")) {


                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol7());
                                            added = String.valueOf(col_data);


                                        } else {

                                            added = obj.getCol7();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 8) {

                                    if (obj != null && !obj.getCol8().equals("")) {


                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol8());
                                            added = String.valueOf(col_data);


                                        } else {

                                            added = obj.getCol8();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 9) {

                                    if (obj != null && !obj.getCol9().equals("")) {


                                        if (!data.equals("")) {

                                            String core_data = obj.getCol9() + "+" + data;
                                            added = core_data;


                                        } else {

                                            added = obj.getCol9();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 10) {

                                    if (obj != null && !obj.getCol10().equals("")) {


                                        if (!data.equals("")) {

                                            String core_data = obj.getCol10() + "+" + data;
                                            added = core_data;


                                        } else {

                                            added = obj.getCol10();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }

                                if (j == 11) {

                                    if (obj != null && !obj.getCol11().equals("")) {


                                        if (!data.equals("")) {

                                            String core_data = obj.getCol11() + "+" + data;
                                            added = core_data;


                                        } else {

                                            added = obj.getCol11();

                                        }

                                    } else {

                                        added = data;
                                    }

                                }


                                if (j == 12) {

                                    if (obj != null && !obj.getCol12().equals("")) {


                                        if (!data.equals("")) {

                                            String core_data = obj.getCol12() + "+" + data;
                                            added = core_data;

                                        } else {

                                            added = obj.getCol12();

                                        }

                                    } else {

                                        added = data;

                                    }

                                }


                                if (j == 13) {

                                    if (obj != null && !obj.getCol13().equals("")) {


                                        if (!data.equals("")) {

                                            String core_data = obj.getCol3() + "+" + data;
                                            added = core_data;


                                        } else {

                                            added = obj.getCol13();

                                        }

                                    } else {
                                        added = data;
                                    }

                                }

                            }

                            if(obj11!=null){
                                Log.d("goshi","obj11 is not null");

                                if (j == 1) {
                                    if (obj11 != null && !obj11.getCol1().equals("")) {
                                        if (!data.equals("")) {
                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol1());
                                            bill_added = String.valueOf(col_data);
                                        } else {
                                            bill_added= obj11.getCol1();
                                        }
                                    } else {
                                        bill_added = data;
                                    }
                                }

                                if (j == 2) {

                                    if (obj11 != null && !obj11.getCol2().equals("")) {

                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol2());
                                            bill_added = String.valueOf(col_data);


                                        } else {

                                            bill_added = obj11.getCol2();

                                        }

                                    } else {
                                        bill_added = data;

                                    }

                                }


                                if (j == 3) {


                                    if (obj11 != null && !obj11.getCol3().equals("")) {


                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol3());
                                            bill_added = String.valueOf(col_data);


                                        } else {

                                            bill_added = obj11.getCol3();

                                        }

                                    } else {
                                        bill_added= data;
                                    }

                                }


                                if (j == 4) {

                                    if (obj11 != null && !obj11.getCol4().equals("")) {


                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol4());
                                            bill_added= String.valueOf(col_data);


                                        } else {

                                            bill_added = obj11.getCol4();

                                        }

                                    } else {

                                        bill_added = data;

                                    }

                                }


                                if (j == 5) {

                                    if (obj11 != null && !obj11.getCol5().equals("")) {


                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol5());
                                            bill_added = String.valueOf(col_data);


                                        } else {

                                            bill_added = obj11.getCol5();

                                        }

                                    } else {

                                        bill_added = data;

                                    }

                                }


                                if (j == 6) {

                                    if (obj11 != null && !obj11.getCol6().equals("")) {


                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol6());
                                            bill_added = String.valueOf(col_data);


                                        } else {

                                            bill_added = obj11.getCol6();

                                        }

                                    } else {

                                        bill_added = data;

                                    }

                                }


                                if (j == 7) {

                                    if (obj11 != null && !obj11.getCol7().equals("")) {


                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol7());
                                            bill_added = String.valueOf(col_data);


                                        } else {

                                            bill_added = obj11.getCol7();

                                        }

                                    } else {

                                        bill_added = data;

                                    }

                                }


                                if (j == 8) {

                                    if (obj11 != null && !obj11.getCol8().equals("")) {


                                        if (!data.equals("")) {

                                            col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol8());
                                            bill_added = String.valueOf(col_data);


                                        } else {

                                            bill_added = obj11.getCol8();

                                        }

                                    } else {

                                        bill_added = data;

                                    }

                                }


                                if (j == 9) {

                                    if (obj11 != null && !obj11.getCol9().equals("")) {


                                        if (!data.equals("")) {

                                            String core_data = obj11.getCol9() + "+" + data;
                                            bill_added = core_data;


                                        } else {

                                            bill_added = obj11.getCol9();

                                        }

                                    } else {

                                        bill_added = data;

                                    }

                                }


                                if (j == 10) {

                                    if (obj11 != null && !obj11.getCol10().equals("")) {


                                        if (!data.equals("")) {

                                            String core_data = obj11.getCol10() + "+" + data;
                                            bill_added = core_data;


                                        } else {

                                            bill_added = obj11.getCol10();

                                        }

                                    } else {

                                        bill_added = data;

                                    }

                                }

                                if (j == 11) {

                                    if (obj11 != null && !obj11.getCol11().equals("")) {


                                        if (!data.equals("")) {

                                            String core_data = obj11.getCol11() + "+" + data;
                                            bill_added = core_data;


                                        } else {

                                            bill_added = obj11.getCol11();

                                        }

                                    } else {

                                        bill_added = data;
                                    }

                                }


                                if (j == 12) {

                                    if (obj11 != null && !obj11.getCol12().equals("")) {


                                        if (!data.equals("")) {

                                            String core_data = obj11.getCol12() + "+" + data;
                                            bill_added = core_data;

                                        } else {

                                            bill_added = obj11.getCol12();

                                        }

                                    } else {

                                        bill_added= data;

                                    }

                                }


                                if (j == 13) {

                                    if (obj11!= null && !obj11.getCol13().equals("")) {


                                        if (!data.equals("")) {

                                            String core_data = obj11.getCol3() + "+" + data;
                                            bill_added = core_data;


                                        } else {

                                            bill_added = obj11.getCol13();

                                        }

                                    } else {
                                        bill_added = data;
                                    }

                                }




                            }
                            if(obj==null){
                                added ="";
                            }
                            if(obj11==null){
                                bill_added=data;
                            }
                            s=item_code.replace("/", "slash");
                            s=s.replace(".", "dot");


                            s=s.replace("#","hash");
                            s=s.replace("$","dollar");
                            s=s.replace("[","openbrace");
                            s=s.replace("]","closebrace");
                            s=s.replace("*","star");

                            if(et_billno.getText().toString().equals("")!=true){




                                root.child(s).child("Data").
                                        child("col" + j).setValue(added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            button.setText("");
                                        }
                                    }
                                });


                                DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().
                                        child("Stock");
                                root1.child(tv_date.getText().toString()).child(s).child("Data").
                                        child("col" + j).setValue(added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            button.setText("");
                                        }
                                    }
                                });
                                DatabaseReference root1_full = FirebaseDatabase.getInstance().getReference().
                                        child("StockFull");
                                root1_full.child(s).child("Data").
                                        child("col" + j).setValue(added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            button.setText("");
                                        }
                                    }
                                });



                                DatabaseReference fuck_you = FirebaseDatabase.getInstance().getReference().
                                        child("StockFull");
                                fuck_you.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for(DataSnapshot node0: snapshot.getChildren()){
                                            if(s.matches(node0.getKey())){

                                                String seq = node0.getValue(Firebase_item_code
                                                        .class).getSeqenceno();

                                                root1.child(tv_date.getText().toString()).child(s).child("Seqenceno").setValue(seq);
                                            }



                                        }



                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });




                                    if (billnum == null) {
                                    DatabaseReference billno = FirebaseDatabase.getInstance().getReference().
                                            child("Billno");
                                    billno.child(et_billno.getText().toString()).child(s).child("Data").
                                            child("col" + j).setValue(bill_added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                button.setText("");


                                            }
                                        }
                                    });
                                    //new change1
                                    billno.child(et_billno.getText().toString()).child(s).child("Date").setValue(tv_date.getText().toString());
                                    int increment = Integer.parseInt(String.valueOf(et_billno.getText())) + 1;
                                    stock_data obj_stock = new stock_data("", "", "", "", "", "", "", "", "", "", "", "", "", "");
                                    billno.child(String.valueOf(increment)).child(s).child("Data").setValue(obj_stock);


                                } else if (billnum != null & et_billno.getText().toString() != "") {
                                    int increment = Integer.parseInt(String.valueOf(et_billno.getText())) + 1;


                                    DatabaseReference billno = FirebaseDatabase.getInstance().getReference().
                                            child("Billno");
                                    billno.child(String.valueOf(increment-1)).child(s).child("Data").
                                            child("col" + j).setValue(bill_added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                button.setText("");

                                            }
                                        }
                                    });
                                    // new change 2
                                        billno.child(String.valueOf(increment-1)).child(s).child("Date").setValue(tv_date.getText().toString());
                                        stock_data obj_stock = new stock_data("", "", "", "", "", "", "", "", "", "", "", "", "", "");
                                    billno.child(String.valueOf(increment)).child(s).child("Data").setValue(obj_stock);


                                } else {
                                    Toast.makeText(Input.this, "Billno is empty", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Input.this, "Billno is empty", Toast.LENGTH_SHORT).show();
                            }



                        }
                    }

                    }
                    progressDialog.dismiss();

                }


            });
            Button btn_add_core= findViewById(R.id.btn_add_core_input);
            btn_add_core.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText editText=(EditText)checking_view;
                    if (editText!=null){
                        if (editText.getText().toString().matches("")){


                            AlertDialog.Builder builder = new AlertDialog.Builder(Input.this);
                            builder.setTitle("Title");

                            final EditText input = new EditText(Input.this);

                            input.setInputType(InputType.TYPE_CLASS_PHONE);
                            builder.setView(input);
                            builder.setTitle("Enter number of core");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    m_Text = input.getText().toString();
                                    String a="90";
                                    for (int i=1;i<Integer.valueOf(m_Text);i++){
                                        a+="+90";

                                    }
                                    editText.setText(a);
                                    //Log.d("tag"," d "+a);
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
                        if(editText.getText().toString().matches("180")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(Input.this);
                            builder.setTitle("Title");

                            final EditText input = new EditText(Input.this);

                            input.setInputType(InputType.TYPE_CLASS_PHONE);
                            builder.setView(input);
                            builder.setTitle("Enter number of core");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    m_Text = input.getText().toString();
                                    String a=editText.getText().toString()+"+90";
                                    for (int i=1;i<Integer.valueOf(m_Text);i++){
                                        a+="+90";

                                    }
                                    editText.setText(a);
                                    //Log.d("tag"," d "+a);
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
                        try {
                            String aa= URLEncoder.encode("+", "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        aa="180+";



                        if(editText.getText().toString().contains(aa)){
                            AlertDialog.Builder builder = new AlertDialog.Builder(Input.this);
                            builder.setTitle("Title");

                            final EditText input = new EditText(Input.this);



                            input.setInputType(InputType.TYPE_CLASS_PHONE);
                            builder.setView(input);
                            builder.setTitle("Enter number of core");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    m_Text = input.getText().toString();
                                    String a=editText.getText().toString()+"+90";
                                    for (int i=1;i<Integer.valueOf(m_Text);i++){
                                        a+="+90";

                                    }
                                    editText.setText(a);
                                    //Log.d("tag"," d "+a);
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
                    }

                    //editText.setText("hi");
                }
            });
        }
        if (orientation1 == Configuration.ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            Button change = findViewById(R.id.btn_change_bill);
            change.setEnabled(false);
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.d("HASH", "bill: " + billnum);

                    if (billnum != "" && billnum != null) {
                        Log.d("HASH", "et: " + et_billno.getText().toString());
                        for (int a = 0; a < list_bill_no.size(); a++) {
                            if (list_bill_no.get(a) != Integer.parseInt(billnum)) {
                                if (list_bill_no.get(a) > Integer.parseInt(et_billno.getText().toString())) {
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    Query applesQuery = ref.child("Billno").child(String.valueOf(list_bill_no.get(a)));

                                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot GroupSnapshot : dataSnapshot.getChildren()) {
                                                GroupSnapshot.getRef().removeValue();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.e(TAG, "onCancelled", databaseError.toException());
                                        }
                                    });
                                }
                            }

                        }


                        DatabaseReference billno_dbs2 = FirebaseDatabase.getInstance().getReference().child("Billno");


                        billno_dbs2.child(billnum).get().addOnSuccessListener(dataSnapshot -> {
                            billno_dbs2.child(et_billno.getText().toString()).setValue(dataSnapshot.getValue());
                            billno_dbs2.child(billnum).removeValue();


                        });


                    } else {
                        Toast.makeText(Input.this, "Bill no cannot be empty", Toast.LENGTH_SHORT).show();
                    }

                }
            });


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
            //tv1.setBackgroundColor(Color.parseColor("#E67F7F"));
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



            et_billno.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(s.toString().equals("")!=true && s.toString()!=null && s.toString().equals(billnum)!=true){

                        Log.d("yasser","1");

                        DatabaseReference billno_dbs1 = FirebaseDatabase.getInstance().getReference().child("Billno").child(s.toString());
                        billno_dbs1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot node : snapshot.getChildren()) {
                                        ArrayList<String> arrayList = new ArrayList<>();
                                        Log.d("gat","node: "+node);
                                        String itemcodeees = String.valueOf(node.getKey());
                                        arrayList.add(itemcodeees);


                                        for (DataSnapshot nodes1 : node.getChildren()) {
                                            if (nodes1.getKey().equals("Data")) {

                                                Log.d("here","here");
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


                                            }



                                        }
                                        rows_list.add(arrayList);

                                    }
                                    int z = 0;
                                    for(int i=0;i<rows_list.size();i++){
                                        Log.d("gat","list: "+ rows_list.get(i));
                                    }
                                    Log.d("gat","table count: "+table.getChildCount());
                                    Log.d("gat","rows list size: "+rows_list.size());
                                    for (int i = 1; i < rows_list.size()+1; i++) {
                                        Log.d("yasser","I am here: "+rows_list.size());


                                        TableRow nrow = (TableRow) table.getChildAt(i);
                                        for (int j = 1; j < 14; j++) {
                                            if(nrow!=null){


                                                if (j == 8) {
                                                    EditText button = (EditText) nrow.getChildAt(j);
                                                    button.setInputType(InputType.TYPE_CLASS_PHONE);
                                                    int sum = string_to_integer(rows_list.get(i-1).get(1)) +
                                                            string_to_integer(rows_list.get(i-1).get(2)) +
                                                            string_to_integer(rows_list.get(i-1).get(3)) +
                                                            string_to_integer(rows_list.get(i-1).get(4)) +
                                                            string_to_integer(rows_list.get(i-1).get(5)) +
                                                            string_to_integer(rows_list.get(i-1).get(6)) +
                                                            string_to_integer(rows_list.get(i-1).get(7));
                                                    button.setText(sum + "");


                                                }
                                                else{

                                                    if(j>=9 && j<14){
                                                        TextView button = (TextView) nrow.getChildAt(j);
                                                        //split(rows_list.get(i).get(j));
                                                        button.setText((rows_list.get(i-1).get(j)));
                                                    }
                                                    else{
                                                        EditText button = (EditText) nrow.getChildAt(j);
                                                        //split(rows_list.get(i).get(j));
                                                        button.setText(rows_list.get(i-1).get(j));


                                                    }

                                                }
                                            }




                                        }
                                        z++;


                                    }

                                }

                                rows_list.clear();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Log.d("last", "not equal");

                    }
                    else if(s.toString().equals(billnum)==true){

                        Log.d("yasser","1");

                        DatabaseReference billno_dbs1 = FirebaseDatabase.getInstance().getReference().child("Billno").child(s.toString());
                        billno_dbs1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot node : snapshot.getChildren()) {
                                        ArrayList<String> arrayList = new ArrayList<>();
                                        Log.d("gat","node: "+node);
                                        String itemcodeees = String.valueOf(node.getKey());
                                        arrayList.add(itemcodeees);



                                        for (DataSnapshot nodes1 : node.getChildren()) {
                                            if (nodes1.getKey().equals("Data")) {

                                                Log.d("here","here");
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


                                            }



                                        }
                                        rows_list.add(arrayList);

                                    }
                                    int z = 0;
                                    for(int i=0;i<rows_list.size();i++){
                                        Log.d("gat","list: "+ rows_list.get(i));
                                    }
                                    Log.d("gat","table count: "+table.getChildCount());
                                    Log.d("gat","rows list size: "+rows_list.size());
                                    for (int i = 1; i < rows_list.size()+1; i++) {
                                        Log.d("yasser","I am here: "+rows_list.size());


                                        TableRow nrow = (TableRow) table.getChildAt(i);
                                        for (int j = 1; j < 14; j++) {
                                            if(nrow!=null){

                                                if (j == 8) {
                                                    EditText button = (EditText) nrow.getChildAt(j);
                                                    button.setInputType(InputType.TYPE_CLASS_PHONE);
                                                    int sum = string_to_integer(rows_list.get(i-1).get(1)) +
                                                            string_to_integer(rows_list.get(i-1).get(2)) +
                                                            string_to_integer(rows_list.get(i-1).get(3)) +
                                                            string_to_integer(rows_list.get(i-1).get(4)) +
                                                            string_to_integer(rows_list.get(i-1).get(5)) +
                                                            string_to_integer(rows_list.get(i-1).get(6)) +
                                                            string_to_integer(rows_list.get(i-1).get(7));
                                                    button.setText(sum + "");


                                                }
                                                else{

                                                    if(j>=9 && j<14){
                                                        TextView button = (TextView) nrow.getChildAt(j);
                                                        //split(rows_list.get(i).get(j));
                                                        button.setText((rows_list.get(i-1).get(j)));
                                                    }
                                                    else{
                                                        EditText button = (EditText) nrow.getChildAt(j);
                                                        //split(rows_list.get(i).get(j));
                                                        button.setText(rows_list.get(i-1).get(j));


                                                    }

                                                }

                                            }





                                        }
                                        z++;


                                    }

                                }

                                rows_list.clear();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Log.d("last", "not equal");

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });






            root = FirebaseDatabase.getInstance().getReference().child("StockFull");
            root.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    objArray.clear();
                    if (snapshot.exists()) {
                        // for(DataSnapshot node0: snapshot.getChildren()){

                        for (DataSnapshot nodes : snapshot.getChildren()) {
                            item_codes_list.add(nodes.getKey());
                            Log.d("date","ITE CODE: "+nodes.getKey());

                            String n = nodes.getKey() .replace("slash", "/");

                            n = n.replace("dot", ".");

                            n=n.replace("hash","#");
                            n=n.replace("dollar","$");
                            n=n.replace("openbrace","[");

                            n=n.replace("closebrace","]");
                            n=n.replace("star","*");


                            list_of_item_code.add(new Item_code_obj(n, nodes.getValue(Firebase_item_code.class).getSeqenceno()));

                            for (DataSnapshot nodes1 : nodes.getChildren()) {
                                if (nodes1.getKey().equals("Data")) {
                                    col_0_obj = nodes1.getValue(new_input_class.class);
                                    col_0_obj.setCol0(n.toString());
                                    objArray.add(col_0_obj);
                                    Log.d("test", "testing: " + col_0_obj.getCol0());

                                } else if (nodes1.getKey().equals("Seqenceno")) {
                                    seq_no.add(nodes1.getValue().toString());

                                }
                            }
                        }
                        //}
                        Log.d("list","list of itemcode: "+list_of_item_code);
                        Collections.sort(list_of_item_code, new Comparator<Item_code_obj>() {
                            public int compare(Item_code_obj obj1, Item_code_obj obj2) {
                                return obj1.sequence.compareToIgnoreCase(obj2.sequence);

                            }
                        });

                        if (list_of_item_code.size() == snapshot.getChildrenCount()) {
                            //////
                            int max_value_no_of_rows=0;
                            for(int i=0;i < list_of_item_code.size();i++){
                                if(Integer.valueOf(list_of_item_code.get(i).getSequence())>max_value_no_of_rows){
                                    max_value_no_of_rows=Integer.valueOf(list_of_item_code.get(i).getSequence());
                                }
                            }
                            //max_value_no_of_rows = Integer.valueOf(list_of_item_code.get(list_of_item_code.size() - 1).getSequence());
                            Log.d("list","max rows: "+max_value_no_of_rows);
                            ////
                            for (Item_code_obj j : list_of_item_code) {
                                list_of_sequence_number.add(Integer.valueOf(j.getSequence()));
                            }
                            HashMap<Integer, Integer> repeated = new HashMap<>();
                            for (Integer r : list_of_sequence_number) {
                                if (repeated.containsKey(r)) {
                                    repeated.put(r, repeated.get(r) + 1);
                                } else {
                                    repeated.put(r, 1);
                                }
                            }
                            for (Map.Entry<Integer, Integer> entry : repeated.entrySet()) {
                                if (entry.getValue() > 1) {
                                    max_value_no_of_rows += (entry.getValue() - 1);
                                }

                            }

                            int z = 0;
                            Log.d("list","max rows: "+max_value_no_of_rows);

                            for (int i = 0; i < max_value_no_of_rows; i++) {
                                row1 = new TableRow(getApplicationContext());
                                row1.setBaselineAligned(false);
                                for (int j = 0; j < 14; j++) {
                                    if (j == 0) {
                                        ab = new TextView(getApplicationContext());
                                        // ab.setText(list_of_item_code.get(0).getName());
                                        //Log.d("TAG", "ARRAY: " + list_of_item_code.get(z).getName());
                                        ab.setWidth(200);
                                        ab.setBackgroundResource(R.drawable.border_white);
                                        ab.setTextColor(Color.BLACK);
                                        ab.setGravity(Gravity.CENTER);
                                        ab.setTextSize(font);
                                        ab.setHeight(height);
                                        ab.setTypeface(Typeface.DEFAULT_BOLD);
                                        row1.addView(ab);
                                    }
                                    else if(j==13){
                                        EditText et = new EditText(getApplicationContext());
                                        et.setInputType(InputType.TYPE_CLASS_PHONE);
                                        et.setText("");
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
                                        et.setInputType(InputType.TYPE_CLASS_PHONE);
                                        et.setText("");
                                        et.setWidth(120);
                                        et.setBackgroundResource(R.drawable.border_white);
                                        et.setTextColor(Color.BLACK);
                                        et.setGravity(Gravity.CENTER);
                                        et.setTextSize(font);
                                        et.setHeight(height);
                                        row1.addView(et);
                                    }


                                }
                                z++;

                                table.addView(row1);

                            }
                            int counta =0;
                            int size=table.getChildCount();
                            Log.d("list","size: "+size);
                            for(int i=0; i<list_of_item_code.size(); i++){
                                Log.d("list","list: "+list_of_item_code.get(i));
                            }
                            for (Item_code_obj jj : list_of_item_code) {
                                counta = Integer.valueOf(jj.getSequence());
                                Log.d("list","sequ: "+counta);


                                while (true) {
                                    Log.d("new", "counta: " + counta);
                                    View view = table.getChildAt(counta);
                                    TableRow r = (TableRow) view;
                                    if (counta <size) {


                                        TextView item_code_box = (TextView) r.getChildAt(0);
                                        if (item_code_box.length() == 0) {
                                            item_code_box.setText(jj.getName());
                                            break;
                                        } else {
                                            Log.d("new", "counta else: " + counta);
                                            counta += 1;
                                        }
                                    }
                                    else{
                                        break;
                                    }
                                }
                            }
                            for (int i = 1; i < max_value_no_of_rows + 1; i++) {
                                View view = table.getChildAt(i);
                                TableRow rowsss = (TableRow) view;
                                TextView item_code_box = (TextView) rowsss.getChildAt(0);
                                Log.d("tag", " row: " + item_code_box.getText().toString());
                                for (Item_code_obj obj : list_of_item_code) {

                                    if (obj.getName().matches(item_code_box.getText().toString())) {
                                        Log.d("tag", " obj name: " + item_code_box.getText().toString());

                                        for (int j = 1; j < 14; j++) {
                                            EditText itemm_code_box = (EditText) rowsss.getChildAt(j);


                                            View vi = table.getChildAt(0);
                                            TableRow ro = (TableRow) vi;
                                            TextView item_code_boxx = (TextView) ro.getChildAt(j);
                                            //Log.d("TAG"," hollla"+item_code_boxx.getText().toString());


                                            itemm_code_box.setTag(obj.getName() + ":" + item_code_boxx.getText().toString());

                                            itemm_code_box.setOnFocusChangeListener(Input.this::onFocusChange);
                                        }
                                    }
                                }

                            }

                        }
                    }
                    //Log.d("TAG","SIZE OF ARRAY: "+objArray.size());
                    progressDialog2.dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });






            int count=table.getChildCount();

            Button btn_save = findViewById(R.id.btn_input_save);
            Log.d("TAG","size: "+objArray.size());
            for(int a=0; a<objArray.size(); a++){
                Log.d("TAG","elem: "+ objArray.get(a));
            }


            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog = new ProgressDialog(Input.this);
                    progressDialog.setMessage("Adding Data"); // Setting Message
                    progressDialog.setTitle("Save Button"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog2.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    obj=null;
                    obj11=null;
                    int z = 0;
                    Log.d("gat","New: "+table.getChildCount());
                    for (int i = 1; i < table.getChildCount(); i++) {
                        TableRow nrow = (TableRow) table.getChildAt(i);
                        TextView itemcode = (TextView) nrow.getChildAt(0);
                        if (itemcode.getText().toString().length() > 0) {
                            for (int x = 0; x < objArray.size(); x++) {

                                if (objArray.get(x).getCol0().equals(itemcode.getText())) {
                                    obj = objArray.get(x);
                                    break;
                                }

                            }
                        }
                        if (itemcode.getText().toString().length() > 0) {
                            for (int x = 0; x < pc_array.size(); x++) {

                                if (pc_array.get(x).getCol0().equals(itemcode.getText())) {
                                    obj11 = pc_array.get(x);
                                    break;
                                }

                            }
                        }
                        String item_code = itemcode.getText().toString();

                        if (item_code.length() > 0) {


                            for (int j = 1; j < 14; j++) {


                                // TextView itemcode = (TextView) nrow.getChildAt(0);
                                //String item_code = itemcode.getText().toString();
                                Log.d("Itag", "itemcode: " + item_code);
                                EditText button = (EditText) nrow.getChildAt(j);
                                String data = button.getText().toString();
                                DatabaseReference root = FirebaseDatabase.getInstance().getReference().
                                        child("InputSummary").
                                        child(tv_date.getText().toString());
                                int col_data = 0;
                                String added = null;
                                String bill_added = null;

                                if (obj != null){

                                    if (j == 1) {
                                        if (obj != null && !obj.getCol1().equals("")) {
                                            if (!data.equals("")) {
                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol1());
                                                added = String.valueOf(col_data);
                                            } else {
                                                added = obj.getCol1();
                                            }
                                        } else {
                                            added = data;
                                        }
                                    }

                                    if (j == 2) {

                                        if (obj != null && !obj.getCol2().equals("")) {

                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol2());
                                                added = String.valueOf(col_data);


                                            } else {

                                                added = obj.getCol2();

                                            }

                                        } else {
                                            added = data;

                                        }

                                    }


                                    if (j == 3) {


                                        if (obj != null && !obj.getCol3().equals("")) {


                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol3());
                                                added = String.valueOf(col_data);


                                            } else {

                                                added = obj.getCol3();

                                            }

                                        } else {
                                            added = data;
                                        }

                                    }


                                    if (j == 4) {

                                        if (obj != null && !obj.getCol4().equals("")) {


                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol4());
                                                added = String.valueOf(col_data);


                                            } else {

                                                added = obj.getCol4();

                                            }

                                        } else {

                                            added = data;

                                        }

                                    }


                                    if (j == 5) {

                                        if (obj != null && !obj.getCol5().equals("")) {


                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol5());
                                                added = String.valueOf(col_data);


                                            } else {

                                                added = obj.getCol5();

                                            }

                                        } else {

                                            added = data;

                                        }

                                    }


                                    if (j == 6) {

                                        if (obj != null && !obj.getCol6().equals("")) {


                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol6());
                                                added = String.valueOf(col_data);


                                            } else {

                                                added = obj.getCol6();

                                            }

                                        } else {

                                            added = data;

                                        }

                                    }


                                    if (j == 7) {

                                        if (obj != null && !obj.getCol7().equals("")) {


                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol7());
                                                added = String.valueOf(col_data);


                                            } else {

                                                added = obj.getCol7();

                                            }

                                        } else {

                                            added = data;

                                        }

                                    }


                                    if (j == 8) {

                                        if (obj != null && !obj.getCol8().equals("")) {


                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj.getCol8());
                                                added = String.valueOf(col_data);


                                            } else {

                                                added = obj.getCol8();

                                            }

                                        } else {

                                            added = data;

                                        }

                                    }


                                    if (j == 9) {

                                        if (obj != null && !obj.getCol9().equals("")) {


                                            if (!data.equals("")) {

                                                String core_data = obj.getCol9() + "+" + data;
                                                added = core_data;


                                            } else {

                                                added = obj.getCol9();

                                            }

                                        } else {

                                            added = data;

                                        }

                                    }


                                    if (j == 10) {

                                        if (obj != null && !obj.getCol10().equals("")) {


                                            if (!data.equals("")) {

                                                String core_data = obj.getCol10() + "+" + data;
                                                added = core_data;


                                            } else {

                                                added = obj.getCol10();

                                            }

                                        } else {

                                            added = data;

                                        }

                                    }

                                    if (j == 11) {

                                        if (obj != null && !obj.getCol11().equals("")) {


                                            if (!data.equals("")) {

                                                String core_data = obj.getCol11() + "+" + data;
                                                added = core_data;


                                            } else {

                                                added = obj.getCol11();

                                            }

                                        } else {

                                            added = data;
                                        }

                                    }


                                    if (j == 12) {

                                        if (obj != null && !obj.getCol12().equals("")) {


                                            if (!data.equals("")) {

                                                String core_data = obj.getCol12() + "+" + data;
                                                added = core_data;

                                            } else {

                                                added = obj.getCol12();

                                            }

                                        } else {

                                            added = data;

                                        }

                                    }


                                    if (j == 13) {

                                        if (obj != null && !obj.getCol13().equals("")) {


                                            if (!data.equals("")) {

                                                String core_data = obj.getCol3() + "+" + data;
                                                added = core_data;


                                            } else {

                                                added = obj.getCol13();

                                            }

                                        } else {
                                            added = data;
                                        }

                                    }

                                }

                                if(obj11!=null){
                                    Log.d("goshi","obj11 is not null");

                                    if (j == 1) {
                                        if (obj11 != null && !obj11.getCol1().equals("")) {
                                            if (!data.equals("")) {
                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol1());
                                                bill_added = String.valueOf(col_data);
                                            } else {
                                                bill_added= obj11.getCol1();
                                            }
                                        } else {
                                            bill_added = data;
                                        }
                                    }

                                    if (j == 2) {

                                        if (obj11 != null && !obj11.getCol2().equals("")) {

                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol2());
                                                bill_added = String.valueOf(col_data);


                                            } else {

                                                bill_added = obj11.getCol2();

                                            }

                                        } else {
                                            bill_added = data;

                                        }

                                    }


                                    if (j == 3) {


                                        if (obj11 != null && !obj11.getCol3().equals("")) {


                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol3());
                                                bill_added = String.valueOf(col_data);


                                            } else {

                                                bill_added = obj11.getCol3();

                                            }

                                        } else {
                                            bill_added= data;
                                        }

                                    }


                                    if (j == 4) {

                                        if (obj11 != null && !obj11.getCol4().equals("")) {


                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol4());
                                                bill_added= String.valueOf(col_data);


                                            } else {

                                                bill_added = obj11.getCol4();

                                            }

                                        } else {

                                            bill_added = data;

                                        }

                                    }


                                    if (j == 5) {

                                        if (obj11 != null && !obj11.getCol5().equals("")) {


                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol5());
                                                bill_added = String.valueOf(col_data);


                                            } else {

                                                bill_added = obj11.getCol5();

                                            }

                                        } else {

                                            bill_added = data;

                                        }

                                    }


                                    if (j == 6) {

                                        if (obj11 != null && !obj11.getCol6().equals("")) {


                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol6());
                                                bill_added = String.valueOf(col_data);


                                            } else {

                                                bill_added = obj11.getCol6();

                                            }

                                        } else {

                                            bill_added = data;

                                        }

                                    }


                                    if (j == 7) {

                                        if (obj11 != null && !obj11.getCol7().equals("")) {


                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol7());
                                                bill_added = String.valueOf(col_data);


                                            } else {

                                                bill_added = obj11.getCol7();

                                            }

                                        } else {

                                            bill_added = data;

                                        }

                                    }


                                    if (j == 8) {

                                        if (obj11 != null && !obj11.getCol8().equals("")) {


                                            if (!data.equals("")) {

                                                col_data = Integer.parseInt(data) + Integer.parseInt(obj11.getCol8());
                                                bill_added = String.valueOf(col_data);


                                            } else {

                                                bill_added = obj11.getCol8();

                                            }

                                        } else {

                                            bill_added = data;

                                        }

                                    }


                                    if (j == 9) {

                                        if (obj11 != null && !obj11.getCol9().equals("")) {


                                            if (!data.equals("")) {

                                                String core_data = obj11.getCol9() + "+" + data;
                                                bill_added = core_data;


                                            } else {

                                                bill_added = obj11.getCol9();

                                            }

                                        } else {

                                            bill_added = data;

                                        }

                                    }


                                    if (j == 10) {

                                        if (obj11 != null && !obj11.getCol10().equals("")) {


                                            if (!data.equals("")) {

                                                String core_data = obj11.getCol10() + "+" + data;
                                                bill_added = core_data;


                                            } else {

                                                bill_added = obj11.getCol10();

                                            }

                                        } else {

                                            bill_added = data;

                                        }

                                    }

                                    if (j == 11) {

                                        if (obj11 != null && !obj11.getCol11().equals("")) {


                                            if (!data.equals("")) {

                                                String core_data = obj11.getCol11() + "+" + data;
                                                bill_added = core_data;


                                            } else {

                                                bill_added = obj11.getCol11();

                                            }

                                        } else {

                                            bill_added = data;
                                        }

                                    }


                                    if (j == 12) {

                                        if (obj11 != null && !obj11.getCol12().equals("")) {


                                            if (!data.equals("")) {

                                                String core_data = obj11.getCol12() + "+" + data;
                                                bill_added = core_data;

                                            } else {

                                                bill_added = obj11.getCol12();

                                            }

                                        } else {

                                            bill_added= data;

                                        }

                                    }


                                    if (j == 13) {

                                        if (obj11!= null && !obj11.getCol13().equals("")) {


                                            if (!data.equals("")) {

                                                String core_data = obj11.getCol3() + "+" + data;
                                                bill_added = core_data;


                                            } else {

                                                bill_added = obj11.getCol13();

                                            }

                                        } else {
                                            bill_added = data;
                                        }

                                    }




                                }
                                if(obj==null){
                                    added ="";
                                }
                                if(obj11==null){
                                    bill_added=data;
                                }


                                //
                                s=item_code.replace("/", "slash");
                                s = s.replace(".", "dot");
                                s=s.replace("#","hash");
                                s=s.replace("$","dollar");
                                s=s.replace("[","openbrace");
                                s=s.replace("]","closebrace");
                                s=s.replace("*","star");

                                if(et_billno.getText().toString().equals("")!=true){




                                    root.child(s).child("Data").
                                            child("col" + j).setValue(added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                button.setText("");
                                            }
                                        }
                                    });


                                    DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().
                                            child("Stock");
                                    root1.child(tv_date.getText().toString()).child(s).child("Data").
                                            child("col" + j).setValue(added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                button.setText("");
                                            }
                                        }
                                    });
                                    DatabaseReference root1_full = FirebaseDatabase.getInstance().getReference().
                                            child("StockFull");
                                    root1_full.child(s).child("Data").
                                            child("col" + j).setValue(added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                button.setText("");
                                            }
                                        }
                                    });



                                    DatabaseReference fuck_you = FirebaseDatabase.getInstance().getReference().
                                            child("StockFull");
                                    fuck_you.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot snapshot) {
                                            for(DataSnapshot node0: snapshot.getChildren()){
                                                if(s.matches(node0.getKey())){

                                                    String seq = node0.getValue(Firebase_item_code
                                                            .class).getSeqenceno();

                                                    root1.child(tv_date.getText().toString()).child(s).child("Seqenceno").setValue(seq);
                                                }



                                            }



                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {

                                        }
                                    });




                                    if (billnum == null) {
                                        DatabaseReference billno = FirebaseDatabase.getInstance().getReference().
                                                child("Billno");
                                        billno.child(et_billno.getText().toString()).child(s).child("Data").
                                                child("col" + j).setValue(bill_added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    button.setText("");


                                                }
                                            }
                                        });
                                        //new change1
                                        billno.child(et_billno.getText().toString()).child(s).child("Date").setValue(tv_date.getText().toString());
                                        int increment = Integer.parseInt(String.valueOf(et_billno.getText())) + 1;
                                        stock_data obj_stock = new stock_data("", "", "", "", "", "", "", "", "", "", "", "", "", "");
                                        billno.child(String.valueOf(increment)).child(s).child("Data").setValue(obj_stock);


                                    } else if (billnum != null & et_billno.getText().toString() != "") {
                                        int increment = Integer.parseInt(String.valueOf(et_billno.getText())) + 1;


                                        DatabaseReference billno = FirebaseDatabase.getInstance().getReference().
                                                child("Billno");
                                        billno.child(String.valueOf(increment-1)).child(s).child("Data").
                                                child("col" + j).setValue(bill_added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    button.setText("");

                                                }
                                            }
                                        });
                                        // new change 2
                                        billno.child(String.valueOf(increment-1)).child(s).child("Date").setValue(tv_date.getText().toString());
                                        stock_data obj_stock = new stock_data("", "", "", "", "", "", "", "", "", "", "", "", "", "");
                                        billno.child(String.valueOf(increment)).child(s).child("Data").setValue(obj_stock);


                                    } else {
                                        Toast.makeText(Input.this, "Billno is empty", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(Input.this, "Billno is empty", Toast.LENGTH_SHORT).show();
                                }



                            }
                        }

                    }
                    progressDialog.dismiss();

                }


            });
            Button btn_add_core= findViewById(R.id.btn_add_core_input);
            btn_add_core.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText editText=(EditText)checking_view;
                    if (editText!=null){
                        if (editText.getText().toString().matches("")){


                            AlertDialog.Builder builder = new AlertDialog.Builder(Input.this);
                            builder.setTitle("Title");

                            final EditText input = new EditText(Input.this);

                            input.setInputType(InputType.TYPE_CLASS_PHONE);
                            builder.setView(input);
                            builder.setTitle("Enter number of core");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    m_Text = input.getText().toString();
                                    String a="90";
                                    for (int i=1;i<Integer.valueOf(m_Text);i++){
                                        a+="+90";

                                    }
                                    editText.setText(a);
                                    //Log.d("tag"," d "+a);
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
                        if(editText.getText().toString().matches("180")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(Input.this);
                            builder.setTitle("Title");

                            final EditText input = new EditText(Input.this);

                            input.setInputType(InputType.TYPE_CLASS_PHONE);
                            builder.setView(input);
                            builder.setTitle("Enter number of core");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    m_Text = input.getText().toString();
                                    String a=editText.getText().toString()+"+90";
                                    for (int i=1;i<Integer.valueOf(m_Text);i++){
                                        a+="+90";

                                    }
                                    editText.setText(a);
                                    //Log.d("tag"," d "+a);
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
                        try {
                            String aa= URLEncoder.encode("+", "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        aa="180+";



                        if(editText.getText().toString().contains(aa)){
                            AlertDialog.Builder builder = new AlertDialog.Builder(Input.this);
                            builder.setTitle("Title");

                            final EditText input = new EditText(Input.this);



                            input.setInputType(InputType.TYPE_CLASS_PHONE);
                            builder.setView(input);
                            builder.setTitle("Enter number of core");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    m_Text = input.getText().toString();
                                    String a=editText.getText().toString()+"+90";
                                    for (int i=1;i<Integer.valueOf(m_Text);i++){
                                        a+="+90";

                                    }
                                    editText.setText(a);
                                    //Log.d("tag"," d "+a);
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
                    }

                    //editText.setText("hi");
                }
            });
        }





    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        checking_view=v;
        EditText vv=(EditText) v;
        //vv.setText("dd");
        // Toast.makeText(getApplicationContext(),vv.getTag().toString(),Toast.LENGTH_SHORT).show();
        TextView gg=findViewById(R.id.textView);
        gg.setText(vv.getTag().toString());


    }


    ArrayList<Integer> split(String aaa){
        core_data.clear();
        String number="";
        ///core_data.add(3);
        if(aaa.matches("")){
            return core_data;
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
        Log.d("tag","funtion call "+core_data.size());
        return core_data;


    }
    int string_to_integer(String s){
        if(s!=null){
            Log.d("sum","s: "+s);
            if (s.matches("")){
                return 0;
            }
            else{
                return Integer.parseInt(s);
            }
        }
        else{
            return 0;
        }


    }
}

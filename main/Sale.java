package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Sale extends AppCompatActivity implements View.OnFocusChangeListener {
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<Integer> menu_array=new ArrayList<Integer>();
    ArrayList<ArrayList<String>> rows_list = new ArrayList<ArrayList<String>>();
    ArrayList<String> company_list = new ArrayList<>();
    ArrayList<String> seq_no = new ArrayList<>();
    public String tagString;
    ProgressDialog progressDialog;
    ProgressDialog progressDialog2;
    DatabaseReference root;
    String m_Text = "";
    public TableRow row;
    int column=0;
    TableLayout table;
    public ArrayList<new_input_class> objArray=new ArrayList<new_input_class>();
    TextView ab;
    View checking_view=null;
    EditText button;
    String data;
    TextView button2;
  
  EditText et;
    public TableRow row1;
    String selected_party=null;

    public new_input_class obj;
    public new_input_class col_0_obj;
    public new_input_class col_0_obj1;
    public new_input_class obj1;
    String billnum;
    EditText et_billno;
    public ArrayList<new_input_class> pc_array=new ArrayList<new_input_class>();
    public int g=1;
    DatePickerDialog.OnDateSetListener setListener;



    ArrayList<Item_code_obj> list_of_item_code= new ArrayList<Item_code_obj>();
    ArrayList<Integer> list_of_sequence_number= new ArrayList<Integer>();
    ArrayList<Integer> list_bill_no= new ArrayList<Integer>();
    ArrayList<String> item_codes_list= new ArrayList<String>();
    private View v;
    public int height=90;
    public int font=11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        getSupportActionBar().setTitle("Sale");
        company_list.add("Select Party");
        progressDialog2 = new ProgressDialog(this);
        progressDialog2.setMessage("Loading"); // Setting Message
        progressDialog2.setTitle("Sale Screen"); // Setting Title
        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog2.setCanceledOnTouchOutside(false);
        progressDialog2.show();


        obj=new new_input_class();


        table = findViewById(R.id.sale_table);
        row = new TableRow(this);
        row.setBaselineAligned(false);

        TextView tv_date=findViewById(R.id.tv_sale_date);
        et_billno=(EditText)findViewById(R.id.et_sale_bill_number);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Sale.this , android.R.style.Theme_Holo_Dialog_MinWidth,setListener,year,month,day);
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

        Spinner spinner=findViewById(R.id.party_spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, company_list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);



        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String millisInString  = dateFormat.format(new Date());
        tv_date.setText(millisInString);



        DatabaseReference partycoding_dbs = FirebaseDatabase.getInstance().getReference().child("PartyCoding");
        partycoding_dbs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                company_list.clear();
                company_list.add("Select Party");
                for(DataSnapshot node: snapshot.getChildren()){

                    company_list.add(node.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference billno_dbs = FirebaseDatabase.getInstance().getReference().child("SaleBill");
        billno_dbs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot node: snapshot.getChildren()){

                    billnum=node.getKey();
                    list_bill_no.add(Integer.parseInt(node.getKey()));
                    Log.d("ahtir","node: "+node.getKey());
                }
                et_billno.setText(billnum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            Button change=findViewById(R.id.sale_btn_change_bill);
            change.setEnabled(false);
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("HASH","bill: "+billnum);

                    if(billnum!="" && billnum!=null){
                        Log.d("HASH","et: "+et_billno.getText().toString());
                        for(int a=0; a<list_bill_no.size(); a++){
                            if(list_bill_no.get(a)!=Integer.parseInt(billnum)){
                                if(list_bill_no.get(a)>Integer.parseInt(et_billno.getText().toString())){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    Query applesQuery = ref.child("SaleBill").child(String.valueOf(list_bill_no.get(a)));

                                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot GroupSnapshot: dataSnapshot.getChildren()) {
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


                        DatabaseReference billno_dbs2 = FirebaseDatabase.getInstance().getReference().child("SaleBill");



                        billno_dbs2.child(billnum).get().addOnSuccessListener(dataSnapshot -> {
                            billno_dbs2.child(et_billno.getText().toString()).setValue(dataSnapshot.getValue());
                            billno_dbs2.child(billnum).removeValue();
                        });


                    }
                    else{
                        Toast.makeText(Sale.this, "Bill no cannot be empty", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item_selected=spinner.getSelectedItem().toString();
                    if(item_selected.equals("Select Party")!=true){
                        selected_party=item_selected;
                        Log.d("nano","selected: "+selected_party);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

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

                        DatabaseReference billno_dbs1 = FirebaseDatabase.getInstance().getReference().child("SaleBill").child(s.toString());
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

                                                if (j == 8) {/*
                                                EditText button = (EditText) nrow.getChildAt(j);
                                                button.setInputType(InputType.TYPE_CLASS_NUMBER);
                                                int sum = string_to_integer(rows_list.get(i-1).get(1)) +
                                                        string_to_integer(rows_list.get(i-1).get(2)) +
                                                        string_to_integer(rows_list.get(i-1).get(3)) +
                                                        string_to_integer(rows_list.get(i-1).get(4)) +
                                                        string_to_integer(rows_list.get(i-1).get(5)) +
                                                        string_to_integer(rows_list.get(i-1).get(6)) +
                                                        string_to_integer(rows_list.get(i-1).get(7));
                                                button.setText(sum + "");*/


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

                        DatabaseReference billno_dbs1 = FirebaseDatabase.getInstance().getReference().child("SaleBill").child(s.toString());
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

                                                if (j == 8) {/*
                                                EditText button = (EditText) nrow.getChildAt(j);
                                                button.setInputType(InputType.TYPE_CLASS_NUMBER);
                                                int sum = string_to_integer(rows_list.get(i-1).get(1)) +
                                                        string_to_integer(rows_list.get(i-1).get(2)) +
                                                        string_to_integer(rows_list.get(i-1).get(3)) +
                                                        string_to_integer(rows_list.get(i-1).get(4)) +
                                                        string_to_integer(rows_list.get(i-1).get(5)) +
                                                        string_to_integer(rows_list.get(i-1).get(6)) +
                                                        string_to_integer(rows_list.get(i-1).get(7));
                                                button.setText(sum + "");
*/

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



            DatabaseReference partycode_dbs=FirebaseDatabase.getInstance().getReference().child("PartyCoding");
            partycode_dbs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    pc_array.clear();
                    if(snapshot.exists()){
                        for(DataSnapshot node: snapshot.getChildren()){
                            Log.d("nano","node "+node.getKey());
                            for(DataSnapshot node1: node.getChildren()){

                                for(DataSnapshot node2:node1.getChildren()){
                                    Log.d("nano","node2 "+node2);
                                    if(node2.getKey().equals("Data")){

                                        col_0_obj=node2.getValue(new_input_class.class);
                                        col_0_obj.setCol0(node1.getKey());
                                        pc_array.add(col_0_obj);

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




            root = FirebaseDatabase.getInstance().getReference().child("StockFull");
            root.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    objArray.clear();
                    if(snapshot.exists()) {
                       // for(DataSnapshot node0: snapshot.getChildren()){

                        for (DataSnapshot nodes : snapshot.getChildren()) {
                            item_codes_list.add(nodes.getKey());
                            String n = nodes.getKey().replace("slash", "/");


                            n = n.replace("dot", ".");

                            n=n.replace("hash","#");
                            n=n.replace("dollar","$");
                            n=n.replace("openbrace","[");

                            n=n.replace("closebrace","]");
                            n=n.replace("star","*");
                            ///
                            ////from hereeee////



                            list_of_item_code.add(new Item_code_obj(n, nodes.getValue(Firebase_item_code
                                    .class).getSeqenceno()));

                            for (DataSnapshot nodes1 : nodes.getChildren()) {


                                if (nodes1.getKey().matches("Data")) {


                                    col_0_obj = nodes1.getValue(new_input_class.class);

                                    col_0_obj.setCol0(n);
                                    //Log.d("hello","data key:  "+col_0_obj.getCol0());
                                    objArray.add(col_0_obj);

                                } else if (nodes1.getKey().equals("Seqenceno")) {
                                    seq_no.add(nodes1.getValue().toString());

                                }
                            }
                        }
                   // }
                        Collections.sort(list_of_item_code, new Comparator<Item_code_obj>(){
                            public int compare(Item_code_obj obj1, Item_code_obj obj2) {
                                return obj1.sequence.compareToIgnoreCase(obj2.sequence);
                            }
                        });

                        if (list_of_item_code.size()==snapshot.getChildrenCount()){
                            int max_value_no_of_rows=0;
                            for(int i=0;i < list_of_item_code.size();i++){
                                if(Integer.valueOf(list_of_item_code.get(i).getSequence())>max_value_no_of_rows){
                                    max_value_no_of_rows=Integer.valueOf(list_of_item_code.get(i).getSequence());
                                }
                            }
                            //int max_value_no_of_rows=Integer.valueOf(list_of_item_code.get(list_of_item_code.size() - 1).getSequence());
                            for(Item_code_obj j : list_of_item_code){
                                list_of_sequence_number.add(Integer.valueOf(j.getSequence()));
                            }
                            HashMap<Integer,Integer> repeated= new HashMap<>();
                            for(Integer r  : list_of_sequence_number) {
                                if(  repeated.containsKey(r)   ) {
                                    repeated.put(r, repeated.get(r) + 1);
                                }
                                else {
                                    repeated.put(r, 1);
                                }
                            }
                            for (Map.Entry<Integer, Integer> entry : repeated.entrySet()) {
                                if (entry.getValue()>1){
                                    max_value_no_of_rows+=(entry.getValue()-1);
                                }

                            }

                            int z = 0;

                            for (int i = 0; i < max_value_no_of_rows; i++) {
                                row1 = new TableRow(getApplicationContext());
                                row1.setBaselineAligned(false);
                                for (int j = 0; j < 14; j++) {
                                    if (j == 0) {
                                        ab = new TextView(getApplicationContext());
                                        ab.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        ab.setWidth(200);
                                        ab.setBackgroundResource(R.drawable.border_white);
                                        ab.setTextColor(Color.BLACK);
                                        ab.setGravity(Gravity.CENTER);
                                        ab.setTextSize(font);
                                        ab.setHeight(height);
                                        ab.setTypeface(Typeface.DEFAULT_BOLD);
                                        row1.addView(ab);
                                    }
                                    else if(j>=9 && j!=13){
                                        ab = new TextView(getApplicationContext());
                                        ab.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        //ab.setText("");
                                        ab.setWidth(120);
                                        ab.setBackgroundResource(R.drawable.border_white);
                                       ab.setTextColor(Color.BLACK);
                                        ab.setGravity(Gravity.CENTER);
                                        ab.setTextSize(font);
                                        ab.setHeight(height);
                                        row1.addView(ab);
                                    }
                                    else if(j==13){
                                        ab = new TextView(getApplicationContext());
                                        ab.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        //ab.setText("");
                                        ab.setWidth(160);
                                        ab.setBackgroundResource(R.drawable.border_white);
                                        ab.setTextColor(Color.BLACK);
                                        ab.setGravity(Gravity.CENTER);
                                        ab.setTextSize(font);
                                        ab.setHeight(height);
                                        row1.addView(ab);
                                    }
                                    else{
                                        et = new EditText(getApplicationContext());
                                        et.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        et.setText("");
                                        et.setWidth(120);
                                        et.setBackgroundResource(R.drawable.border_white);
                                        et.setTextColor(Color.BLACK);
                                        et.setGravity(Gravity.CENTER);
                                        //et.setFocusableInTouchMode();
                                        et.setTextSize(font);
                                        et.setHeight(height);
                                        row1.addView(et);
                                    }



                                }
                                z++;

                                table.addView(row1);

                            }
                            int counta=0;
                            int size=table.getChildCount();
                            for (Item_code_obj jj: list_of_item_code){
                                counta=Integer.valueOf(jj.getSequence());

                                while(true) {
                                    View view = table.getChildAt(counta);
                                    TableRow r = (TableRow) view;
                                    if (counta < size) {



                                    TextView item_code_box = (TextView) r.getChildAt(0);
                                    if (item_code_box.length() == 0) {
                                        item_code_box.setText(jj.getName());
                                        break;
                                    } else {
                                        counta += 1;
                                    }
                                }
                                    else{
                                        break;
                                    }
                                }
                            }
                            for (int i = 1; i < max_value_no_of_rows+1; i++){
                                View view=table.getChildAt(i);
                                TableRow rowsss = (TableRow) view;
                                TextView item_code_box = (TextView) rowsss.getChildAt(0);
                                //Log.d("tag"," row: "+item_code_box.getText().toString());
                                for (Item_code_obj obj: list_of_item_code){

                                    if (obj.getName().matches(item_code_box.getText().toString())){
                                        //Log.d("tag"," obj name: "+item_code_box.getText().toString());

                                        for (int j = 1; j < 14; j++){
                                            if(j<9) {
                                                EditText itemm_code_box = (EditText) rowsss.getChildAt(j);


                                                View vi = table.getChildAt(0);
                                                TableRow ro = (TableRow) vi;
                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);


                                                itemm_code_box.setTag(obj.getName() + ":" + item_code_boxx.getText().toString());

                                            itemm_code_box.setOnFocusChangeListener(Sale.this::onFocusChange);}

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


            int count=table.getChildCount();

            Button btn_save = findViewById(R.id.btn_sale_save);

            Log.d("TAG","size: "+objArray.size());
            for(int a=0; a<objArray.size(); a++){
                Log.d("sex","elem: "+ objArray.get(a));
            }
            //////
            DatabaseReference newroot = FirebaseDatabase.getInstance().getReference().child("StockFull");
            newroot.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        for (DataSnapshot nodes : snapshot.getChildren()) {
                            Log.d("tag", "new node:" + nodes.getKey());
                            String n = nodes.getKey().replace("slash", "/");
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
                                            et_sale.setHint(col_0_obj1.getCol9());
                                           // et_sale.setText(col_0_obj1.getCol9());
                                           et_sale.setOnClickListener(Sale.this::onClick);


                                        } else if (j == 10) {
                                            column = 10;
                                            TextView et_sale = (TextView) nrow.getChildAt(j);
                                            menu_array.clear();
                                            et_sale.setTag("3 core:" + col_0_obj1.getCol10());
                                            et_sale.setHint(col_0_obj1.getCol10());
                                            et_sale.setOnClickListener(Sale.this::onClick);


                                        } else if (j == 11) {
                                            column = 11;
                                            TextView et_sale = (TextView) nrow.getChildAt(j);
                                            menu_array.clear();
                                            et_sale.setTag("4 core:" + col_0_obj1.getCol11());
                                            et_sale.setHint(col_0_obj1.getCol11());

                                            //et_sale.setOnFocusChangeListener(Sale.this::onFocusChange);
                                            et_sale.setOnClickListener(Sale.this::onClick);

                                        } else if (j == 12) {
                                            column = 12;
                                            TextView et_sale = (TextView) nrow.getChildAt(j);
                                            menu_array.clear();
                                            et_sale.setTag("5 core:" + col_0_obj1.getCol12());
                                            et_sale.setHint(col_0_obj1.getCol12());

                                            et_sale.setOnClickListener(Sale.this::onClick);


                                        } else if (j == 13) {
                                            column = 13;
                                            TextView et_sale = (TextView) nrow.getChildAt(j);
                                            menu_array.clear();
                                            et_sale.setTag("6 core:" + col_0_obj1.getCol13());
                                            et_sale.setHint(col_0_obj1.getCol13());


                                            et_sale.setOnClickListener(Sale.this::onClick);


                                        }

                                    }
                                }
                            }
                        }

                    }



                    progressDialog2.dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });









            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog = new ProgressDialog(Sale.this);
                    progressDialog.setMessage("Saling"); // Setting Message
                    progressDialog.setTitle("Sale Button"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    obj=null;
                    obj1=null;
                    Log.d("TAG","count: "+table.getChildCount());
                    if(selected_party!=null){

                    if (et_billno.getText().toString().equals("") != true) {
                        int z=0;
                        int y=0;

                        for (int i = 1; i < table.getChildCount(); i++) {
                            TableRow nrow = (TableRow) table.getChildAt(i);
                            TextView itemcode = (TextView) nrow.getChildAt(0);
                            if (itemcode.getText().toString().length()>0) {
                                for (int x = 0; x < objArray.size(); x++) {

                                    if (objArray.get(x).getCol0().matches(itemcode.getText().toString())) {
                                        Log.d("hello","data key:  "+objArray.get(x).getCol0());
                                        obj = objArray.get(x);
                                        break;
                                    }

                                }
                            }
                            if (itemcode.getText().toString().length()>0) {
                                for (int x = 0; x < pc_array.size(); x++) {
                                    Log.d("nani","pc array: "+pc_array.get(x).getCol0());

                                    if (pc_array.get(x).getCol0().matches(itemcode.getText().toString())) {
                                        obj1 = pc_array.get(x);
                                        break;
                                    }

                                }
                            }



                            for (int j = 1; j < 14; j++) {

                                 itemcode = (TextView) nrow.getChildAt(0);


                                String item_code = itemcode.getText().toString();
                                item_code=item_code.replace("/","slash");
                                item_code=item_code.replace(".","dot");


                                item_code=item_code.replace("#","hash");
                                item_code=item_code.replace("$","dollar");
                                item_code=item_code.replace("[","openbrace");
                                item_code=item_code.replace("]","closebrace");
                                item_code=item_code.replace("*","star");
                                for(int o=0; o<item_codes_list.size(); o++){
                                    Log.d("TAG","list item: "+item_codes_list.get(0).toString());
                                    Log.d("TAG","item code"+item_code);
                                    if(item_code.matches(item_codes_list.get(o))){
                                        g=0;
                                    }
                                }

                                if(item_code.equals("")!=true & g==0){

                                    Log.d("TAG","i: "+i+" "+item_code);
                                    Log.d("nano","size of pc: "+pc_array.size());

                                    if(j<9){
                                        button = (EditText) nrow.getChildAt(j);
                                        data = button.getText().toString();
                                    }
                                    else if(j>=9){
                                      button2= (TextView) nrow.getChildAt(j);
                                      data=button2.getText().toString();
                                    }





                                    DatabaseReference root = FirebaseDatabase.getInstance().getReference().
                                            child("OutputSummary").
                                            child(tv_date.getText().toString());
                                    int col_data = 0;
                                    String added = null;
                                    String party_added=null;
                                    int object=0;
                                    if (obj != null) {

                                        object=0;

                                        if (j == 1) {


                                            if (obj != null && !obj.getCol1().equals("")) {


                                                if (!data.equals("")) {
                                                  if(Integer.parseInt(obj.getCol1()) >= Integer.parseInt(data)){
                                                      col_data =  Integer.parseInt(obj.getCol1())- Integer.parseInt(data) ;
                                                      added = String.valueOf(col_data);
                                                      Log.d("hashh",""+ Integer.parseInt(obj.getCol1()));
                                                      object=1;
                                                  }
                                                  else{


                                                      added=obj.getCol1();
                                                      Log.d("TAG","JANHJEE: "+added);
                                                      Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                  }



                                                } else {


                                                    added = obj.getCol1();

                                                }

                                            } else {


                                                added = obj.getCol1();

                                            }

                                        }


                                        if (j == 2) {


                                            if (obj != null && !obj.getCol2().equals("")) {

                                                if (!data.equals("")) {
                                                    if(Integer.parseInt(obj.getCol2()) >= Integer.parseInt(data)){
                                                        col_data =  Integer.parseInt(obj.getCol2())- Integer.parseInt(data) ;
                                                        added = String.valueOf(col_data);
                                                        object=1;
                                                    }
                                                    else{
                                                        added=obj.getCol2();
                                                        Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                    }



                                                } else {

                                                    added = obj.getCol2();

                                                }

                                            } else {

                                                added = obj.getCol2();

                                            }


                                        }



                                        if (j == 3) {


                                            if (obj != null && !obj.getCol3().equals("")) {


                                                if (!data.equals("")) {
                                                    if(Integer.parseInt(obj.getCol3()) >= Integer.parseInt(data)){
                                                        col_data =  Integer.parseInt(obj.getCol1())- Integer.parseInt(data) ;
                                                        added = String.valueOf(col_data);
                                                        object=1;
                                                    }
                                                    else{
                                                        added=obj.getCol3();
                                                        Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                    }



                                                } else {

                                                    added = obj.getCol3();

                                                }

                                            } else {

                                                added = obj.getCol3();

                                            }

                                        }


                                        if (j == 4) {


                                            if (obj != null && !obj.getCol4().equals("")) {


                                                if (!data.equals("")) {
                                                    if(Integer.parseInt(obj.getCol4()) >= Integer.parseInt(data)){
                                                        col_data =  Integer.parseInt(obj.getCol4())- Integer.parseInt(data) ;
                                                        added = String.valueOf(col_data);
                                                        object=1;
                                                    }
                                                    else{
                                                        added=obj.getCol4();
                                                        Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                    }



                                                } else {

                                                    added = obj.getCol4();

                                                }

                                            } else {

                                                added = obj.getCol4();

                                            }

                                        }


                                        if (j == 5) {


                                            if (obj != null && !obj.getCol5().equals("")) {


                                                if (!data.equals("")) {
                                                    if(Integer.parseInt(obj.getCol5()) >= Integer.parseInt(data)){
                                                        col_data =  Integer.parseInt(obj.getCol5())- Integer.parseInt(data) ;
                                                        added = String.valueOf(col_data);
                                                        object=1;
                                                    }
                                                    else{
                                                        added=obj.getCol5();
                                                        Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                    }



                                                } else {

                                                    added = obj.getCol5();

                                                }

                                            } else {

                                                added = obj.getCol5();

                                            }

                                        }


                                        if (j == 6) {


                                            if (obj != null && !obj.getCol6().equals("")) {


                                                if (!data.equals("")) {
                                                    if(Integer.parseInt(obj.getCol6()) >= Integer.parseInt(data)){
                                                        col_data =  Integer.parseInt(obj.getCol6())- Integer.parseInt(data) ;
                                                        added = String.valueOf(col_data);
                                                        object=1;
                                                    }
                                                    else{
                                                        added=obj.getCol6();
                                                        Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                    }



                                                } else {

                                                    added = obj.getCol6();

                                                }

                                            } else {

                                                added = obj.getCol6();

                                            }

                                        }


                                        if (j == 7) {


                                            if (obj != null && !obj.getCol7().equals("")) {


                                                if (!data.equals("")) {
                                                    if(Integer.parseInt(obj.getCol7()) >= Integer.parseInt(data)){
                                                        col_data =  Integer.parseInt(obj.getCol7())- Integer.parseInt(data) ;
                                                        added = String.valueOf(col_data);
                                                        object=1;
                                                    }
                                                    else{
                                                        added=obj.getCol7();
                                                        Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                    }



                                                } else {


                                                    added = obj.getCol7();

                                                }

                                            } else {

                                                added = obj.getCol7();

                                            }

                                        }


                                        if (j == 8) {


                                            if (obj != null && !obj.getCol8().equals("")) {


                                                if (!data.equals("")) {
                                                    if(Integer.parseInt(obj.getCol8()) >= Integer.parseInt(data)){
                                                        col_data =  Integer.parseInt(obj.getCol8())- Integer.parseInt(data) ;
                                                        added = String.valueOf(col_data);
                                                        object=1;
                                                    }
                                                    else{
                                                        added=obj.getCol8();
                                                        Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                    }



                                                } else {

                                                    added = obj.getCol8();

                                                }

                                            } else {

                                                added = obj.getCol8();

                                            }

                                        }



                                        if (j == 9) {


                                            if (obj != null && !obj.getCol9().equals("")) {

                                            }
                                                if (!data.equals("")) {
                                                    String aaa=obj.getCol9();
                                                    ArrayList<Integer> temp=new ArrayList<Integer>();

                                                    String number="";
                                                    if(aaa.contains("90")||aaa.contains("180")){


                                                        for (int a=0;a<aaa.length();a++){
                                                            Character value=aaa.charAt(a);

                                                            if("0123456789".indexOf(value) != -1){
                                                                number+=value;
                                                            }
                                                            else{

                                                               temp.add(Integer.valueOf(number));
                                                                number="";
                                                            }

                                                        }
                                                       temp.add(Integer.valueOf(number));
                                                        String min=null;
                                                        added="";
                                                        for(int b=0; b<temp.size();b++){
                                                            if(Integer.parseInt(data)<=temp.get(b)){
                                                                min="ok";
                                                                col_data =  temp.get(b)- Integer.parseInt(data) ;
                                                                temp.set(b, col_data);
                                                                for(int n=0; n<temp.size();n++){
                                                                    if(added.equals("")){
                                                                        added= added+temp.get(n);
                                                                    }
                                                                    else{
                                                                        added= added+"+"+temp.get(n);
                                                                    }

                                                                }
                                                                Log.d("goshi","added: "+added);
                                                                object=1;
                                                                break;
                                                            }
                                                        }




                                                        if(min==null){
                                                        added=obj.getCol9();
                                                        Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                    }



                                                } else {

                                                    added = obj.getCol9();

                                                }

                                            } else {

                                                added = obj.getCol9();

                                            }

                                        }



                                        if (j == 10) {


                                            if (obj != null && !obj.getCol10().equals("")) {

                                            }
                                            if (!data.equals("")) {
                                                String aaa=obj.getCol10();
                                                ArrayList<Integer> temp=new ArrayList<Integer>();

                                                String number="";
                                                if(aaa.contains("90")||aaa.contains("180")){


                                                    for (int a=0;a<aaa.length();a++){
                                                        Character value=aaa.charAt(a);

                                                        if("0123456789".indexOf(value) != -1){
                                                            number+=value;
                                                        }
                                                        else{

                                                            temp.add(Integer.valueOf(number));
                                                            number="";
                                                        }

                                                    }
                                                    temp.add(Integer.valueOf(number));
                                                    String min=null;
                                                    added="";
                                                    for(int b=0; b<temp.size();b++){
                                                        if(Integer.parseInt(data)<=temp.get(b)){
                                                            min="ok";
                                                            col_data =  temp.get(b)- Integer.parseInt(data) ;
                                                            temp.set(b, col_data);
                                                            for(int n=0; n<temp.size();n++){
                                                                if(added.equals("")){
                                                                    added= added+temp.get(n);
                                                                }
                                                                else{
                                                                    added= added+"+"+temp.get(n);
                                                                }

                                                            }
                                                            Log.d("goshi","added: "+added);
                                                            object=1;
                                                            break;
                                                        }
                                                    }




                                                    if(min==null){
                                                        added=obj.getCol10();
                                                        Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                    }



                                                } else {

                                                    added = obj.getCol10();

                                                }

                                            } else {

                                                added = obj.getCol10();

                                            }

                                        }

                                        if (j == 11) {


                                            if (obj != null && !obj.getCol11().equals("")) {

                                            }
                                            if (!data.equals("")) {
                                                String aaa=obj.getCol11();
                                                ArrayList<Integer> temp=new ArrayList<Integer>();

                                                String number="";
                                                if(aaa.contains("90")||aaa.contains("180")){


                                                    for (int a=0;a<aaa.length();a++){
                                                        Character value=aaa.charAt(a);

                                                        if("0123456789".indexOf(value) != -1){
                                                            number+=value;
                                                        }
                                                        else{

                                                            temp.add(Integer.valueOf(number));
                                                            number="";
                                                        }

                                                    }
                                                    temp.add(Integer.valueOf(number));
                                                    String min=null;
                                                    added="";
                                                    for(int b=0; b<temp.size();b++){
                                                        if(Integer.parseInt(data)<=temp.get(b)){
                                                            min="ok";
                                                            col_data =  temp.get(b)- Integer.parseInt(data) ;
                                                            temp.set(b, col_data);
                                                            for(int n=0; n<temp.size();n++){
                                                                if(added.equals("")){
                                                                    added= added+temp.get(n);
                                                                }
                                                                else{
                                                                    added= added+"+"+temp.get(n);
                                                                }

                                                            }
                                                            Log.d("goshi","added: "+added);
                                                            object=1;
                                                            break;
                                                        }
                                                    }




                                                    if(min==null){
                                                        added=obj.getCol11();
                                                        Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                    }



                                                } else {

                                                    added = obj.getCol11();

                                                }

                                            } else {

                                                added = obj.getCol11();

                                            }

                                        }


                                        if (j == 12) {


                                            if (obj != null && !obj.getCol12().equals("")) {

                                            }
                                            if (!data.equals("")) {
                                                String aaa=obj.getCol12();
                                                ArrayList<Integer> temp=new ArrayList<Integer>();

                                                String number="";
                                                if(aaa.contains("90")||aaa.contains("180")){


                                                    for (int a=0;a<aaa.length();a++){
                                                        Character value=aaa.charAt(a);

                                                        if("0123456789".indexOf(value) != -1){
                                                            number+=value;
                                                        }
                                                        else{

                                                            temp.add(Integer.valueOf(number));
                                                            number="";
                                                        }

                                                    }
                                                    temp.add(Integer.valueOf(number));
                                                    String min=null;
                                                    added="";
                                                    for(int b=0; b<temp.size();b++){
                                                        if(Integer.parseInt(data)<=temp.get(b)){
                                                            min="ok";
                                                            col_data =  temp.get(b)- Integer.parseInt(data) ;
                                                            temp.set(b, col_data);
                                                            for(int n=0; n<temp.size();n++){
                                                                if(added.equals("")){
                                                                    added= added+temp.get(n);
                                                                }
                                                                else{
                                                                    added= added+"+"+temp.get(n);
                                                                }

                                                            }
                                                            Log.d("goshi","added: "+added);
                                                            object=1;
                                                            break;
                                                        }
                                                    }




                                                    if(min==null){
                                                        added=obj.getCol12();
                                                        Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                    }



                                                } else {

                                                    added = obj.getCol12();

                                                }

                                            } else {

                                                added = obj.getCol12();

                                            }

                                        }


                                        if (j == 13) {


                                            if (obj != null && !obj.getCol13().equals("")) {

                                            }
                                            if (!data.equals("")) {
                                                String aaa=obj.getCol13();
                                                ArrayList<Integer> temp=new ArrayList<Integer>();

                                                String number="";
                                                if(aaa.contains("90")||aaa.contains("180")){


                                                    for (int a=0;a<aaa.length();a++){
                                                        Character value=aaa.charAt(a);

                                                        if("0123456789".indexOf(value) != -1){
                                                            number+=value;
                                                        }
                                                        else{

                                                            temp.add(Integer.valueOf(number));
                                                            number="";
                                                        }

                                                    }
                                                    temp.add(Integer.valueOf(number));
                                                    String min=null;
                                                    added="";
                                                    for(int b=0; b<temp.size();b++){
                                                        if(Integer.parseInt(data)<=temp.get(b)){
                                                            min="ok";
                                                            col_data =  temp.get(b)- Integer.parseInt(data) ;
                                                            temp.set(b, col_data);
                                                            for(int n=0; n<temp.size();n++){
                                                                if(added.equals("")){
                                                                    added= added+temp.get(n);
                                                                }
                                                                else{
                                                                    added= added+"+"+temp.get(n);
                                                                }

                                                            }
                                                            Log.d("goshi","added: "+added);
                                                            object=1;
                                                            break;
                                                        }
                                                    }




                                                    if(min==null){
                                                        added=obj.getCol13();
                                                        Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                    }



                                                } else {

                                                    added = obj.getCol13();

                                                }

                                            } else {

                                                added = obj.getCol13();

                                            }

                                        }



                                    }
                                    if(obj1!=null){
                                        Log.d("nano","done");

                                        if (j == 1) {


                                            if (obj1 != null && !obj1.getCol1().equals("")) {


                                                if (!data.equals("")) {



                                                    col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol1());
                                                   party_added = String.valueOf(col_data);


                                                } else {

                                                    party_added= obj1.getCol1();

                                                }

                                            } else {

                                                party_added = data;

                                            }

                                        }


                                        if (j == 2) {

                                            if (obj1 != null && !obj1.getCol2().equals("")) {

                                                if (!data.equals("")) {

                                                    col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol2());
                                                    party_added = String.valueOf(col_data);


                                                } else {

                                                  party_added= obj1.getCol2();

                                                }

                                            } else {

                                               party_added = data;

                                            }

                                        }


                                        if (j == 3) {


                                            if (obj1 != null && !obj1.getCol3().equals("")) {


                                                if (!data.equals("")) {

                                                    col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol3());
                                                   party_added= String.valueOf(col_data);


                                                } else {

                                                    added = obj1.getCol3();

                                                }

                                            } else {
                                                party_added = data;
                                            }

                                        }


                                        if (j == 4) {

                                            if (obj1 != null && !obj1.getCol4().equals("")) {


                                                if (!data.equals("")) {

                                                    col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol4());
                                                   party_added = String.valueOf(col_data);


                                                } else {

                                                   party_added = obj1.getCol4();

                                                }

                                            } else {

                                                party_added = data;

                                            }

                                        }


                                        if (j == 5) {

                                            if (obj1 != null && !obj1.getCol5().equals("")) {


                                                if (!data.equals("")) {

                                                    col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol5());
                                                    party_added = String.valueOf(col_data);


                                                } else {

                                                 party_added= obj1.getCol5();

                                                }

                                            } else {

                                               party_added = data;

                                            }

                                        }


                                        if (j == 6) {

                                            if (obj1 != null && !obj1.getCol6().equals("")) {


                                                if (!data.equals("")) {

                                                    col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol6());
                                                    party_added = String.valueOf(col_data);


                                                } else {

                                                   party_added = obj1.getCol6();

                                                }

                                            } else {

                                               party_added = data;

                                            }

                                        }


                                        if (j == 7) {

                                            if (obj1 != null && !obj1.getCol7().equals("")) {


                                                if (!data.equals("")) {

                                                    col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol7());
                                                 party_added = String.valueOf(col_data);


                                                } else {

                                                   party_added = obj1.getCol7();

                                                }

                                            } else {

                                               party_added = data;

                                            }

                                        }


                                        if (j == 8) {

                                            if (obj1 != null && !obj1.getCol8().equals("")) {


                                                if (!data.equals("")) {

                                                    col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol8());
                                                    added = String.valueOf(col_data);


                                                } else {

                                                    party_added = obj1.getCol8();

                                                }

                                            } else {

                                               party_added = data;

                                            }

                                        }



                                        if (j == 9) {

                                            if (obj1 != null && !obj1.getCol9().equals("")) {


                                                if (!data.equals("")) {

                                                    col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol9());
                                                   party_added = String.valueOf(col_data);


                                                } else {

                                                    party_added = obj1.getCol9();

                                                }

                                            } else {

                                               party_added = data;

                                            }

                                        }


                                        if (j == 10) {

                                            if (obj1 != null && !obj1.getCol10().equals("")) {


                                                if (!data.equals("")) {

                                                    col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol10());
                                                    party_added = String.valueOf(col_data);


                                                } else {

                                                   party_added = obj1.getCol10();

                                                }

                                            } else {

                                               party_added = data;

                                            }

                                        }

                                        if (j == 11) {

                                            if (obj1 != null && !obj1.getCol11().equals("")) {


                                                if (!data.equals("")) {

                                                    col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol11());
                                                   party_added = String.valueOf(col_data);


                                                } else {

                                                    party_added= obj1.getCol11();

                                                }

                                            } else {

                                               party_added= data;

                                            }

                                        }


                                        if (j == 12) {

                                            if (obj1 != null && !obj1.getCol12().equals("")) {


                                                if (!data.equals("")) {

                                                    col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol12());
                                                    party_added = String.valueOf(col_data);


                                                } else {

                                                   party_added = obj1.getCol12();

                                                }

                                            } else {

                                              party_added = data;

                                            }

                                        }


                                        if (j == 13) {

                                            if (obj1 != null && !obj1.getCol13().equals("")) {


                                                if (!data.equals("")) {

                                                    col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol13());
                                                    party_added = String.valueOf(col_data);


                                                } else {

                                                party_added= obj1.getCol13();

                                                }

                                            } else {

                                               party_added = data;

                                            }

                                        }

                                    }

                                    if(obj==null){
                                        added ="";
                                    }
                                    if(obj1==null){
                                        Log.d("rana","null dikha arhaa hai");
                                        party_added=data;
                                    }
                                    //Log.d("hassan","DATA: "+data);
                                    if(j>=9){
                                        button2.setText("");
                                    }
                                    if(j<9){
                                        button.setText("");
                                    }



                                    root.child(item_code).child("Data").
                                            child("col" + j).setValue(party_added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                            }
                                        }
                                    });
                                    Log.d("haha","party added: "+party_added +"j "+j);
                                 //   if(object==1){
                                        DatabaseReference party=FirebaseDatabase.getInstance().getReference().child("PartyCoding");
                                        party.child(selected_party).child(item_code).child("Data").child("col" + j).setValue(party_added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                }


                                            }
                                        });
                                    party.child(selected_party).child(item_code).child("Date").setValue(tv_date.getText().toString());


                                    // }







                                    if (billnum == null) {
                                        DatabaseReference billno = FirebaseDatabase.getInstance().getReference().
                                                child("SaleBill");
                                        billno.child(et_billno.getText().toString()).child(item_code).child("Data").
                                                child("col" + j).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    button.setText("");


                                                }
                                            }
                                        });

                                        billno.child(et_billno.getText().toString()).child(item_code).child("Date").setValue(tv_date.getText().toString());
                                        billno.child(et_billno.getText().toString()).child(item_code).child("Party").setValue(selected_party);
                                        int increment = Integer.parseInt(String.valueOf(et_billno.getText())) + 1;
                                        stock_data obj_stock = new stock_data("", "", "", "", "", "", "", "", "", "", "", "", "", "");
                                        billno.child(String.valueOf(increment)).child(item_code).child("Data").setValue(obj_stock);


                                    } else if (billnum != null & et_billno.getText().toString() != "") {
                                        int increment = Integer.parseInt(String.valueOf(et_billno.getText())) + 1;


                                        DatabaseReference billno = FirebaseDatabase.getInstance().getReference().
                                                child("SaleBill");
                                        billno.child(String.valueOf(increment-1)).child(item_code).child("Data").
                                                child("col" + j).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    button.setText("");

                                                }
                                            }
                                        });
                                        billno.child(String.valueOf(increment-1)).child(item_code).child("Date").setValue(tv_date.getText().toString());
                                        billno.child(et_billno.getText().toString()).child(item_code).child("Party").setValue(selected_party);
                                        stock_data obj_stock = new stock_data("", "", "", "", "", "", "", "", "", "", "", "", "", "");
                                        billno.child(String.valueOf(increment)).child(item_code).child("Data").setValue(obj_stock);


                                    } else {
                                        Toast.makeText(Sale.this, "Billno is empty", Toast.LENGTH_SHORT).show();
                                    }



                                    DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().
                                            child("Stock").child(tv_date.getText().toString());
                                    root1.child(item_code).child("Data").
                                            child("col" + j).setValue(added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                            }
                                        }
                                    });
                                    root1.child(item_code).child("Seqenceno").setValue(String.valueOf(i));

                                    DatabaseReference root1_full = FirebaseDatabase.getInstance().getReference().
                                            child("StockFull");
                                    root1_full.child(item_code).child("Data").
                                            child("col" + j).setValue(added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                            }
                                        }
                                    });
                                    root1_full.child(item_code).child("Seqenceno").setValue(String.valueOf(i));



                                }

                            }
                        }





                    }
                    else{ et_billno.setError("Bill no cannot be empty");


                    }
                }
                    else{
                        Toast.makeText(Sale.this, "select party", Toast.LENGTH_SHORT).show();
                    }



                    progressDialog.dismiss();
                    Toast.makeText(Sale.this, "Saled", Toast.LENGTH_SHORT).show();
                }




            });
        }
        if (orientation1 == Configuration.ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            Button change=findViewById(R.id.sale_btn_change_bill);
            change.setEnabled(false);
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("HASH","bill: "+billnum);

                    if(billnum!="" && billnum!=null){
                        Log.d("HASH","et: "+et_billno.getText().toString());
                        for(int a=0; a<list_bill_no.size(); a++){
                            if(list_bill_no.get(a)!=Integer.parseInt(billnum)){
                                if(list_bill_no.get(a)>Integer.parseInt(et_billno.getText().toString())){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    Query applesQuery = ref.child("SaleBill").child(String.valueOf(list_bill_no.get(a)));

                                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot GroupSnapshot: dataSnapshot.getChildren()) {
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


                        DatabaseReference billno_dbs2 = FirebaseDatabase.getInstance().getReference().child("SaleBill");



                        billno_dbs2.child(billnum).get().addOnSuccessListener(dataSnapshot -> {
                            billno_dbs2.child(et_billno.getText().toString()).setValue(dataSnapshot.getValue());
                            billno_dbs2.child(billnum).removeValue();
                        });


                    }
                    else{
                        Toast.makeText(Sale.this, "Bill no cannot be empty", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item_selected=spinner.getSelectedItem().toString();
                    if(item_selected.equals("Select Party")!=true){
                        selected_party=item_selected;
                        Log.d("nano","selected: "+selected_party);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

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

                        DatabaseReference billno_dbs1 = FirebaseDatabase.getInstance().getReference().child("SaleBill").child(s.toString());
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

                                                if (j == 8) {/*
                                                EditText button = (EditText) nrow.getChildAt(j);
                                                button.setInputType(InputType.TYPE_CLASS_NUMBER);
                                                int sum = string_to_integer(rows_list.get(i-1).get(1)) +
                                                        string_to_integer(rows_list.get(i-1).get(2)) +
                                                        string_to_integer(rows_list.get(i-1).get(3)) +
                                                        string_to_integer(rows_list.get(i-1).get(4)) +
                                                        string_to_integer(rows_list.get(i-1).get(5)) +
                                                        string_to_integer(rows_list.get(i-1).get(6)) +
                                                        string_to_integer(rows_list.get(i-1).get(7));
                                                button.setText(sum + "");*/


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

                        DatabaseReference billno_dbs1 = FirebaseDatabase.getInstance().getReference().child("SaleBill").child(s.toString());
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

                                                if (j == 8) {/*
                                                EditText button = (EditText) nrow.getChildAt(j);
                                                button.setInputType(InputType.TYPE_CLASS_NUMBER);
                                                int sum = string_to_integer(rows_list.get(i-1).get(1)) +
                                                        string_to_integer(rows_list.get(i-1).get(2)) +
                                                        string_to_integer(rows_list.get(i-1).get(3)) +
                                                        string_to_integer(rows_list.get(i-1).get(4)) +
                                                        string_to_integer(rows_list.get(i-1).get(5)) +
                                                        string_to_integer(rows_list.get(i-1).get(6)) +
                                                        string_to_integer(rows_list.get(i-1).get(7));
                                                button.setText(sum + "");
*/

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



            DatabaseReference partycode_dbs=FirebaseDatabase.getInstance().getReference().child("PartyCoding");
            partycode_dbs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    pc_array.clear();
                    if(snapshot.exists()){
                        for(DataSnapshot node: snapshot.getChildren()){
                            Log.d("nano","node "+node.getKey());
                            for(DataSnapshot node1: node.getChildren()){

                                for(DataSnapshot node2:node1.getChildren()){
                                    Log.d("nano","node2 "+node2);
                                    if(node2.getKey().equals("Data")){

                                        col_0_obj=node2.getValue(new_input_class.class);
                                        col_0_obj.setCol0(node1.getKey());
                                        pc_array.add(col_0_obj);

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




            root = FirebaseDatabase.getInstance().getReference().child("StockFull");
            root.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    objArray.clear();
                    if(snapshot.exists()) {
                        // for(DataSnapshot node0: snapshot.getChildren()){

                        for (DataSnapshot nodes : snapshot.getChildren()) {
                            item_codes_list.add(nodes.getKey());
                            String n = nodes.getKey().replace("slash", "/");
                            n = n.replace("o", ".");


                            n=n.replace("hash","#");
                            n=n.replace("dollar","$");
                            n=n.replace("openbrace","[");

                            n=n.replace("closebrace","]");
                            n=n.replace("star","*");

                            list_of_item_code.add(new Item_code_obj(n, nodes.getValue(Firebase_item_code
                                    .class).getSeqenceno()));

                            for (DataSnapshot nodes1 : nodes.getChildren()) {


                                if (nodes1.getKey().matches("Data")) {


                                    col_0_obj = nodes1.getValue(new_input_class.class);

                                    col_0_obj.setCol0(n);
                                    //Log.d("hello","data key:  "+col_0_obj.getCol0());
                                    objArray.add(col_0_obj);

                                } else if (nodes1.getKey().equals("Seqenceno")) {
                                    seq_no.add(nodes1.getValue().toString());

                                }
                            }
                        }
                        // }
                        Collections.sort(list_of_item_code, new Comparator<Item_code_obj>(){
                            public int compare(Item_code_obj obj1, Item_code_obj obj2) {
                                return obj1.sequence.compareToIgnoreCase(obj2.sequence);
                            }
                        });

                        if (list_of_item_code.size()==snapshot.getChildrenCount()){
                            int max_value_no_of_rows=0;
                            for(int i=0;i < list_of_item_code.size();i++){
                                if(Integer.valueOf(list_of_item_code.get(i).getSequence())>max_value_no_of_rows){
                                    max_value_no_of_rows=Integer.valueOf(list_of_item_code.get(i).getSequence());
                                }
                            }
                            //int max_value_no_of_rows=Integer.valueOf(list_of_item_code.get(list_of_item_code.size() - 1).getSequence());
                            for(Item_code_obj j : list_of_item_code){
                                list_of_sequence_number.add(Integer.valueOf(j.getSequence()));
                            }
                            HashMap<Integer,Integer> repeated= new HashMap<>();
                            for(Integer r  : list_of_sequence_number) {
                                if(  repeated.containsKey(r)   ) {
                                    repeated.put(r, repeated.get(r) + 1);
                                }
                                else {
                                    repeated.put(r, 1);
                                }
                            }
                            for (Map.Entry<Integer, Integer> entry : repeated.entrySet()) {
                                if (entry.getValue()>1){
                                    max_value_no_of_rows+=(entry.getValue()-1);
                                }

                            }

                            int z = 0;

                            for (int i = 0; i < max_value_no_of_rows; i++) {
                                row1 = new TableRow(getApplicationContext());
                                row1.setBaselineAligned(false);
                                for (int j = 0; j < 14; j++) {
                                    if (j == 0) {
                                        ab = new TextView(getApplicationContext());
                                        ab.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        ab.setWidth(200);
                                        ab.setBackgroundResource(R.drawable.border_white);
                                        ab.setTextColor(Color.BLACK);
                                        ab.setGravity(Gravity.CENTER);
                                        ab.setTextSize(font);
                                        ab.setHeight(height);
                                        ab.setTypeface(Typeface.DEFAULT_BOLD);
                                        row1.addView(ab);
                                    }
                                    else if(j>=9 && j!=13){
                                        ab = new TextView(getApplicationContext());
                                        ab.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        //ab.setText("");
                                        ab.setWidth(120);
                                        ab.setBackgroundResource(R.drawable.border_white);
                                        ab.setTextColor(Color.BLACK);
                                        ab.setGravity(Gravity.CENTER);
                                        ab.setTextSize(font);
                                        ab.setHeight(height);
                                        row1.addView(ab);
                                    }
                                    else if(j==13){
                                        ab = new TextView(getApplicationContext());
                                        ab.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        //ab.setText("");
                                        ab.setWidth(160);
                                        ab.setBackgroundResource(R.drawable.border_white);
                                        ab.setTextColor(Color.BLACK);
                                        ab.setGravity(Gravity.CENTER);
                                        ab.setTextSize(font);
                                        ab.setHeight(height);
                                        row1.addView(ab);
                                    }
                                    else{
                                        et = new EditText(getApplicationContext());
                                        et.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        et.setText("");
                                        et.setWidth(120);
                                        et.setBackgroundResource(R.drawable.border_white);
                                        et.setTextColor(Color.BLACK);
                                        et.setGravity(Gravity.CENTER);
                                        //et.setFocusableInTouchMode();
                                        et.setTextSize(font);
                                        et.setHeight(height);
                                        row1.addView(et);
                                    }



                                }
                                z++;

                                table.addView(row1);

                            }
                            int counta=0;
                            int size=table.getChildCount();
                            for (Item_code_obj jj: list_of_item_code){
                                counta=Integer.valueOf(jj.getSequence());

                                while(true) {
                                    View view = table.getChildAt(counta);
                                    TableRow r = (TableRow) view;
                                    if (counta < size) {



                                        TextView item_code_box = (TextView) r.getChildAt(0);
                                        if (item_code_box.length() == 0) {
                                            item_code_box.setText(jj.getName());
                                            break;
                                        } else {
                                            counta += 1;
                                        }
                                    }
                                    else{
                                        break;
                                    }
                                }
                            }
                            for (int i = 1; i < max_value_no_of_rows+1; i++){
                                View view=table.getChildAt(i);
                                TableRow rowsss = (TableRow) view;
                                TextView item_code_box = (TextView) rowsss.getChildAt(0);
                                //Log.d("tag"," row: "+item_code_box.getText().toString());
                                for (Item_code_obj obj: list_of_item_code){

                                    if (obj.getName().matches(item_code_box.getText().toString())){
                                        //Log.d("tag"," obj name: "+item_code_box.getText().toString());

                                        for (int j = 1; j < 14; j++){
                                            if(j<9) {
                                                EditText itemm_code_box = (EditText) rowsss.getChildAt(j);


                                                View vi = table.getChildAt(0);
                                                TableRow ro = (TableRow) vi;
                                                TextView item_code_boxx = (TextView) ro.getChildAt(j);


                                                itemm_code_box.setTag(obj.getName() + ":" + item_code_boxx.getText().toString());

                                                itemm_code_box.setOnFocusChangeListener(Sale.this::onFocusChange);}

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


            int count=table.getChildCount();

            Button btn_save = findViewById(R.id.btn_sale_save);

            Log.d("TAG","size: "+objArray.size());
            for(int a=0; a<objArray.size(); a++){
                Log.d("sex","elem: "+ objArray.get(a));
            }
            //////
            DatabaseReference newroot = FirebaseDatabase.getInstance().getReference().child("StockFull");
            newroot.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        for (DataSnapshot nodes : snapshot.getChildren()) {
                            Log.d("tag", "new node:" + nodes.getKey());
                            String n = nodes.getKey().replace("slash", "/");
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
                                            et_sale.setHint(col_0_obj1.getCol9());
                                            // et_sale.setText(col_0_obj1.getCol9());
                                            et_sale.setOnClickListener(Sale.this::onClick);


                                        } else if (j == 10) {
                                            column = 10;
                                            TextView et_sale = (TextView) nrow.getChildAt(j);
                                            menu_array.clear();
                                            et_sale.setTag("3 core:" + col_0_obj1.getCol10());
                                            et_sale.setHint(col_0_obj1.getCol10());
                                            et_sale.setOnClickListener(Sale.this::onClick);


                                        } else if (j == 11) {
                                            column = 11;
                                            TextView et_sale = (TextView) nrow.getChildAt(j);
                                            menu_array.clear();
                                            et_sale.setTag("4 core:" + col_0_obj1.getCol11());
                                            et_sale.setHint(col_0_obj1.getCol11());

                                            //et_sale.setOnFocusChangeListener(Sale.this::onFocusChange);
                                            et_sale.setOnClickListener(Sale.this::onClick);

                                        } else if (j == 12) {
                                            column = 12;
                                            TextView et_sale = (TextView) nrow.getChildAt(j);
                                            menu_array.clear();
                                            et_sale.setTag("5 core:" + col_0_obj1.getCol12());
                                            et_sale.setHint(col_0_obj1.getCol12());

                                            et_sale.setOnClickListener(Sale.this::onClick);


                                        } else if (j == 13) {
                                            column = 13;
                                            TextView et_sale = (TextView) nrow.getChildAt(j);
                                            menu_array.clear();
                                            et_sale.setTag("6 core:" + col_0_obj1.getCol13());
                                            et_sale.setHint(col_0_obj1.getCol13());


                                            et_sale.setOnClickListener(Sale.this::onClick);


                                        }

                                    }
                                }
                            }
                        }

                    }



                    progressDialog2.dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });









            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog = new ProgressDialog(Sale.this);
                    progressDialog.setMessage("Saling"); // Setting Message
                    progressDialog.setTitle("Sale Button"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    obj=null;
                    obj1=null;
                    Log.d("TAG","count: "+table.getChildCount());
                    if(selected_party!=null){

                        if (et_billno.getText().toString().equals("") != true) {
                            int z=0;
                            int y=0;

                            for (int i = 1; i < table.getChildCount(); i++) {
                                TableRow nrow = (TableRow) table.getChildAt(i);
                                TextView itemcode = (TextView) nrow.getChildAt(0);
                                if (itemcode.getText().toString().length()>0) {
                                    for (int x = 0; x < objArray.size(); x++) {

                                        if (objArray.get(x).getCol0().matches(itemcode.getText().toString())) {
                                            Log.d("hello","data key:  "+objArray.get(x).getCol0());
                                            obj = objArray.get(x);
                                            break;
                                        }

                                    }
                                }
                                if (itemcode.getText().toString().length()>0) {
                                    for (int x = 0; x < pc_array.size(); x++) {
                                        Log.d("nani","pc array: "+pc_array.get(x).getCol0());

                                        if (pc_array.get(x).getCol0().matches(itemcode.getText().toString())) {
                                            obj1 = pc_array.get(x);
                                            break;
                                        }

                                    }
                                }



                                for (int j = 1; j < 14; j++) {

                                    itemcode = (TextView) nrow.getChildAt(0);


                                    String item_code = itemcode.getText().toString();
                                    item_code=item_code.replace("/","slash");
                                    item_code=item_code.replace(".","dot");


                                    item_code=item_code.replace("#","hash");
                                    item_code=item_code.replace("$","dollar");
                                    item_code=item_code.replace("[","openbrace");
                                    item_code=item_code.replace("]","closebrace");
                                    item_code=item_code.replace("*","star");
                                    for(int o=0; o<item_codes_list.size(); o++){
                                        Log.d("TAG","list item: "+item_codes_list.get(0).toString());
                                        Log.d("TAG","item code"+item_code);
                                        if(item_code.matches(item_codes_list.get(o))){
                                            g=0;
                                        }
                                    }

                                    if(item_code.equals("")!=true & g==0){

                                        Log.d("TAG","i: "+i+" "+item_code);
                                        Log.d("nano","size of pc: "+pc_array.size());

                                        if(j<9){
                                            button = (EditText) nrow.getChildAt(j);
                                            data = button.getText().toString();
                                        }
                                        else if(j>=9){
                                            button2= (TextView) nrow.getChildAt(j);
                                            data=button2.getText().toString();
                                        }





                                        DatabaseReference root = FirebaseDatabase.getInstance().getReference().
                                                child("OutputSummary").
                                                child(tv_date.getText().toString());
                                        int col_data = 0;
                                        String added = null;
                                        String party_added=null;
                                        int object=0;
                                        if (obj != null) {

                                            object=0;

                                            if (j == 1) {


                                                if (obj != null && !obj.getCol1().equals("")) {


                                                    if (!data.equals("")) {
                                                        if(Integer.parseInt(obj.getCol1()) >= Integer.parseInt(data)){
                                                            col_data =  Integer.parseInt(obj.getCol1())- Integer.parseInt(data) ;
                                                            added = String.valueOf(col_data);
                                                            Log.d("hashh",""+ Integer.parseInt(obj.getCol1()));
                                                            object=1;
                                                        }
                                                        else{


                                                            added=obj.getCol1();
                                                            Log.d("TAG","JANHJEE: "+added);
                                                            Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                        }



                                                    } else {


                                                        added = obj.getCol1();

                                                    }

                                                } else {


                                                    added = obj.getCol1();

                                                }

                                            }


                                            if (j == 2) {


                                                if (obj != null && !obj.getCol2().equals("")) {

                                                    if (!data.equals("")) {
                                                        if(Integer.parseInt(obj.getCol2()) >= Integer.parseInt(data)){
                                                            col_data =  Integer.parseInt(obj.getCol2())- Integer.parseInt(data) ;
                                                            added = String.valueOf(col_data);
                                                            object=1;
                                                        }
                                                        else{
                                                            added=obj.getCol2();
                                                            Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                        }



                                                    } else {

                                                        added = obj.getCol2();

                                                    }

                                                } else {

                                                    added = obj.getCol2();

                                                }


                                            }



                                            if (j == 3) {


                                                if (obj != null && !obj.getCol3().equals("")) {


                                                    if (!data.equals("")) {
                                                        if(Integer.parseInt(obj.getCol3()) >= Integer.parseInt(data)){
                                                            col_data =  Integer.parseInt(obj.getCol1())- Integer.parseInt(data) ;
                                                            added = String.valueOf(col_data);
                                                            object=1;
                                                        }
                                                        else{
                                                            added=obj.getCol3();
                                                            Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                        }



                                                    } else {

                                                        added = obj.getCol3();

                                                    }

                                                } else {

                                                    added = obj.getCol3();

                                                }

                                            }


                                            if (j == 4) {


                                                if (obj != null && !obj.getCol4().equals("")) {


                                                    if (!data.equals("")) {
                                                        if(Integer.parseInt(obj.getCol4()) >= Integer.parseInt(data)){
                                                            col_data =  Integer.parseInt(obj.getCol4())- Integer.parseInt(data) ;
                                                            added = String.valueOf(col_data);
                                                            object=1;
                                                        }
                                                        else{
                                                            added=obj.getCol4();
                                                            Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                        }



                                                    } else {

                                                        added = obj.getCol4();

                                                    }

                                                } else {

                                                    added = obj.getCol4();

                                                }

                                            }


                                            if (j == 5) {


                                                if (obj != null && !obj.getCol5().equals("")) {


                                                    if (!data.equals("")) {
                                                        if(Integer.parseInt(obj.getCol5()) >= Integer.parseInt(data)){
                                                            col_data =  Integer.parseInt(obj.getCol5())- Integer.parseInt(data) ;
                                                            added = String.valueOf(col_data);
                                                            object=1;
                                                        }
                                                        else{
                                                            added=obj.getCol5();
                                                            Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                        }



                                                    } else {

                                                        added = obj.getCol5();

                                                    }

                                                } else {

                                                    added = obj.getCol5();

                                                }

                                            }


                                            if (j == 6) {


                                                if (obj != null && !obj.getCol6().equals("")) {


                                                    if (!data.equals("")) {
                                                        if(Integer.parseInt(obj.getCol6()) >= Integer.parseInt(data)){
                                                            col_data =  Integer.parseInt(obj.getCol6())- Integer.parseInt(data) ;
                                                            added = String.valueOf(col_data);
                                                            object=1;
                                                        }
                                                        else{
                                                            added=obj.getCol6();
                                                            Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                        }



                                                    } else {

                                                        added = obj.getCol6();

                                                    }

                                                } else {

                                                    added = obj.getCol6();

                                                }

                                            }


                                            if (j == 7) {


                                                if (obj != null && !obj.getCol7().equals("")) {


                                                    if (!data.equals("")) {
                                                        if(Integer.parseInt(obj.getCol7()) >= Integer.parseInt(data)){
                                                            col_data =  Integer.parseInt(obj.getCol7())- Integer.parseInt(data) ;
                                                            added = String.valueOf(col_data);
                                                            object=1;
                                                        }
                                                        else{
                                                            added=obj.getCol7();
                                                            Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                        }



                                                    } else {


                                                        added = obj.getCol7();

                                                    }

                                                } else {

                                                    added = obj.getCol7();

                                                }

                                            }


                                            if (j == 8) {


                                                if (obj != null && !obj.getCol8().equals("")) {


                                                    if (!data.equals("")) {
                                                        if(Integer.parseInt(obj.getCol8()) >= Integer.parseInt(data)){
                                                            col_data =  Integer.parseInt(obj.getCol8())- Integer.parseInt(data) ;
                                                            added = String.valueOf(col_data);
                                                            object=1;
                                                        }
                                                        else{
                                                            added=obj.getCol8();
                                                            Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                        }



                                                    } else {

                                                        added = obj.getCol8();

                                                    }

                                                } else {

                                                    added = obj.getCol8();

                                                }

                                            }



                                            if (j == 9) {


                                                if (obj != null && !obj.getCol9().equals("")) {

                                                }
                                                if (!data.equals("")) {
                                                    String aaa=obj.getCol9();
                                                    ArrayList<Integer> temp=new ArrayList<Integer>();

                                                    String number="";
                                                    if(aaa.contains("90")||aaa.contains("180")){


                                                        for (int a=0;a<aaa.length();a++){
                                                            Character value=aaa.charAt(a);

                                                            if("0123456789".indexOf(value) != -1){
                                                                number+=value;
                                                            }
                                                            else{

                                                                temp.add(Integer.valueOf(number));
                                                                number="";
                                                            }

                                                        }
                                                        temp.add(Integer.valueOf(number));
                                                        String min=null;
                                                        added="";
                                                        for(int b=0; b<temp.size();b++){
                                                            if(Integer.parseInt(data)<=temp.get(b)){
                                                                min="ok";
                                                                col_data =  temp.get(b)- Integer.parseInt(data) ;
                                                                temp.set(b, col_data);
                                                                for(int n=0; n<temp.size();n++){
                                                                    if(added.equals("")){
                                                                        added= added+temp.get(n);
                                                                    }
                                                                    else{
                                                                        added= added+"+"+temp.get(n);
                                                                    }

                                                                }
                                                                Log.d("goshi","added: "+added);
                                                                object=1;
                                                                break;
                                                            }
                                                        }




                                                        if(min==null){
                                                            added=obj.getCol9();
                                                            Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                        }



                                                    } else {

                                                        added = obj.getCol9();

                                                    }

                                                } else {

                                                    added = obj.getCol9();

                                                }

                                            }



                                            if (j == 10) {


                                                if (obj != null && !obj.getCol10().equals("")) {

                                                }
                                                if (!data.equals("")) {
                                                    String aaa=obj.getCol10();
                                                    ArrayList<Integer> temp=new ArrayList<Integer>();

                                                    String number="";
                                                    if(aaa.contains("90")||aaa.contains("180")){


                                                        for (int a=0;a<aaa.length();a++){
                                                            Character value=aaa.charAt(a);

                                                            if("0123456789".indexOf(value) != -1){
                                                                number+=value;
                                                            }
                                                            else{

                                                                temp.add(Integer.valueOf(number));
                                                                number="";
                                                            }

                                                        }
                                                        temp.add(Integer.valueOf(number));
                                                        String min=null;
                                                        added="";
                                                        for(int b=0; b<temp.size();b++){
                                                            if(Integer.parseInt(data)<=temp.get(b)){
                                                                min="ok";
                                                                col_data =  temp.get(b)- Integer.parseInt(data) ;
                                                                temp.set(b, col_data);
                                                                for(int n=0; n<temp.size();n++){
                                                                    if(added.equals("")){
                                                                        added= added+temp.get(n);
                                                                    }
                                                                    else{
                                                                        added= added+"+"+temp.get(n);
                                                                    }

                                                                }
                                                                Log.d("goshi","added: "+added);
                                                                object=1;
                                                                break;
                                                            }
                                                        }




                                                        if(min==null){
                                                            added=obj.getCol10();
                                                            Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                        }



                                                    } else {

                                                        added = obj.getCol10();

                                                    }

                                                } else {

                                                    added = obj.getCol10();

                                                }

                                            }

                                            if (j == 11) {


                                                if (obj != null && !obj.getCol11().equals("")) {

                                                }
                                                if (!data.equals("")) {
                                                    String aaa=obj.getCol11();
                                                    ArrayList<Integer> temp=new ArrayList<Integer>();

                                                    String number="";
                                                    if(aaa.contains("90")||aaa.contains("180")){


                                                        for (int a=0;a<aaa.length();a++){
                                                            Character value=aaa.charAt(a);

                                                            if("0123456789".indexOf(value) != -1){
                                                                number+=value;
                                                            }
                                                            else{

                                                                temp.add(Integer.valueOf(number));
                                                                number="";
                                                            }

                                                        }
                                                        temp.add(Integer.valueOf(number));
                                                        String min=null;
                                                        added="";
                                                        for(int b=0; b<temp.size();b++){
                                                            if(Integer.parseInt(data)<=temp.get(b)){
                                                                min="ok";
                                                                col_data =  temp.get(b)- Integer.parseInt(data) ;
                                                                temp.set(b, col_data);
                                                                for(int n=0; n<temp.size();n++){
                                                                    if(added.equals("")){
                                                                        added= added+temp.get(n);
                                                                    }
                                                                    else{
                                                                        added= added+"+"+temp.get(n);
                                                                    }

                                                                }
                                                                Log.d("goshi","added: "+added);
                                                                object=1;
                                                                break;
                                                            }
                                                        }




                                                        if(min==null){
                                                            added=obj.getCol11();
                                                            Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                        }



                                                    } else {

                                                        added = obj.getCol11();

                                                    }

                                                } else {

                                                    added = obj.getCol11();

                                                }

                                            }


                                            if (j == 12) {


                                                if (obj != null && !obj.getCol12().equals("")) {

                                                }
                                                if (!data.equals("")) {
                                                    String aaa=obj.getCol12();
                                                    ArrayList<Integer> temp=new ArrayList<Integer>();

                                                    String number="";
                                                    if(aaa.contains("90")||aaa.contains("180")){


                                                        for (int a=0;a<aaa.length();a++){
                                                            Character value=aaa.charAt(a);

                                                            if("0123456789".indexOf(value) != -1){
                                                                number+=value;
                                                            }
                                                            else{

                                                                temp.add(Integer.valueOf(number));
                                                                number="";
                                                            }

                                                        }
                                                        temp.add(Integer.valueOf(number));
                                                        String min=null;
                                                        added="";
                                                        for(int b=0; b<temp.size();b++){
                                                            if(Integer.parseInt(data)<=temp.get(b)){
                                                                min="ok";
                                                                col_data =  temp.get(b)- Integer.parseInt(data) ;
                                                                temp.set(b, col_data);
                                                                for(int n=0; n<temp.size();n++){
                                                                    if(added.equals("")){
                                                                        added= added+temp.get(n);
                                                                    }
                                                                    else{
                                                                        added= added+"+"+temp.get(n);
                                                                    }

                                                                }
                                                                Log.d("goshi","added: "+added);
                                                                object=1;
                                                                break;
                                                            }
                                                        }




                                                        if(min==null){
                                                            added=obj.getCol12();
                                                            Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                        }



                                                    } else {

                                                        added = obj.getCol12();

                                                    }

                                                } else {

                                                    added = obj.getCol12();

                                                }

                                            }


                                            if (j == 13) {


                                                if (obj != null && !obj.getCol13().equals("")) {

                                                }
                                                if (!data.equals("")) {
                                                    String aaa=obj.getCol13();
                                                    ArrayList<Integer> temp=new ArrayList<Integer>();

                                                    String number="";
                                                    if(aaa.contains("90")||aaa.contains("180")){


                                                        for (int a=0;a<aaa.length();a++){
                                                            Character value=aaa.charAt(a);

                                                            if("0123456789".indexOf(value) != -1){
                                                                number+=value;
                                                            }
                                                            else{

                                                                temp.add(Integer.valueOf(number));
                                                                number="";
                                                            }

                                                        }
                                                        temp.add(Integer.valueOf(number));
                                                        String min=null;
                                                        added="";
                                                        for(int b=0; b<temp.size();b++){
                                                            if(Integer.parseInt(data)<=temp.get(b)){
                                                                min="ok";
                                                                col_data =  temp.get(b)- Integer.parseInt(data) ;
                                                                temp.set(b, col_data);
                                                                for(int n=0; n<temp.size();n++){
                                                                    if(added.equals("")){
                                                                        added= added+temp.get(n);
                                                                    }
                                                                    else{
                                                                        added= added+"+"+temp.get(n);
                                                                    }

                                                                }
                                                                Log.d("goshi","added: "+added);
                                                                object=1;
                                                                break;
                                                            }
                                                        }




                                                        if(min==null){
                                                            added=obj.getCol13();
                                                            Toast.makeText(Sale.this, "Smaller value cannot minus larger", Toast.LENGTH_SHORT).show();
                                                        }



                                                    } else {

                                                        added = obj.getCol13();

                                                    }

                                                } else {

                                                    added = obj.getCol13();

                                                }

                                            }



                                        }
                                        if(obj1!=null){
                                            Log.d("nano","done");

                                            if (j == 1) {


                                                if (obj1 != null && !obj1.getCol1().equals("")) {


                                                    if (!data.equals("")) {



                                                        col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol1());
                                                        party_added = String.valueOf(col_data);


                                                    } else {

                                                        party_added= obj1.getCol1();

                                                    }

                                                } else {

                                                    party_added = data;

                                                }

                                            }


                                            if (j == 2) {

                                                if (obj1 != null && !obj1.getCol2().equals("")) {

                                                    if (!data.equals("")) {

                                                        col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol2());
                                                        party_added = String.valueOf(col_data);


                                                    } else {

                                                        party_added= obj1.getCol2();

                                                    }

                                                } else {

                                                    party_added = data;

                                                }

                                            }


                                            if (j == 3) {


                                                if (obj1 != null && !obj1.getCol3().equals("")) {


                                                    if (!data.equals("")) {

                                                        col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol3());
                                                        party_added= String.valueOf(col_data);


                                                    } else {

                                                        added = obj1.getCol3();

                                                    }

                                                } else {
                                                    party_added = data;
                                                }

                                            }


                                            if (j == 4) {

                                                if (obj1 != null && !obj1.getCol4().equals("")) {


                                                    if (!data.equals("")) {

                                                        col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol4());
                                                        party_added = String.valueOf(col_data);


                                                    } else {

                                                        party_added = obj1.getCol4();

                                                    }

                                                } else {

                                                    party_added = data;

                                                }

                                            }


                                            if (j == 5) {

                                                if (obj1 != null && !obj1.getCol5().equals("")) {


                                                    if (!data.equals("")) {

                                                        col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol5());
                                                        party_added = String.valueOf(col_data);


                                                    } else {

                                                        party_added= obj1.getCol5();

                                                    }

                                                } else {

                                                    party_added = data;

                                                }

                                            }


                                            if (j == 6) {

                                                if (obj1 != null && !obj1.getCol6().equals("")) {


                                                    if (!data.equals("")) {

                                                        col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol6());
                                                        party_added = String.valueOf(col_data);


                                                    } else {

                                                        party_added = obj1.getCol6();

                                                    }

                                                } else {

                                                    party_added = data;

                                                }

                                            }


                                            if (j == 7) {

                                                if (obj1 != null && !obj1.getCol7().equals("")) {


                                                    if (!data.equals("")) {

                                                        col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol7());
                                                        party_added = String.valueOf(col_data);


                                                    } else {

                                                        party_added = obj1.getCol7();

                                                    }

                                                } else {

                                                    party_added = data;

                                                }

                                            }


                                            if (j == 8) {

                                                if (obj1 != null && !obj1.getCol8().equals("")) {


                                                    if (!data.equals("")) {

                                                        col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol8());
                                                        added = String.valueOf(col_data);


                                                    } else {

                                                        party_added = obj1.getCol8();

                                                    }

                                                } else {

                                                    party_added = data;

                                                }

                                            }



                                            if (j == 9) {

                                                if (obj1 != null && !obj1.getCol9().equals("")) {


                                                    if (!data.equals("")) {

                                                        col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol9());
                                                        party_added = String.valueOf(col_data);


                                                    } else {

                                                        party_added = obj1.getCol9();

                                                    }

                                                } else {

                                                    party_added = data;

                                                }

                                            }


                                            if (j == 10) {

                                                if (obj1 != null && !obj1.getCol10().equals("")) {


                                                    if (!data.equals("")) {

                                                        col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol10());
                                                        party_added = String.valueOf(col_data);


                                                    } else {

                                                        party_added = obj1.getCol10();

                                                    }

                                                } else {

                                                    party_added = data;

                                                }

                                            }

                                            if (j == 11) {

                                                if (obj1 != null && !obj1.getCol11().equals("")) {


                                                    if (!data.equals("")) {

                                                        col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol11());
                                                        party_added = String.valueOf(col_data);


                                                    } else {

                                                        party_added= obj1.getCol11();

                                                    }

                                                } else {

                                                    party_added= data;

                                                }

                                            }


                                            if (j == 12) {

                                                if (obj1 != null && !obj1.getCol12().equals("")) {


                                                    if (!data.equals("")) {

                                                        col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol12());
                                                        party_added = String.valueOf(col_data);


                                                    } else {

                                                        party_added = obj1.getCol12();

                                                    }

                                                } else {

                                                    party_added = data;

                                                }

                                            }


                                            if (j == 13) {

                                                if (obj1 != null && !obj1.getCol13().equals("")) {


                                                    if (!data.equals("")) {

                                                        col_data = Integer.parseInt(data) + Integer.parseInt(obj1.getCol13());
                                                        party_added = String.valueOf(col_data);


                                                    } else {

                                                        party_added= obj1.getCol13();

                                                    }

                                                } else {

                                                    party_added = data;

                                                }

                                            }

                                        }

                                        if(obj==null){
                                            added ="";
                                        }
                                        if(obj1==null){
                                            Log.d("rana","null dikha arhaa hai");
                                            party_added=data;
                                        }
                                        //Log.d("hassan","DATA: "+data);
                                        if(j>=9){
                                            button2.setText("");
                                        }
                                        if(j<9){
                                            button.setText("");
                                        }



                                        root.child(item_code).child("Data").
                                                child("col" + j).setValue(party_added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                }
                                            }
                                        });
                                        Log.d("haha","party added: "+party_added +"j "+j);
                                        //   if(object==1){
                                        DatabaseReference party=FirebaseDatabase.getInstance().getReference().child("PartyCoding");
                                        party.child(selected_party).child(item_code).child("Data").child("col" + j).setValue(party_added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                }


                                            }
                                        });
                                        party.child(selected_party).child(item_code).child("Date").setValue(tv_date.getText().toString());


                                        // }







                                        if (billnum == null) {
                                            DatabaseReference billno = FirebaseDatabase.getInstance().getReference().
                                                    child("SaleBill");
                                            billno.child(et_billno.getText().toString()).child(item_code).child("Data").
                                                    child("col" + j).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        button.setText("");


                                                    }
                                                }
                                            });

                                            billno.child(et_billno.getText().toString()).child(item_code).child("Date").setValue(tv_date.getText().toString());
                                            billno.child(et_billno.getText().toString()).child(item_code).child("Party").setValue(selected_party);
                                            int increment = Integer.parseInt(String.valueOf(et_billno.getText())) + 1;
                                            stock_data obj_stock = new stock_data("", "", "", "", "", "", "", "", "", "", "", "", "", "");
                                            billno.child(String.valueOf(increment)).child(item_code).child("Data").setValue(obj_stock);


                                        } else if (billnum != null & et_billno.getText().toString() != "") {
                                            int increment = Integer.parseInt(String.valueOf(et_billno.getText())) + 1;


                                            DatabaseReference billno = FirebaseDatabase.getInstance().getReference().
                                                    child("SaleBill");
                                            billno.child(String.valueOf(increment-1)).child(item_code).child("Data").
                                                    child("col" + j).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        button.setText("");

                                                    }
                                                }
                                            });
                                            billno.child(String.valueOf(increment-1)).child(item_code).child("Date").setValue(tv_date.getText().toString());
                                            billno.child(et_billno.getText().toString()).child(item_code).child("Party").setValue(selected_party);
                                            stock_data obj_stock = new stock_data("", "", "", "", "", "", "", "", "", "", "", "", "", "");
                                            billno.child(String.valueOf(increment)).child(item_code).child("Data").setValue(obj_stock);


                                        } else {
                                            Toast.makeText(Sale.this, "Billno is empty", Toast.LENGTH_SHORT).show();
                                        }



                                        DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().
                                                child("Stock").child(tv_date.getText().toString());
                                        root1.child(item_code).child("Data").
                                                child("col" + j).setValue(added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                }
                                            }
                                        });
                                        root1.child(item_code).child("Seqenceno").setValue(String.valueOf(i));

                                        DatabaseReference root1_full = FirebaseDatabase.getInstance().getReference().
                                                child("StockFull");
                                        root1_full.child(item_code).child("Data").
                                                child("col" + j).setValue(added).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                }
                                            }
                                        });
                                        root1_full.child(item_code).child("Seqenceno").setValue(String.valueOf(i));



                                    }

                                }
                            }





                        }
                        else{ et_billno.setError("Bill no cannot be empty");


                        }
                    }
                    else{
                        Toast.makeText(Sale.this, "select party", Toast.LENGTH_SHORT).show();
                    }



                    progressDialog.dismiss();
                    Toast.makeText(Sale.this, "Saled", Toast.LENGTH_SHORT).show();
                }




            });
        }





    }

    public void onClick(View v) {

        menu_array.clear();
        PopupMenu menu = new PopupMenu(getApplicationContext(), v);
        menu.getMenu().clear();



       TextView vv=(TextView) v;

        String a= vv.getTag().toString();
        String [] split_string_list=a.split(":");
        String aaa=split_string_list[split_string_list.length-1];


        String number="";
       /* if(isNumeric(number)) {
            System.out.println("String is numeric!");
            // Do something
        } else {
            System.out.println("String is not numeric.");
        }*/
        Log.d("aaa","aaa: "+aaa);
       /* for(int i=0; i<aaa.length();i++){
            if(isNumeric(String.valueOf(aaa.charAt(i)))){
                Log.d("numeric","numeric: "+i);
            }
        }*/
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
        TextView gg=findViewById(R.id.sale_textView);
       gg.setText(vv.getTag().toString());

       menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               Log.d("tag", "item: "+item);



              AlertDialog.Builder builder = new AlertDialog.Builder(Sale.this);
               builder.setTitle("Title");

               final EditText input = new EditText(Sale.this);

               input.setInputType(InputType.TYPE_CLASS_NUMBER );
               builder.setView(input);
               builder.setTitle("Enter amount to sale");

               builder.setPositiveButton("Sale", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       m_Text = input.getText().toString();
                       Log.d("tag","dialog box: "+m_Text);
                       Log.d("tag","column: "+column);
                       vv.setText(m_Text);

                   }
               });
               builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                   }
               });

               builder.show();

               return true;
           }
       });

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        EditText vv=(EditText) v;
        TextView gg=findViewById(R.id.sale_textView);


        gg.setText(vv.getTag().toString());



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
    public static boolean isNumeric(String string) {
        int intValue;

        System.out.println(String.format("Parsing string: \"%s\"", string));

        if(string == null || string.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }
}

package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class check_sequence extends AppCompatActivity {
    ArrayList<Integer> seq_list = new ArrayList<Integer>();
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Integer> item_seq = new ArrayList<>();
    int maximum;

    public TableRow row;
    TableLayout table;
    public ArrayList<stock_data> objArray=new ArrayList<stock_data>();
    TextView ab;
    public TableRow row1;

    public stock_data obj;
    DatabaseReference root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sequence);
        obj=new stock_data();

        table = findViewById(R.id.seq_table);
        row = new TableRow(this);
        DatabaseReference dbs1 = FirebaseDatabase.getInstance().getReference().child("Stock");
        dbs1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot node: snapshot.getChildren()){

                   for(DataSnapshot node1: node.getChildren()){
                        if (node1.getKey().equals("Seqenceno")){
                            int num= Integer.parseInt(String.valueOf(node1.getValue()));
                            Log.d("gat","num: "+num);

                            seq_list.add(num);

                        }


                    }




                }
                Log.d("gat","size: "+seq_list.size());
                maximum=Collections.max(seq_list);
                Log.d("gat","max: "+maximum);
                if(seq_list.size()!=0){

                    TextView tv = new TextView(getApplicationContext());
                    tv.setText(" Item Code ");
                    tv.setWidth(400);
                    tv.setBackgroundResource(R.drawable.border_item);
                    tv.setTextColor(Color.BLACK);
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(24);

                    row.addView(tv);

                    TextView tv1 = new TextView(getApplicationContext());
                    tv1.setText(" Red ");
                    tv1.setWidth(300);
                    tv1.setBackgroundResource(R.drawable.border_red);
                    tv1.setTextColor(Color.parseColor("#D1C7C7"));
                    tv1.setGravity(Gravity.CENTER);
                    tv1.setTextSize(24);
                    //tv1.setBackgroundColor(Color.parseColor("#E67F7F"));
                    row.addView(tv1);

                    TextView tv2 = new TextView(getApplicationContext());
                    tv2.setText(" Black ");
                    tv2.setWidth(300);
                    tv2.setBackgroundResource(R.drawable.border_black);
                    tv2.setTextColor(Color.parseColor("#7CB342"));
                    tv2.setGravity(Gravity.CENTER);
                    tv2.setTextSize(24);
                    //tv2.setBackgroundColor(Color.parseColor("#050505"));
                    row.addView(tv2);

                    TextView tv3 = new TextView(getApplicationContext());
                    tv3.setText(" Yellow ");
                    tv3.setWidth(300);
                    tv3.setBackgroundResource(R.drawable.border_yellow);
                    tv3.setTextColor(Color.BLACK);
                    tv3.setGravity(Gravity.CENTER);
                    tv3.setTextSize(24);
                    //tv3.setBackgroundColor(Color.parseColor("#FDD835"));
                    row.addView(tv3);

                    TextView tv4 = new TextView(getApplicationContext());
                    tv4.setText(" Blue ");
                    tv4.setWidth(300);
                    tv4.setBackgroundResource(R.drawable.border_blue);
                    tv4.setTextColor(Color.BLACK);
                    tv4.setGravity(Gravity.CENTER);
                    tv4.setTextSize(24);
                    //tv4.setBackgroundColor(Color.parseColor("#1E88E5"));
                    row.addView(tv4);

                    TextView tv5 = new TextView(getApplicationContext());
                    tv5.setText(" Green ");
                    tv5.setWidth(300);
                    tv5.setBackgroundResource(R.drawable.border_green);
                    tv5.setTextColor(Color.BLACK);
                    tv5.setGravity(Gravity.CENTER);
                    tv5.setTextSize(24);
                    //tv5.setBackgroundColor(Color.parseColor("#43A047"));
                    row.addView(tv5);

                    TextView tv6 = new TextView(getApplicationContext());
                    tv6.setText(" White ");
                    tv6.setWidth(300);
                    tv6.setBackgroundResource(R.drawable.border_white);
                    tv6.setTextColor(Color.BLACK);
                    tv6.setGravity(Gravity.CENTER);
                    tv6.setTextSize(24);
                    //tv6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    row.addView(tv6);

                    TextView tv7 = new TextView(getApplicationContext());
                    tv7.setText(" Other ");
                    tv7.setWidth(300);
                    tv7.setBackgroundResource(R.drawable.border_other);
                    tv7.setTextColor(Color.BLACK);
                    tv7.setGravity(Gravity.CENTER);
                    tv7.setTextSize(24);
                    //tv7.setBackgroundColor(Color.parseColor("#DBDF9C"));
                    row.addView(tv7);

                    TextView tv8 = new TextView(getApplicationContext());
                    tv8.setText(" Total ");
                    tv8.setWidth(300);
                    tv8.setBackgroundResource(R.drawable.border_white);
                    tv8.setTextColor(Color.BLACK);
                    tv8.setGravity(Gravity.CENTER);
                    tv8.setTextSize(24);
                    //tv8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    row.addView(tv8);

                    TextView tv9 = new TextView(getApplicationContext());
                    tv9.setText(" 2 Core ");
                    tv9.setWidth(300);
                    tv9.setBackgroundResource(R.drawable.border_white);
                    tv9.setTextColor(Color.BLACK);
                    tv9.setGravity(Gravity.CENTER);
                    tv9.setTextSize(24);
                    //tv9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    row.addView(tv9);

                    TextView tv10 = new TextView(getApplicationContext());
                    tv10.setText(" 3 Core ");
                    tv10.setWidth(300);
                    tv10.setBackgroundResource(R.drawable.border_white);
                    tv10.setTextColor(Color.BLACK);
                    tv10.setGravity(Gravity.CENTER);
                    tv10.setTextSize(24);
                    //tv10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    row.addView(tv10);

                    TextView tv11 = new TextView(getApplicationContext());
                    tv11.setText(" 4 Core ");
                    tv11.setWidth(300);
                    tv11.setBackgroundResource(R.drawable.border_white);
                    tv11.setTextColor(Color.BLACK);
                    tv11.setGravity(Gravity.CENTER);
                    tv11.setTextSize(24);
                    //tv11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    row.addView(tv11);

                    TextView tv12 = new TextView(getApplicationContext());
                    tv12.setText(" 5 Core ");
                    tv12.setWidth(300);
                    tv12.setBackgroundResource(R.drawable.border_white);
                    tv12.setTextColor(Color.BLACK);
                    tv12.setGravity(Gravity.CENTER);
                    tv12.setTextSize(24);
                    //tv12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    row.addView(tv12);

                    TextView tv13 = new TextView(getApplicationContext());
                    tv13.setText(" 6 Core ");
                    tv13.setWidth(300);
                    tv13.setBackgroundResource(R.drawable.border_white);
                    tv13.setTextColor(Color.BLACK);
                    tv13.setGravity(Gravity.CENTER);
                    tv13.setTextSize(24);
                    //tv13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    row.addView(tv13);


                    table.addView(row);

                    root = FirebaseDatabase.getInstance().getReference().child("Stock");
                    root.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            objArray.clear();
                            if (snapshot.exists()) {
                                for (DataSnapshot nodes : snapshot.getChildren()) {
                                    String itemcodeees = String.valueOf(nodes.getKey());
                                    arrayList.add(itemcodeees);
                                    for (DataSnapshot nodes1 : nodes.getChildren()) {
                                        if(nodes1.getKey().equals("Data")){
                                            objArray.add(nodes1.getValue(stock_data.class));
                                        }
                                        else if(nodes1.getKey().equals("Seqenceno")){
                                            Log.d("gat","ITe seq: "+ nodes1.getValue());
                                            item_seq.add(Integer.parseInt(String.valueOf(nodes1.getValue())));
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                    DatabaseReference dbs = FirebaseDatabase.getInstance().getReference().child("Stock");
                    dbs.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int z = 0;
                            int a=0;
                            Log.d("TAG", "Size of array: " + arrayList.size());
                            for (int i = 1; i < maximum+1; i++) {
                                row1 = new TableRow(getApplicationContext());
                                for (int j = 0; j < 14; j++) {
                                    if (j == 0) {
                                        ab = new TextView(getApplicationContext());
                                        Log.d("GAT","itemeq: "+item_seq.get(a));
                                        for(int x=0; x<item_seq.size();x++){
                                            if(item_seq.get(x)==i){
                                                if(ab.getText().toString()!=null){
                                                    ab.setText(arrayList.get(a));
                                                    a++;
                                                }

                                            }
                                        }



                                        //Log.d("TAG", "ARRAY: " + arrayList.get(z));
                                        ab.setWidth(400);
                                        ab.setBackgroundResource(R.drawable.border_white);
                                        ab.setTextColor(Color.BLACK);
                                        ab.setGravity(Gravity.CENTER);
                                        ab.setTextSize(16);
                                        ab.setHeight(115);
                                        ab.setTypeface(Typeface.DEFAULT_BOLD);
                                        row1.addView(ab);
                                    }
                                    EditText et = new EditText(getApplicationContext());
                                    et.setText("");
                                    et.setWidth(100);
                                    et.setBackgroundResource(R.drawable.border_white);
                                    et.setTextColor(Color.BLACK);
                                    et.setGravity(Gravity.CENTER);
                                    et.setTextSize(16);
                                    et.setHeight(115);
                                    row1.addView(et);

                                }


                                table.addView(row1);

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
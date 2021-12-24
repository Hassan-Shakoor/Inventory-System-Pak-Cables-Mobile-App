package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.ArrayList;

public class latest_dealer_ledger extends AppCompatActivity {
    TableLayout table;
    ArrayList<Integer> menu_array=new ArrayList<Integer>();
    TableRow row1;
    String party="";
    String core="";
    TextView ab;
    ArrayList<ArrayList<String>> rows_list = new ArrayList<ArrayList<String>>();
    public int height=90;
    public int font=11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_dealer_ledger);
        table = findViewById(R.id.latest_dealer_ledger_table);
        Intent item=getIntent();
        party=item.getStringExtra("party");
        Log.d("log","party: "+party);

        TableRow row = new TableRow(latest_dealer_ledger.this);
        row.setBaselineAligned(false);



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

        row.addView(tv2);

        TextView tv3 = new TextView(getApplicationContext());
        tv3.setText(" Yellow ");
        tv3.setWidth(120);
        tv3.setBackgroundResource(R.drawable.border_yellow);
        tv3.setTextColor(Color.BLACK);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextSize(font);

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
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("PartyCoding").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot party_node: snapshot.getChildren()){

                    if(party_node.getKey().matches(party)){

                        for(DataSnapshot item_node: party_node.getChildren()){
                            if(!item_node.getKey().matches("Id") &&
                                    !item_node.getKey().matches("company")
                            && !item_node.getKey().matches("phone")){
                                ArrayList<String> arrayList = new ArrayList<>();
                                arrayList.add(item_node.getKey());
                                for(DataSnapshot node: item_node.getChildren()){
                                    if(node.getKey().matches("Data")){
                                        arrayList.add(node.getValue(Tablerow.class).getCol1());


                                        arrayList.add(node.getValue(Tablerow.class).getCol2());


                                        arrayList.add(node.getValue(Tablerow.class).getCol3());


                                        arrayList.add(node.getValue(Tablerow.class).getCol4());


                                        arrayList.add(node.getValue(Tablerow.class).getCol5());


                                        arrayList.add(node.getValue(Tablerow.class).getCol6());


                                        arrayList.add(node.getValue(Tablerow.class).getCol7());


                                        arrayList.add(node.getValue(Tablerow.class).getCol8());


                                        arrayList.add(node.getValue(Tablerow.class).getCol9());


                                        arrayList.add(node.getValue(Tablerow.class).getCol10());


                                        arrayList.add(node.getValue(Tablerow.class).getCol11());


                                        arrayList.add(node.getValue(Tablerow.class).getCol12());


                                        arrayList.add(node.getValue(Tablerow.class).getCol13());
                                        rows_list.add(arrayList);
                                    }
                                }

                            }
                        }
                    }

                }
                int c=0;
                for(int a=0; a<rows_list.size(); a++){
                    // for(int x=0; x<a; x++) {


                    c++;
                    Log.d("raat", "c: " + c);
                    Log.d("raat", "list: " + rows_list.get(a));
                    // }
                }
                for (int i = 0; i <rows_list.size(); i++) {
                    row1 = new TableRow(getApplicationContext());
                    for (int j = 0; j < 14; j++) {

                        ab = new TextView(getApplicationContext());

                        if(j==0){

                            String n =  rows_list.get(i).get(j);
                            n=n.replace("slash", "/");
                            n = n.replace("dot", ".");

                            n=n.replace("hash","#");
                            n=n.replace("dollar","$");
                            n=n.replace("openbrace","[");

                            n=n.replace("closebrace","]");
                            n=n.replace("star","*");
                            ab.setText(n);

                            ab.setWidth(200);
                            ab.setBackgroundResource(R.drawable.border_white);
                            ab.setTextColor(Color.BLACK);
                            ab.setGravity(Gravity.CENTER);
                            ab.setTextSize(font);
                            ab.setHeight(height);

                        }
                        if (j == 8) {
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
                        }
                        if(j==9){
                            menu_array.clear();

                            String number="";
                            int output=0;
                            String num=rows_list.get(i).get(j);
                            //split(rows_list.get(i).get(j));

                            ab.setWidth(120);
                            ab.setBackgroundResource(R.drawable.border_white);
                            ab.setTextColor(Color.BLACK);
                            ab.setGravity(Gravity.CENTER);
                            ab.setOnClickListener(latest_dealer_ledger.this::onClick);
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
                        if(j==10){
                            menu_array.clear();

                            String number="";
                            int output=0;
                            String num=rows_list.get(i).get(j);
                            //split(rows_list.get(i).get(j));

                            ab.setWidth(120);
                            ab.setBackgroundResource(R.drawable.border_white);
                            ab.setTextColor(Color.BLACK);
                            ab.setGravity(Gravity.CENTER);
                            ab.setOnClickListener(latest_dealer_ledger.this::onClick);
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
                        if(j==11){
                            menu_array.clear();

                            String number="";
                            int output=0;
                            String num=rows_list.get(i).get(j);
                            //split(rows_list.get(i).get(j));

                            ab.setWidth(120);
                            ab.setBackgroundResource(R.drawable.border_white);
                            ab.setTextColor(Color.BLACK);
                            ab.setGravity(Gravity.CENTER);
                            ab.setOnClickListener(latest_dealer_ledger.this::onClick);
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
                        if(j==12){
                            menu_array.clear();

                            String number="";
                            int output=0;
                            String num=rows_list.get(i).get(j);
                            //split(rows_list.get(i).get(j));

                            ab.setWidth(120);
                            ab.setBackgroundResource(R.drawable.border_white);
                            ab.setTextColor(Color.BLACK);
                            ab.setGravity(Gravity.CENTER);
                            ab.setOnClickListener(latest_dealer_ledger.this::onClick);
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
                        if(j==13){
                            menu_array.clear();

                            String number="";
                            int output=0;
                            String num=rows_list.get(i).get(j);
                            //split(rows_list.get(i).get(j));

                            ab.setWidth(160);
                            ab.setBackgroundResource(R.drawable.border_white);
                            ab.setTextColor(Color.BLACK);
                            ab.setGravity(Gravity.CENTER);
                            ab.setOnClickListener(latest_dealer_ledger.this::onClick);
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
                            String n =  rows_list.get(i).get(j);
                            n=n.replace("slash", "/");
                            n = n.replace("dot", ".");

                            n=n.replace("hash","#");
                            n=n.replace("dollar","$");
                            n=n.replace("openbrace","[");

                            n=n.replace("closebrace","]");
                            n=n.replace("star","*");
                            ab.setText(n);
                            ab.setWidth(120);
                            ab.setBackgroundResource(R.drawable.border_white);
                            ab.setTextColor(Color.BLACK);
                            ab.setGravity(Gravity.CENTER);
                            ab.setTextSize(font);
                            ab.setHeight(height);
                        }


                        View vi=table.getChildAt(0);
                        TableRow ro = (TableRow) vi;
                        TextView item_code_boxx = (TextView) ro.getChildAt(j);
                        ab.setTag(rows_list.get(i).get(0)+":"+item_code_boxx.getText().toString());

                        ab.setOnClickListener(latest_dealer_ledger.this::onClick);




                        row1.addView(ab);

                    }




                    table.addView(row1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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
        TextView gg=findViewById(R.id.latest_dealer_ledger_textview);
        gg.setText(vv.getTag().toString());

    }
}
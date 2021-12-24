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

public class dealer_ledger extends AppCompatActivity {
    TableLayout table;
    ArrayList<Integer> menu_array=new ArrayList<Integer>();
    TableRow row1;
    String party="";
    TextView ab;
    ArrayList<ArrayList<String>> rows_list = new ArrayList<ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_ledger);
        table = findViewById(R.id.dealer_ledger_table);
        Intent item=getIntent();
        party=item.getStringExtra("party");
        Log.d("log","party: "+party);

        TableRow row = new TableRow(dealer_ledger.this);

        TextView tv0 = new TextView(getApplicationContext());
        tv0.setText(" Billno ");
        tv0.setWidth(200);
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

        row.addView(tv1);

        TextView tv2 = new TextView(getApplicationContext());
        tv2.setText(" Black ");
        tv2.setWidth(200);
        tv2.setBackgroundResource(R.drawable.border_black);
        tv2.setTextColor(Color.parseColor("#7CB342"));
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextSize(20);

        row.addView(tv2);

        TextView tv3 = new TextView(getApplicationContext());
        tv3.setText(" Yellow ");
        tv3.setWidth(200);
        tv3.setBackgroundResource(R.drawable.border_yellow);
        tv3.setTextColor(Color.BLACK);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextSize(20);

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
        row.addView(tv13);

        TextView tv14 = new TextView(getApplicationContext());
        tv14.setText(" Date");
        tv14.setWidth(400);
        tv14.setBackgroundResource(R.drawable.border_white);
        tv14.setTextColor(Color.BLACK);
        tv14.setGravity(Gravity.CENTER);
        tv14.setTextSize(20);
        row.addView(tv14);
        table.addView(row);


       DatabaseReference database = FirebaseDatabase.getInstance().getReference();
       database.child("SaleBill").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot bill: snapshot.getChildren()){

                   String billno= bill.getKey();

                   for(DataSnapshot item_code: bill.getChildren()){
                       ArrayList<String> arrayList = new ArrayList<>();
                       String itemcode=item_code.getKey();
                       arrayList.add(billno);
                       arrayList.add(itemcode);
                       for(DataSnapshot node: item_code.getChildren()){
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


                           }
                           if(node.getKey().matches("Date")){
                               String date = String.valueOf(node.getValue());
                               arrayList.add(date);
                           }
                           if(node.getKey().matches("Party")){
                               if(party.matches(String.valueOf(node.getValue()))){
                                   rows_list.add(arrayList);
                               }
                               else{
                                   arrayList.clear();
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

               int z = 0;
               for (int i = 0; i <rows_list.size(); i++) {
                   row1 = new TableRow(getApplicationContext());
                   for (int j = 0; j < 16; j++) {

                       ab = new TextView(getApplicationContext());

                       if(j==0){
                           ab.setText(rows_list.get(i).get(j));

                           ab.setWidth(300);
                           ab.setBackgroundResource(R.drawable.border_white);
                           ab.setTextColor(Color.BLACK);
                           ab.setGravity(Gravity.CENTER);
                           ab.setTextSize(16);
                           ab.setHeight(115);
                           View vi=table.getChildAt(0);
                           TableRow ro = (TableRow) vi;
                           TextView item_code_boxx = (TextView) ro.getChildAt(j);
                           ab.setTag(rows_list.get(i).get(0)+":"+rows_list.get(i).get(1)+":"+item_code_boxx.getText().toString());

                           ab.setOnClickListener(dealer_ledger.this::onClick);

                       }
                       if(j==15){
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

                           ab.setOnClickListener(dealer_ledger.this::onClick);
                       }
                       if(j==2){
                           menu_array.clear();
                           String num=rows_list.get(i).get(j);
                           Log.d("chung","num: "+rows_list.get(i).get(j));
                           String number="";
                           int output=0;
                           //split(rows_list.get(i).get(j));

                           ab.setWidth(200);
                           ab.setBackgroundResource(R.drawable.border_white);
                           ab.setTextColor(Color.BLACK);
                           ab.setGravity(Gravity.CENTER);
                           ab.setOnClickListener(dealer_ledger.this::onClick);
                           ab.setTextSize(16);
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

                           ab.setText(String.valueOf(output));
                           TextView item_code_boxx = (TextView) ro.getChildAt(j);

                           ab.setTag(rows_list.get(i).get(0)+":"+rows_list.get(i).get(1)+":"+item_code_boxx.getText().toString()+":"+num);

                           // textView.setTag(rows_list.get(0).get(0)+": "+item_code_boxx.getText().toString()+"\n: "+rows_list.get(i).get(j));
                           ab.setHeight(115);
                           ab.setOnClickListener(dealer_ledger.this::onClick);
                       }

                       else{
                           ab.setText(rows_list.get(i).get(j));
                           ab.setWidth(200);
                           ab.setBackgroundResource(R.drawable.border_white);
                           ab.setTextColor(Color.BLACK);
                           ab.setGravity(Gravity.CENTER);
                           ab.setTextSize(16);
                           ab.setHeight(115);
                           View vi=table.getChildAt(0);
                           TableRow ro = (TableRow) vi;
                           TextView item_code_boxx = (TextView) ro.getChildAt(j);
                           ab.setTag(rows_list.get(i).get(0)+":"+rows_list.get(i).get(1)+":"+item_code_boxx.getText().toString());

                           ab.setOnClickListener(dealer_ledger.this::onClick);
                       }













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
    }
    public void onClick(View v) {
        menu_array.clear();

        TextView vv=(TextView)v;
        TextView gg= findViewById(R.id.dealer_ledger_textview);

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
}
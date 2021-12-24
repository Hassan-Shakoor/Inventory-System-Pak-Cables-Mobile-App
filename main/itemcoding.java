package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Date;
import java.util.HashMap;

public class itemcoding extends AppCompatActivity {
    DatabaseReference root;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Integer>seq_list = new ArrayList<Integer>();
    ArrayList<String> itemcode_list = new ArrayList<>();
    ArrayList<String> date_list=new ArrayList<String>();

    String s;

    ProgressDialog progressDialog;
    ProgressDialog progressDialog2;

    public String code;
    public String nn;
    public String n;
    public String right;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemcoding);
        getSupportActionBar().setTitle("Item Name");
        itemcode_list.add("Select Item code down");
        Button save=findViewById(R.id.btn_save_itemcoding);
        EditText item_code=findViewById(R.id.tv_itemcode);
        EditText seq_no=findViewById(R.id.tv_sequenceno);
        Spinner sp=findViewById(R.id.sp_itemcoding);
        progressDialog2 = new ProgressDialog(itemcoding.this);
        progressDialog2.setMessage("Saving Details..."); // Setting Message
        progressDialog2.setTitle("Item Coding"); // Setting Title
        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog2.setCanceledOnTouchOutside(false);
        progressDialog2.show();


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String millisInString  = dateFormat.format(new Date());


        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);


            DatabaseReference dbs = FirebaseDatabase.getInstance().getReference().child("StockFull");
            dbs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    itemcode_list.clear();
                    itemcode_list.add("Select Item code down");
                    sp.setSelection(0);


                    for (DataSnapshot node : snapshot.getChildren()) {
                        Log.d("snap", "key: " + node.getKey());
                        String n = node.getKey().replace("slash", "/");
                        n = n.replace("dot", ".");

                        n=n.replace("hash","#");
                        n=n.replace("dollar","$");
                        n=n.replace("openbrace","[");

                        n=n.replace("closebrace","]");
                        n=n.replace("star","*");


                        itemcode_list.add(n);
                        for (DataSnapshot node1 : node.getChildren()) {
                            if (node1.getKey().equals("Seqenceno")) {
                                seq_list.add(Integer.parseInt(String.valueOf(node1.getValue())));
                                Log.d("snap","seq value: "+node1.getValue());

                            }


                        }


                    }





                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, itemcode_list);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp.setAdapter(arrayAdapter);

                    Log.d("zeesh", "size: " + seq_list.size());
                    if (seq_list.isEmpty() != true) {

                        int newseq = Collections.max(seq_list);
                        seq_no.setText(String.valueOf(newseq + 1));
                    } else {
                        seq_no.setText("1");
                    }
                    progressDialog2.dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Collections.sort(seq_list);
            Log.d("zeesh", "size: " + seq_list.size());
            Button exit = findViewById(R.id.btn_exit_itemcoding);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            Button delete = findViewById(R.id.btn_delete_itemcoding);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    s = item_code.getText().toString();
                    String seq = seq_no.getText().toString();
                    if (s.equals("") != true & seq.equals("") != true) {


                        s = s.replace("/", "slash");
                        s = s.replace(".", "dot");
                        s=s.replace("#","hash");
                        s=s.replace("$","dollar");
                        s=s.replace("[","openbrace");
                        s=s.replace("]","closebrace");
                        s=s.replace("*","star");





                        DatabaseReference dbs_stock = FirebaseDatabase.getInstance().getReference().child("Stock");
                        dbs_stock.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {
                                    for(DataSnapshot node1: node.getChildren()){
                                        Log.d("sadia", "node: " + node.getKey());

                                        if(node1.getKey().equals(s)){
                                            node1.getRef().removeValue();
                                        }

                                        item_code.setText("");
                                        //seq_no.setText("");
                                        Toast.makeText(itemcoding.this, "Deleted", Toast.LENGTH_SHORT).show();
                                        //seq_no.setText("");
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        DatabaseReference dbs_stock_full = FirebaseDatabase.getInstance().getReference().child("StockFull").
                                child(s);
                        dbs_stock_full.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {

                                    node.getRef().removeValue();
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    Toast.makeText(itemcoding.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    //seq_no.setText("");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        DatabaseReference dbs_input = FirebaseDatabase.getInstance().getReference().child("InputSummary");

                        dbs_input.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {
                                    for(DataSnapshot node1: node.getChildren()){
                                        if(node1.getKey().matches(s)){
                                            node1.getRef().removeValue();
                                            item_code.setText("");
                                            //seq_no.setText("");
                                            Toast.makeText(itemcoding.this, "Deleted", Toast.LENGTH_SHORT).show();
                                            //seq_no.setText("");
                                        }

                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        DatabaseReference dbs_output = FirebaseDatabase.getInstance().getReference().child("OutputSummary");

                        dbs_output.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {
                                    for(DataSnapshot node1: node.getChildren()){
                                        if(node1.getKey().matches(s)){
                                            node1.getRef().removeValue();
                                            item_code.setText("");
                                            //seq_no.setText("");
                                            Toast.makeText(itemcoding.this, "Deleted", Toast.LENGTH_SHORT).show();
                                            //seq_no.setText("");
                                        }

                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        DatabaseReference dbs_itemrate = FirebaseDatabase.getInstance().getReference().child("ItemRate").
                                child(s);
                        dbs_itemrate.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {

                                    node.getRef().removeValue();
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    Toast.makeText(itemcoding.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    //seq_no.setText("");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                        ///
                        finish();


                    }
                }
            });


            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    code = sp.getSelectedItem().toString();
                    EditText seqno = findViewById(R.id.tv_sequenceno);
                    EditText tv_itemcode = findViewById(R.id.tv_itemcode);

                    right = code.replace("/", "slash");
                    right = right.replace(".", "dot");

                    right=right.replace("#","hash");
                    right=right.replace("$","dollar");
                    right=right.replace("[","openbrace");
                    right=right.replace("]","closebrace");
                    right=right.replace("*","star");


                    Log.d("Mam", right);

                    if (code.equals("Select Item code down") != true) {


                        ////
                        n = code.replace("slash", "/");
                        n = n.replace("dot", ".");

                        n=n.replace("hash","#");
                        n=n.replace("dollar","$");
                        n=n.replace("openbrace","[");

                        n=n.replace("closebrace","]");
                        n=n.replace("star","*");




                    }
                    if (n != null) {


                        DatabaseReference dbs1 = FirebaseDatabase.getInstance().getReference().child("StockFull")
                                .child(right);//.child("Data"); //item_code
                        dbs1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {
                                    Log.d("tag", "node= " + node);
                                    if (node.getKey().equals("Seqenceno")) {
                                        Log.d("tag", "node1: " + node.getValue());
                                        seq_no.setText(node.getValue().toString());
                                        tv_itemcode.setText(n);

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else if (n == null) {


                        DatabaseReference dbs1 = FirebaseDatabase.getInstance().getReference().child("StockFull")
                                .child(right);//.child("Data"); //item_code
                        dbs1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {
                                    Log.d("tag", "node= " + node);
                                    if (node.getKey().equals("Seqenceno")) {
                                        Log.d("tag", "node1: " + node.getValue());
                                        seq_no.setText(node.getValue().toString());
                                        tv_itemcode.setText(code);

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /*    progressDialog = new ProgressDialog(itemcoding.this);
                    progressDialog.setMessage("Saving"); // Setting Message
                    progressDialog.setTitle("Item Coding"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();*/



                    String itemcode = item_code.getText().toString();
                    nn = itemcode;
                    Log.d("TAG", "ITM CODE: " + nn);
                    String sequenceno = seq_no.getText().toString();
                    HashMap<String, Object> itemcoding = new HashMap<>();
                    itemcoding.put("Seqenceno", sequenceno);
                    String n = itemcode;


                    int num = 0;
                    for (int x = 0; x < itemcode_list.size(); x++) {
                        Log.d("testing","itemcodes matching: "+itemcode_list.get(x));
                        if (n.equals(itemcode_list.get(x))) {
                            num = 1;
                            break;
                        }
                    }
                    Log.d("testing","num: "+num);

                    n = n.replace("/", "slash");
                    n = n.replace(".", "dot");
                    n=n.replace("#","hash");
                    n=n.replace("$","dollar");
                    n=n.replace("[","openbrace");
                    n=n.replace("]","closebrace");
                    n=n.replace("*","star");

                    ///

                    if (num == 0) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String millisInString = dateFormat.format(new Date());

                        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("InputSummary").
                                child(millisInString);
                        root.child(n).setValue(itemcoding).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");


                                }
                            }
                        });

                        DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().child("Stock").child(millisInString);

                        root1.child(n).setValue(itemcoding).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");


                                }
                            }
                        });
                        DatabaseReference root1_full = FirebaseDatabase.getInstance().getReference().child("StockFull");

                        root1_full.child(n).setValue(itemcoding).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");



                                }
                            }
                        });

                        DatabaseReference itemrate = FirebaseDatabase.getInstance().getReference().child("ItemRate");

                        itemrate.child(n).setValue(itemcoding).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");



                                }
                            }
                        });

                        DatabaseReference outputsum = FirebaseDatabase.getInstance().getReference().child("OutputSummary").
                                child(millisInString);
                        outputsum.child(n).setValue(itemcoding).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    //  progressDialog.dismiss();

                                }

                            }
                        });


                        Log.d("goshi", "num: " + num);
                        //  if(num==0){
                        stock_data obj_stock = new stock_data("", "", "", "", "", "", "", "", "", "", "", "", "", "");
                        rates obj_rates = new rates("", "", "", "", "", "", "", "", "", "", "", "");
                        DatabaseReference dbs2 = FirebaseDatabase.getInstance().getReference().child("Stock").child(millisInString);

                        dbs2.child(n).child("Data").setValue(obj_stock);
                        DatabaseReference dbs2_full = FirebaseDatabase.getInstance().getReference().child("StockFull");

                        dbs2_full.child(n).child("Data").setValue(obj_stock);


                        DatabaseReference dbs3 = FirebaseDatabase.getInstance().getReference().child("InputSummary").
                                child(millisInString);
                        dbs3.child(n).child("Data").setValue(obj_stock);

                        DatabaseReference dbs4 = FirebaseDatabase.getInstance().getReference().child("ItemRate");
                        dbs4.child(n).child("Rate").setValue(obj_rates);

                        DatabaseReference dbs5 = FirebaseDatabase.getInstance().getReference().child("OutputSummary").
                                child(millisInString);
                        dbs5.child(n).child("Data").setValue(obj_stock);



                        // }


                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String millisInString = dateFormat.format(new Date());


                        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("InputSummary").
                                child(millisInString);
                        root.child(n).child("Seqenceno").setValue(sequenceno).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    //  progressDialog.dismiss();


                                }
                            }
                        });
                        DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().child("Stock").child(millisInString);

                        root1.child(n).child("Seqenceno").setValue(sequenceno).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    //   progressDialog.dismiss();


                                }
                            }
                        });
                        DatabaseReference root1_full = FirebaseDatabase.getInstance().getReference().child("StockFull");

                        root1_full.child(n).child("Seqenceno").setValue(sequenceno).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    //  progressDialog.dismiss();


                                }
                            }
                        });

                        DatabaseReference itemrate = FirebaseDatabase.getInstance().getReference().child("ItemRate");

                        itemrate.child(n).child("Seqenceno").setValue(sequenceno).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    //   progressDialog.dismiss();


                                }
                            }
                        });
                        DatabaseReference outputsum = FirebaseDatabase.getInstance().getReference().child("OutputSummary").
                                child(millisInString);
                        outputsum.child(n).child("Seqenceno").setValue(sequenceno).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");


                                    // progressDialog.dismiss();

                                }

                            }
                        });



                    }
                    //finish();


                }
            });
        }

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);


            DatabaseReference dbs = FirebaseDatabase.getInstance().getReference().child("StockFull");
            dbs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    itemcode_list.clear();
                    itemcode_list.add("Select Item code down");
                    sp.setSelection(0);


                    for (DataSnapshot node : snapshot.getChildren()) {
                        Log.d("snap", "key: " + node.getKey());



                        ////
                        String n = node.getKey().replace("slash", "/");
                        n = n.replace("dot", ".");

                        n=n.replace("hash","#");
                        n=n.replace("dollar","$");
                        n=n.replace("openbrace","[");

                        n=n.replace("closebrace","]");
                        n=n.replace("star","*");


                        itemcode_list.add(n);
                        for (DataSnapshot node1 : node.getChildren()) {
                            if (node1.getKey().equals("Seqenceno")) {
                                seq_list.add(Integer.parseInt(String.valueOf(node1.getValue())));
                                Log.d("snap","seq value: "+node1.getValue());

                            }


                        }


                    }





                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, itemcode_list);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp.setAdapter(arrayAdapter);

                    Log.d("zeesh", "size: " + seq_list.size());
                    if (seq_list.isEmpty() != true) {

                        int newseq = Collections.max(seq_list);
                        seq_no.setText(String.valueOf(newseq + 1));
                    } else {
                        seq_no.setText("1");
                    }
                    progressDialog2.dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Collections.sort(seq_list);
            Log.d("zeesh", "size: " + seq_list.size());
            Button exit = findViewById(R.id.btn_exit_itemcoding);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            Button delete = findViewById(R.id.btn_delete_itemcoding);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    s = item_code.getText().toString();
                    String seq = seq_no.getText().toString();
                    if (s.equals("") != true & seq.equals("") != true) {

                        s = s.replace("/", "slash");
                        s = s.replace(".", "dot");
                        s=s.replace("#","hash");
                        s=s.replace("$","dollar");
                        s=s.replace("[","openbrace");
                        s=s.replace("]","closebrace");
                        s=s.replace("*","star");





                        DatabaseReference dbs_stock = FirebaseDatabase.getInstance().getReference().child("Stock");
                        dbs_stock.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {
                                    for(DataSnapshot node1: node.getChildren()){
                                        Log.d("sadia", "node: " + node.getKey());

                                        if(node1.getKey().equals(s)){
                                            node1.getRef().removeValue();
                                        }

                                        item_code.setText("");
                                        //seq_no.setText("");
                                        Toast.makeText(itemcoding.this, "Deleted", Toast.LENGTH_SHORT).show();
                                        //seq_no.setText("");
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        DatabaseReference dbs_stock_full = FirebaseDatabase.getInstance().getReference().child("StockFull").
                                child(s);
                        dbs_stock_full.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {

                                    node.getRef().removeValue();
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    Toast.makeText(itemcoding.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    //seq_no.setText("");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        DatabaseReference dbs_input = FirebaseDatabase.getInstance().getReference().child("InputSummary");

                        dbs_input.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {
                                    for(DataSnapshot node1: node.getChildren()){
                                        if(node1.getKey().matches(s)){
                                            node1.getRef().removeValue();
                                            item_code.setText("");
                                            //seq_no.setText("");
                                            Toast.makeText(itemcoding.this, "Deleted", Toast.LENGTH_SHORT).show();
                                            //seq_no.setText("");
                                        }

                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        DatabaseReference dbs_output = FirebaseDatabase.getInstance().getReference().child("OutputSummary");

                        dbs_output.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {
                                    for(DataSnapshot node1: node.getChildren()){
                                        if(node1.getKey().matches(s)){
                                            node1.getRef().removeValue();
                                            item_code.setText("");
                                            //seq_no.setText("");
                                            Toast.makeText(itemcoding.this, "Deleted", Toast.LENGTH_SHORT).show();
                                            //seq_no.setText("");
                                        }

                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        DatabaseReference dbs_itemrate = FirebaseDatabase.getInstance().getReference().child("ItemRate").
                                child(s);
                        dbs_itemrate.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {

                                    node.getRef().removeValue();
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    Toast.makeText(itemcoding.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    //seq_no.setText("");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                        ///
                        finish();


                    }
                }
            });


            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    code = sp.getSelectedItem().toString();
                    EditText seqno = findViewById(R.id.tv_sequenceno);
                    EditText tv_itemcode = findViewById(R.id.tv_itemcode);

                    right = code.replace("/", "slash");
                    right = right.replace(".", "dot");

                    right=right.replace("#","hash");
                    right=right.replace("$","dollar");
                    right=right.replace("[","openbrace");
                    right=right.replace("]","closebrace");
                    right=right.replace("*","star");


                    Log.d("Mam", right);

                    if (code.equals("Select Item code down") != true) {

                        n = code.replace("slash", "/");
                        n = n.replace("dot", ".");

                        n=n.replace("hash","#");
                        n=n.replace("dollar","$");
                        n=n.replace("openbrace","[");

                        n=n.replace("closebrace","]");
                        n=n.replace("star","*");




                    }
                    if (n != null) {


                        DatabaseReference dbs1 = FirebaseDatabase.getInstance().getReference().child("StockFull")
                                .child(right);//.child("Data"); //item_code
                        dbs1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {
                                    Log.d("tag", "node= " + node);
                                    if (node.getKey().equals("Seqenceno")) {
                                        Log.d("tag", "node1: " + node.getValue());
                                        seq_no.setText(node.getValue().toString());
                                        tv_itemcode.setText(n);

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else if (n == null) {


                        DatabaseReference dbs1 = FirebaseDatabase.getInstance().getReference().child("StockFull")
                                .child(right);//.child("Data"); //item_code
                        dbs1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {
                                    Log.d("tag", "node= " + node);
                                    if (node.getKey().equals("Seqenceno")) {
                                        Log.d("tag", "node1: " + node.getValue());
                                        seq_no.setText(node.getValue().toString());
                                        tv_itemcode.setText(code);

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /*    progressDialog = new ProgressDialog(itemcoding.this);
                    progressDialog.setMessage("Saving"); // Setting Message
                    progressDialog.setTitle("Item Coding"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();*/



                    String itemcode = item_code.getText().toString();
                    nn = itemcode;
                    Log.d("TAG", "ITM CODE: " + nn);
                    String sequenceno = seq_no.getText().toString();
                    HashMap<String, Object> itemcoding = new HashMap<>();
                    itemcoding.put("Seqenceno", sequenceno);
                    String n = itemcode;


                    int num = 0;
                    for (int x = 0; x < itemcode_list.size(); x++) {
                        Log.d("testing","itemcodes matching: "+itemcode_list.get(x));
                        if (n.equals(itemcode_list.get(x))) {
                            num = 1;
                            break;
                        }
                    }
                    Log.d("testing","num: "+num);

                    n = n.replace("/", "slash");
                    n = n.replace(".", "dot");
                    n=n.replace("#","hash");
                    n=n.replace("$","dollar");
                    n=n.replace("[","openbrace");
                    n=n.replace("]","closebrace");
                    n=n.replace("*","star");
                    if (num == 0) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String millisInString = dateFormat.format(new Date());

                        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("InputSummary").
                                child(millisInString);
                        root.child(n).setValue(itemcoding).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");


                                }
                            }
                        });

                        DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().child("Stock").child(millisInString);

                        root1.child(n).setValue(itemcoding).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");


                                }
                            }
                        });
                        DatabaseReference root1_full = FirebaseDatabase.getInstance().getReference().child("StockFull");

                        root1_full.child(n).setValue(itemcoding).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");



                                }
                            }
                        });

                        DatabaseReference itemrate = FirebaseDatabase.getInstance().getReference().child("ItemRate");

                        itemrate.child(n).setValue(itemcoding).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");



                                }
                            }
                        });

                        DatabaseReference outputsum = FirebaseDatabase.getInstance().getReference().child("OutputSummary").
                                child(millisInString);
                        outputsum.child(n).setValue(itemcoding).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    //  progressDialog.dismiss();

                                }

                            }
                        });


                        Log.d("goshi", "num: " + num);
                        //  if(num==0){
                        stock_data obj_stock = new stock_data("", "", "", "", "", "", "", "", "", "", "", "", "", "");
                        rates obj_rates = new rates("", "", "", "", "", "", "", "", "", "", "", "");
                        DatabaseReference dbs2 = FirebaseDatabase.getInstance().getReference().child("Stock").child(millisInString);

                        dbs2.child(n).child("Data").setValue(obj_stock);
                        DatabaseReference dbs2_full = FirebaseDatabase.getInstance().getReference().child("StockFull");

                        dbs2_full.child(n).child("Data").setValue(obj_stock);


                        DatabaseReference dbs3 = FirebaseDatabase.getInstance().getReference().child("InputSummary").
                                child(millisInString);
                        dbs3.child(n).child("Data").setValue(obj_stock);

                        DatabaseReference dbs4 = FirebaseDatabase.getInstance().getReference().child("ItemRate");
                        dbs4.child(n).child("Rate").setValue(obj_rates);

                        DatabaseReference dbs5 = FirebaseDatabase.getInstance().getReference().child("OutputSummary").
                                child(millisInString);
                        dbs5.child(n).child("Data").setValue(obj_stock);



                        // }


                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String millisInString = dateFormat.format(new Date());


                        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("InputSummary").
                                child(millisInString);
                        root.child(n).child("Seqenceno").setValue(sequenceno).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    //  progressDialog.dismiss();


                                }
                            }
                        });
                        DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().child("Stock").child(millisInString);

                        root1.child(n).child("Seqenceno").setValue(sequenceno).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    //   progressDialog.dismiss();


                                }
                            }
                        });
                        DatabaseReference root1_full = FirebaseDatabase.getInstance().getReference().child("StockFull");

                        root1_full.child(n).child("Seqenceno").setValue(sequenceno).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    //  progressDialog.dismiss();


                                }
                            }
                        });

                        DatabaseReference itemrate = FirebaseDatabase.getInstance().getReference().child("ItemRate");

                        itemrate.child(n).child("Seqenceno").setValue(sequenceno).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");
                                    //   progressDialog.dismiss();


                                }
                            }
                        });
                        DatabaseReference outputsum = FirebaseDatabase.getInstance().getReference().child("OutputSummary").
                                child(millisInString);
                        outputsum.child(n).child("Seqenceno").setValue(sequenceno).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(itemcoding.this, "Added into firebase", Toast.LENGTH_SHORT).show();
                                    arrayList.add(nn);
                                    item_code.setText("");
                                    //seq_no.setText("");


                                    // progressDialog.dismiss();

                                }

                            }
                        });



                    }
                    //finish();


                }
            });
        }
    }
}
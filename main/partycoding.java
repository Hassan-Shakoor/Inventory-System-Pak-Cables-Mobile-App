package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
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

import java.util.ArrayList;

public class partycoding extends AppCompatActivity {
    ArrayList<String> company_list = new ArrayList<>();
    ArrayList<String>id_list = new ArrayList<>();
    ArrayList<String> phone = new ArrayList<>();
    ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partycoding);
        getSupportActionBar().setTitle("Party Coding");
        progressDialog2 = new ProgressDialog(partycoding.this);
        progressDialog2.setMessage("Loading"); // Setting Message
        progressDialog2.setTitle("Part Coding"); // Setting Title
        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog2.setCanceledOnTouchOutside(false);
        progressDialog2.show();
        EditText p_id = findViewById(R.id.party_id);
        EditText p_company = findViewById(R.id.party_company);
        p_id.setInputType(InputType.TYPE_CLASS_NUMBER);
        Spinner spinner = findViewById(R.id.spinner_party_coding);

        EditText p_phone = findViewById(R.id.phone);
        p_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);



        DatabaseReference partycoding_dbs = FirebaseDatabase.getInstance().getReference().child("PartyCoding");
        partycoding_dbs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                company_list.clear();
                phone.clear();
                id_list.clear();
                company_list.add("Select Party");
                for (DataSnapshot node : snapshot.getChildren()) {

                    company_list.add(node.getKey());
                    for (DataSnapshot node1 : node.getChildren()) {
                        if (node1.getKey().equals("Id")) {
                            id_list.add(node1.getValue().toString());
                        } else if (node1.getKey().equals("phone")) {
                            phone.add(node1.getValue().toString());
                        }


                    }

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, company_list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setSelection(0);
                progressDialog2.dismiss();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item_selected = spinner.getSelectedItem().toString();
                int pos = spinner.getSelectedItemPosition();
                if (item_selected.equals("Select Party") != true) {
                    p_company.setText(item_selected);
                    DatabaseReference partycoding_dbs1 = FirebaseDatabase.getInstance().getReference().child("PartyCoding").
                            child(item_selected);
                    partycoding_dbs1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot node : snapshot.getChildren()) {
                                Log.d("ali", node.toString());
                                if (node.getKey().equals("Id")) {
                                    p_id.setText(node.getValue().toString());
                                } else if (node.getKey().equals("phone")) {
                                    p_phone.setText(node.getValue().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                } else {
                    p_company.setText("");
                }

                Log.d("sadia", "item: " + item_selected.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button exit = findViewById(R.id.party_btn_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button delete = findViewById(R.id.btn_party_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = p_company.getText().toString();
                String i = p_id.getText().toString();
                String p = p_phone.getText().toString();
                if (c.equals("") != true) {

                    DatabaseReference dbs_stock = FirebaseDatabase.getInstance().getReference().child("PartyCoding").
                            child(c);
                    dbs_stock.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot node : snapshot.getChildren()) {
                                Log.d("sadia", "node: " + node.getKey());
                                node.getRef().removeValue();
                                p_company.setText("");
                                p_phone.setText("");
                                p_id.setText("");
                                Toast.makeText(partycoding.this, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });


        Button save = findViewById(R.id.party_btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company = p_company.getText().toString();
                String idd = p_id.getText().toString();
                String phone = p_phone.getText().toString();

                DatabaseReference partydbs = FirebaseDatabase.getInstance().getReference().
                        child("PartyCoding").child(company).child("Id");
                partydbs.setValue(idd).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "idd: " + idd);
                            //p_id.setText("");


                        }
                    }
                });

                DatabaseReference partydbs2 = FirebaseDatabase.getInstance().getReference().
                        child("PartyCoding").child(company).child("company");
                partydbs2.setValue(company).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {


                            //p_company.setText("");

                        }
                    }
                });
                DatabaseReference partydbs3 = FirebaseDatabase.getInstance().getReference().
                        child("PartyCoding").child(company).child("phone");
                partydbs3.setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                           //finish();

                        }
                    }
                });
            }

        });

    }
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);



            DatabaseReference partycoding_dbs = FirebaseDatabase.getInstance().getReference().child("PartyCoding");
            partycoding_dbs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    company_list.clear();
                    phone.clear();
                    id_list.clear();
                    company_list.add("Select Party");
                    for (DataSnapshot node : snapshot.getChildren()) {

                        company_list.add(node.getKey());
                        for (DataSnapshot node1 : node.getChildren()) {
                            if (node1.getKey().equals("Id")) {
                                id_list.add(node1.getValue().toString());
                            } else if (node1.getKey().equals("phone")) {
                                phone.add(node1.getValue().toString());
                            }


                        }

                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, company_list);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayAdapter);
                    spinner.setSelection(0);
                    progressDialog2.dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item_selected = spinner.getSelectedItem().toString();
                    int pos = spinner.getSelectedItemPosition();
                    if (item_selected.equals("Select Party") != true) {
                        p_company.setText(item_selected);
                        DatabaseReference partycoding_dbs1 = FirebaseDatabase.getInstance().getReference().child("PartyCoding").
                                child(item_selected);
                        partycoding_dbs1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {
                                    Log.d("ali", node.toString());
                                    if (node.getKey().equals("Id")) {
                                        p_id.setText(node.getValue().toString());
                                    } else if (node.getKey().equals("phone")) {
                                        p_phone.setText(node.getValue().toString());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    } else {
                        p_company.setText("");
                    }

                    Log.d("sadia", "item: " + item_selected.toString());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            Button exit = findViewById(R.id.party_btn_exit);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            Button delete = findViewById(R.id.btn_party_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String c = p_company.getText().toString();
                    String i = p_id.getText().toString();
                    String p = p_phone.getText().toString();
                    if (c.equals("") != true) {

                        DatabaseReference dbs_stock = FirebaseDatabase.getInstance().getReference().child("PartyCoding").
                                child(c);
                        dbs_stock.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot node : snapshot.getChildren()) {
                                    Log.d("sadia", "node: " + node.getKey());
                                    node.getRef().removeValue();
                                    p_company.setText("");
                                    p_phone.setText("");
                                    p_id.setText("");
                                    Toast.makeText(partycoding.this, "Deleted", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            });


            Button save = findViewById(R.id.party_btn_save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String company = p_company.getText().toString();
                    String idd = p_id.getText().toString();
                    String phone = p_phone.getText().toString();

                    DatabaseReference partydbs = FirebaseDatabase.getInstance().getReference().
                            child("PartyCoding").child(company).child("Id");
                    partydbs.setValue(idd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TAG", "idd: " + idd);
                                //p_id.setText("");


                            }
                        }
                    });

                    DatabaseReference partydbs2 = FirebaseDatabase.getInstance().getReference().
                            child("PartyCoding").child(company).child("company");
                    partydbs2.setValue(company).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {


                                //p_company.setText("");

                            }
                        }
                    });
                    DatabaseReference partydbs3 = FirebaseDatabase.getInstance().getReference().
                            child("PartyCoding").child(company).child("phone");
                    partydbs3.setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                //finish();

                            }
                        }
                    });
                }

            });

        }


    }
}
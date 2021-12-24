package com.mamoo.istproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class before_stock extends AppCompatActivity {
    ProgressDialog progressDialog2;
    ArrayList<String> date_list=new ArrayList<String >();
    DatePickerDialog.OnDateSetListener setListener;
    ArrayList<String> company_list = new ArrayList<>();
    ArrayList<String> item_code_list = new ArrayList<>();
    ArrayList<String> party = new ArrayList<>();
    String item_selected="";
    String item_selected0="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_stock);
        company_list.add("Please Select any Item");
        company_list.add("Red");
        company_list.add("Black");
        company_list.add("Yellow");
        company_list.add("Blue");
        company_list.add("Green");
        company_list.add("White");
        company_list.add("Other");
        company_list.add("2 Core");
        company_list.add("3 Core");
        company_list.add("4 Core");
        company_list.add("5 Core");
        company_list.add("6 Core");
        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_PORTRAIT) {

        progressDialog2 = new ProgressDialog(this);
        getSupportActionBar().setTitle("Stock");
        progressDialog2.setMessage("Loading"); // Setting Message
        progressDialog2.setTitle("Select Stock Date"); // Setting Title
        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog2.setCanceledOnTouchOutside(false);
        progressDialog2.show();


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        TextView tv_date = findViewById(R.id.tv_date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        tv_date.setText("");
        party.add("Select Party");

        DatabaseReference party_dbs = FirebaseDatabase.getInstance().getReference().child("PartyCoding");
        party_dbs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot partys : snapshot.getChildren()) {
                    party.add(String.valueOf(partys.getKey()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        before_stock.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                month = month + 1;
                String m = "", day = "";
                if (month < 10) {
                    m = "0" + month;
                }
                if (dayofMonth < 10) {
                    day = "0" + dayofMonth;
                }
                if (month >= 10) {
                    m = "" + month;
                }
                if (dayofMonth >= 10) {
                    day = "" + dayofMonth;
                }

                String date = day + "-" + m + "-" + year;

                tv_date.setText(date);


            }
        };
        item_code_list.add("Please Select any Item Code");
        DatabaseReference stock_full_dbs = FirebaseDatabase.getInstance().getReference().child("StockFull");
        stock_full_dbs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot node : snapshot.getChildren()) {
                    Log.d("rif", node.getKey());


                    ///
                    String n = node.getKey().replace("slash", "/");
                    n = n.replace("dot", ".");

                    n=n.replace("hash","#");
                    n=n.replace("dollar","$");
                    n=n.replace("openbrace","[");

                    n=n.replace("closebrace","]");
                    n=n.replace("star","*");
                    item_code_list.add(n);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference date_root = FirebaseDatabase.getInstance().getReference().child("Stock");
        date_root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {

                        String datee = data.getKey();
                        Log.d("hash", "date: " + datee);
                        date_list.add(datee);
                        count++;

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
                if (count == snapshot.getChildrenCount()) {
                    if (date_list.size() != 0) {
                        tv_date.setText(date_list.get(date_list.size() - 1));
                    }
                    progressDialog2.dismiss();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Button report = findViewById(R.id.btn_report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_date.getText().toString().equals("") != true) {
                    Intent intent = new Intent(getApplicationContext(), temp_stock.class);
                    intent.putExtra("date", tv_date.getText().toString());
                    startActivity(intent);

                } else {
                    Toast.makeText(before_stock.this, "Select Date", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // EditText item_ledger=findViewById(R.id.ledger_itemcode);
        Button ledger = findViewById(R.id.ledger);
        ledger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(before_stock.this);
                builder.setTitle("Ledger");

                final Spinner input = new Spinner(before_stock.this);
                input.setPadding(0, 70, 0, 0);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, item_code_list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                input.setAdapter(arrayAdapter);
                input.setSelection(0);


                final Spinner input1 = new Spinner(before_stock.this);
                input1.setPadding(0, 60, 0, 0);

                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, company_list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                input1.setAdapter(arrayAdapter1);
                input1.setSelection(0);

                final TextView from = new TextView(before_stock.this);
                from.setHint("Select Starting Date");
                from.setPadding(0, 60, 0, 50);
                from.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                before_stock.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                String m = "", d = "";
                                if (month < 10) {
                                    m = "0" + month;
                                }
                                if (day < 10) {
                                    d = "0" + day;
                                }
                                if (month >= 10) {
                                    m = "" + month;
                                }
                                if (day >= 10) {
                                    d = "" + day;
                                }
                                String date = d + "-" + m + "-" + year;
                                from.setText(date);
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });
                final TextView to = new TextView(before_stock.this);
                to.setHint("Select Ending Date");
                to.setPadding(0, 60, 0, 50);
                to.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                before_stock.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                String m = "", d = "";
                                if (month < 10) {
                                    m = "0" + month;
                                }
                                if (day < 10) {
                                    d = "0" + day;
                                }
                                if (month >= 10) {
                                    m = "" + month;
                                }
                                if (day >= 10) {
                                    d = "" + day;
                                }
                                String date = d + "-" + m + "-" + year;
                                to.setText(date);
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });


                LinearLayout ll = new LinearLayout(before_stock.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(input);
                ll.addView(input1);
                ll.addView(from);
                ll.addView(to);

                builder.setView(ll);


                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getSelectedItem().toString();
                        String m_Text1 = input1.getSelectedItem().toString();
                        String date1 = from.getText().toString();
                        String date2 = to.getText().toString();

                        item_selected = m_Text1;
                        item_selected0 = m_Text;

                        Log.d("faiz", m_Text);
                        Log.d("faiz", m_Text1);
                        if (!m_Text.matches("Please Select any Item") && !m_Text1.matches("Please Select any Item Code")
                                && !date1.matches("") && !date2.matches("")) {
                            Intent intent = new Intent(getApplicationContext(), change_ledger.class);
                            intent.putExtra("date", tv_date.getText().toString());
                            intent.putExtra("ledgerItem", m_Text);
                            intent.putExtra("item", m_Text1);
                            intent.putExtra("from", date1);
                            intent.putExtra("to", date2);
                            startActivity(intent);
                        } else {

                            Toast.makeText(before_stock.this, "Please fill all the entries", Toast.LENGTH_SHORT).show();
                        }


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
        });
        Button dealer = findViewById(R.id.btn_dealer_ledger);
        dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(before_stock.this);
                builder.setTitle("Dealer Ledger");
                final Spinner party_selection = new Spinner(before_stock.this);
                party_selection.setPadding(0, 60, 0, 0);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, party);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                party_selection.setAdapter(arrayAdapter);
                party_selection.setSelection(0);


                LinearLayout ll = new LinearLayout(before_stock.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(party_selection);

                builder.setView(ll);


                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = party_selection.getSelectedItem().toString();

                        if (!m_Text.matches("Select Party")) {
                            Intent intent = new Intent(getApplicationContext(), latest_dealer_ledger.class);
                            intent.putExtra("party", m_Text);
                            startActivity(intent);
                        } else {

                            Toast.makeText(before_stock.this, "Please select party for dealer ledger", Toast.LENGTH_SHORT).show();
                        }


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
        });
    }
        if(orientation1==Configuration.ORIENTATION_LANDSCAPE){

            progressDialog2 = new ProgressDialog(this);
            getSupportActionBar().setTitle("Stock");
            progressDialog2.setMessage("Loading"); // Setting Message
            progressDialog2.setTitle("Select Stock Date"); // Setting Title
            progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog2.setCanceledOnTouchOutside(false);
            progressDialog2.show();


            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            TextView tv_date = findViewById(R.id.tv_date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            tv_date.setText("");
            party.add("Select Party");

            DatabaseReference party_dbs = FirebaseDatabase.getInstance().getReference().child("PartyCoding");
            party_dbs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot partys : snapshot.getChildren()) {
                        party.add(String.valueOf(partys.getKey()));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            tv_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            before_stock.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            });
            setListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                    month = month + 1;
                    String m = "", day = "";
                    if (month < 10) {
                        m = "0" + month;
                    }
                    if (dayofMonth < 10) {
                        day = "0" + dayofMonth;
                    }
                    if (month >= 10) {
                        m = "" + month;
                    }
                    if (dayofMonth >= 10) {
                        day = "" + dayofMonth;
                    }

                    String date = day + "-" + m + "-" + year;

                    tv_date.setText(date);


                }
            };
            item_code_list.add("Please Select any Item Code");
            DatabaseReference stock_full_dbs = FirebaseDatabase.getInstance().getReference().child("StockFull");
            stock_full_dbs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot node : snapshot.getChildren()) {
                        Log.d("rif", node.getKey());
                        String n = node.getKey().replace("slash", "/");
                        n = n.replace("dot", ".");

                        n=n.replace("hash","#");
                        n=n.replace("dollar","$");
                        n=n.replace("openbrace","[");

                        n=n.replace("closebrace","]");
                        n=n.replace("star","*");
                        item_code_list.add(n);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            DatabaseReference date_root = FirebaseDatabase.getInstance().getReference().child("Stock");
            date_root.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int count = 0;
                    if (snapshot.exists()) {
                        for (DataSnapshot data : snapshot.getChildren()) {

                            String datee = data.getKey();
                            Log.d("hash", "date: " + datee);
                            date_list.add(datee);
                            count++;

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
                    if (count == snapshot.getChildrenCount()) {
                        if (date_list.size() != 0) {
                            tv_date.setText(date_list.get(date_list.size() - 1));
                        }
                        progressDialog2.dismiss();


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            Button report = findViewById(R.id.btn_report);
            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (tv_date.getText().toString().equals("") != true) {
                        Intent intent = new Intent(getApplicationContext(), temp_stock.class);
                        intent.putExtra("date", tv_date.getText().toString());
                        startActivity(intent);

                    } else {
                        Toast.makeText(before_stock.this, "Select Date", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            // EditText item_ledger=findViewById(R.id.ledger_itemcode);
            Button ledger = findViewById(R.id.ledger);
            ledger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(before_stock.this);
                    builder.setTitle("Ledger");

                    final Spinner input = new Spinner(before_stock.this);
                    input.setPadding(0, 70, 0, 0);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, item_code_list);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    input.setAdapter(arrayAdapter);
                    input.setSelection(0);


                    final Spinner input1 = new Spinner(before_stock.this);
                    input1.setPadding(0, 60, 0, 0);

                    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, company_list);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    input1.setAdapter(arrayAdapter1);
                    input1.setSelection(0);

                    final TextView from = new TextView(before_stock.this);
                    from.setHint("Select Starting Date");
                    from.setPadding(0, 60, 0, 50);
                    from.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DatePickerDialog datePickerDialog = new DatePickerDialog(
                                    before_stock.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    month = month + 1;
                                    String m = "", d = "";
                                    if (month < 10) {
                                        m = "0" + month;
                                    }
                                    if (day < 10) {
                                        d = "0" + day;
                                    }
                                    if (month >= 10) {
                                        m = "" + month;
                                    }
                                    if (day >= 10) {
                                        d = "" + day;
                                    }
                                    String date = d + "-" + m + "-" + year;
                                    from.setText(date);
                                }
                            }, year, month, day);
                            datePickerDialog.show();
                        }
                    });
                    final TextView to = new TextView(before_stock.this);
                    to.setHint("Select Ending Date");
                    to.setPadding(0, 60, 0, 50);
                    to.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DatePickerDialog datePickerDialog = new DatePickerDialog(
                                    before_stock.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    month = month + 1;
                                    String m = "", d = "";
                                    if (month < 10) {
                                        m = "0" + month;
                                    }
                                    if (day < 10) {
                                        d = "0" + day;
                                    }
                                    if (month >= 10) {
                                        m = "" + month;
                                    }
                                    if (day >= 10) {
                                        d = "" + day;
                                    }
                                    String date = d + "-" + m + "-" + year;
                                    to.setText(date);
                                }
                            }, year, month, day);
                            datePickerDialog.show();
                        }
                    });


                    LinearLayout ll = new LinearLayout(before_stock.this);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.addView(input);
                    ll.addView(input1);
                    ll.addView(from);
                    ll.addView(to);

                    builder.setView(ll);


                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String m_Text = input.getSelectedItem().toString();
                            String m_Text1 = input1.getSelectedItem().toString();
                            String date1 = from.getText().toString();
                            String date2 = to.getText().toString();

                            item_selected = m_Text1;
                            item_selected0 = m_Text;

                            Log.d("faiz", m_Text);
                            Log.d("faiz", m_Text1);
                            if (!m_Text.matches("Please Select any Item") && !m_Text1.matches("Please Select any Item Code")
                                    && !date1.matches("") && !date2.matches("")) {
                                Intent intent = new Intent(getApplicationContext(), change_ledger.class);
                                intent.putExtra("date", tv_date.getText().toString());
                                intent.putExtra("ledgerItem", m_Text);
                                intent.putExtra("item", m_Text1);
                                intent.putExtra("from", date1);
                                intent.putExtra("to", date2);
                                startActivity(intent);
                            } else {

                                Toast.makeText(before_stock.this, "Please fill all the entries", Toast.LENGTH_SHORT).show();
                            }


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
            });
            Button dealer = findViewById(R.id.btn_dealer_ledger);
            dealer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(before_stock.this);
                    builder.setTitle("Dealer Ledger");
                    final Spinner party_selection = new Spinner(before_stock.this);
                    party_selection.setPadding(0, 60, 0, 0);

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, party);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    party_selection.setAdapter(arrayAdapter);
                    party_selection.setSelection(0);


                    LinearLayout ll = new LinearLayout(before_stock.this);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.addView(party_selection);

                    builder.setView(ll);


                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String m_Text = party_selection.getSelectedItem().toString();

                            if (!m_Text.matches("Select Party")) {
                                Intent intent = new Intent(getApplicationContext(), latest_dealer_ledger.class);
                                intent.putExtra("party", m_Text);
                                startActivity(intent);
                            } else {

                                Toast.makeText(before_stock.this, "Please select party for dealer ledger", Toast.LENGTH_SHORT).show();
                            }


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
            });
        }

    }
}
package com.example.barbershop_hit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import java.util.Map;


public class OrdersActivity extends AppCompatActivity {

    private static final String TAG = "OrdersActivity";
    private ListView mListView;
    public Button btnDelete;
    public EditText editTextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
                mListView = findViewById(R.id.listView);
                btnDelete = findViewById(R.id.del_btn);
                editTextId = findViewById(R.id.editTId);
                populateListView();
                DeleteData();
            }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");
        ArrayList<String> dataList = new ArrayList<String>();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://barbershophit-1e7e1-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("appointment");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();

                for (Map.Entry<String, String> e : map.entrySet()) {
                    Object v = e.getValue();
                    String[] a = v.toString().split(",");
                    for (int i=0; i< a.length; i++){
                        String[] g = a[i].split("=");
                        dataList.add(g[1]);
                        if (i == a.length - 1) {
                            ListAdapter adapter = new ArrayAdapter<>(OrdersActivity.this, android.R.layout.simple_list_item_1, dataList);
                            mListView.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void DeleteData() {
        btnDelete.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://barbershophit-1e7e1-default-rtdb.firebaseio.com/");
                    DatabaseReference myRef = database.getReference("appointment").child(editTextId.getText().toString());
                    myRef.removeValue();
                    populateListView();
                }
            }
        );
    }
        }


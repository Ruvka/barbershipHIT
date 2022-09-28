package com.example.barbershop_hit;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppointmentActivity extends AppCompatActivity {
    private Button book_btn,btnGet;
    private EditText names,emails,phones;
    CalendarView calendarView;
    TextView dates,times;
    TimePicker picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        book_btn = findViewById(R.id.bookbtn);
        names = findViewById(R.id.txt_name);
        emails = findViewById(R.id.txt_email);
        phones = findViewById(R.id.txt_phone);
        times = findViewById(R.id.txt_time);
        calendarView = findViewById(R.id.calendarView);
        dates = findViewById(R.id.txt_date);

        picker=findViewById(R.id.timePicker1);
        picker.setIs24HourView(true);

        btnGet=findViewById(R.id.button1);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int hour, minute;

                String am_pm;
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = picker.getHour();
                    minute = picker.getMinute();
                }
                else{
                    hour = picker.getCurrentHour();
                    minute = picker.getCurrentMinute();
                }
                if(hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                }
                else
                {
                    am_pm="AM";
                }
                String c = hour +":"+ minute+" "+am_pm;
                times.setText(c);
            }
        });



//Database entry..
        book_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = names.getText().toString();
                String mail = emails.getText().toString();
                String phoneNumber = phones.getText().toString();
                String selectedDate = dates.getText().toString();
                String selectedTime = times.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if ((names.length() != 0)&&(emails.getText().toString().trim().matches(emailPattern))&&(phones.length() == 10)&&(dates.length() != 0)&&(times.length() != 0)) {
                    AddData(name,mail,phoneNumber,selectedDate,selectedTime);
                    names.setText("");
                    emails.setText("");
                    phones.setText("");
                    dates.setText("");
                    times.setText("");
                    startActivity(new Intent(AppointmentActivity.this,MainActivity.class));
                }
                else {
                    toastMessage("Something wrong");
                    toastMessage("Please check entered value");
                    }
            }
        });



    }

    public void AddData(String name, String mail, String phoneNumber, String selectedDate, String selectedTime) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://barbershophit-1e7e1-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("appointment").child(phoneNumber);

        AppointmentData AppointmentData = new AppointmentData(name, mail, phoneNumber, selectedDate, selectedTime);
        myRef.setValue(AppointmentData);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public void datevalid(View view) {
        dates = findViewById(R.id.txt_date);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dates.setText("");
                int d = dayOfMonth;
                int m = month;
                int y = year;
                String c = d + "-" + m + "-" + y;
                dates.append(c);
            }
        });

    }

}



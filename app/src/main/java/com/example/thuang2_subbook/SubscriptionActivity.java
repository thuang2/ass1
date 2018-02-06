package com.example.thuang2_subbook;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SubscriptionActivity extends AppCompatActivity {
    private String purpose;

    private EditText name;
    private EditText charge;
    private EditText date;
    private EditText comment;


    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        Intent intent = getIntent();
        purpose = intent.getStringExtra("purpose");

        gson = new Gson();
        Button delete = findViewById(R.id.btn_delete);
        name = findViewById(R.id.et_name);
        charge = findViewById(R.id.et_charge);
        date = findViewById(R.id.et_date);
        comment = findViewById(R.id.ed_comment);



        if (purpose.equals("edit")){
            Subscription subscription = gson.fromJson(intent.getStringExtra("content"),Subscription.class);
            name.setText(subscription.getName());
            charge.setText(subscription.getMonthly_charge().toString());
            date.setText(subscription.getDate().toString());
            comment.setText(subscription.getComment());

        }

        final Activity that = this;




        if (purpose.equals("add")){
            delete.setVisibility(View.GONE);
        }


    }

    private boolean checkFields(){
        if (name.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (date.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (charge.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Monthly charge cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    public void onConfirmClicked(View view){
        if (checkFields()) {
            Date datetime = new Date();

            Subscription subscription = new Subscription(
                    name.getText().toString(),
                    datetime,
                    Double.parseDouble(charge.getText().toString()),
                    comment.getText().toString()
            );
            String subscription_str = gson.toJson(subscription);
            Intent intent = new Intent();
            intent.putExtra("result",subscription_str);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    public void onDeleteClicked(View view){
        Intent intent = new Intent();
        intent.putExtra("is_deleted",true);
        setResult(RESULT_OK,intent);
        finish();
    }
}

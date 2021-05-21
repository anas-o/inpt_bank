package com.example.inptbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class balance extends AppCompatActivity {
    private TextView balance;
    private float fbalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        balance = (TextView)findViewById(R.id.solde);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
            fbalance = bundle.getFloat("Balance");

        balance.setText("Your balance is : " + fbalance);
    }
}
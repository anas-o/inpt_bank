package com.example.inptbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Transfer extends AppCompatActivity {
    private float fbalance, newBalance, amount;
    //private float recbalance;
    private Integer receiver;
    private TextView remainingBalance;
    private EditText ribReceiver, amountToTransfer;
    private Button bTransfer;
    private String nomBase = "bdINPTbank.sqlite";
    private Integer vsersionBase = 1;
    private Donnees accesBD;
    private SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Bundle bundle = getIntent().getExtras();

        remainingBalance = (TextView) findViewById(R.id.remainingBalance);
        ribReceiver = (EditText) findViewById(R.id.ribReceiver);
        amountToTransfer = (EditText) findViewById(R.id.amountToTransfer);
        bTransfer = (Button) findViewById(R.id.bTransfer);

        if (bundle != null) {
            fbalance = bundle.getFloat("Balance");
            newBalance = fbalance;
        }
        accesBD = new Donnees(this, nomBase, null, vsersionBase);
    }
    
    public void bTransfer(View view){
        try{
            receiver = Integer.parseInt(ribReceiver.getText().toString());
            amount = Float.parseFloat(amountToTransfer.getText().toString());
            /*
            bd = accesBD.getReadableDatabase();
            String req = "select rib from profils where rib = \'" + receiver + "\';";
            Cursor query = bd.rawQuery(req, null);
            query.close();*/

            newBalance = fbalance - amount;
            if(newBalance>=0){
                bd = accesBD.getWritableDatabase();
                String req1 = "UPDATE profils SET solde = solde + " + amount + " WHERE rib = \'" + receiver + "\';";
                String req2 = "UPDATE profils SET solde = " + newBalance + " WHERE rib = \'" + MainActivity.riba7 + "\';";
                bd.execSQL(req1);
                bd.execSQL(req2);
                remainingBalance.setText("Your balance is : " + newBalance);
            } else {
                Toast.makeText(getBaseContext(), "Sorry :( ... You can't make this operation", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e){
            System.out.println(e);
            Toast.makeText(getBaseContext(), "Make sure you entered correctly all the fields", Toast.LENGTH_LONG).show();
        }
    }
}
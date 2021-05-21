package com.example.inptbank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {
    private Button bSolde, bDevise, bVirement, bMap;
    private TextView tvMessage;
    private String nomBase = "bdINPTbank.sqlite";
    private Integer vsersionBase = 1;
    private Donnees accesBD;
    private SQLiteDatabase bd;
    private String prenom, nom;
    private float solde;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);
        bSolde = (Button)findViewById(R.id.bSolde);
        bDevise = (Button)findViewById(R.id.bDevise);
        bVirement = (Button)findViewById(R.id.bVirement);
        bMap = (Button)findViewById(R.id.bMap);

        tvMessage = (TextView)findViewById(R.id.tvMessage);

        accesBD = new Donnees(this, nomBase, null, vsersionBase);
        bd = accesBD.getReadableDatabase();
        String req = "select prenom, nom, solde from profils where rib = \'" + MainActivity.riba7 + "\';";
        Cursor query = bd.rawQuery(req, null);
        if (query.moveToFirst()) {
            prenom = query.getString(0);
            nom = query.getString(1);
            solde = Float.parseFloat(query.getString(2));
        }
        query.close();
        tvMessage.setText("Bonjour Mr " + nom);
        //Intent intent = getIntent();
    }
    public void bMap(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=33.9804179,-6.8672307"));
        startActivity(intent);
    }
    public void bDevise(View view){
        Intent intent = new Intent(this,ConvertActivity.class);
        startActivity(intent);

    }

    public void bCheckBalance(View view) {
        Intent intent = new Intent(this, balance.class);
        intent.putExtra("Balance", this.solde);
        startActivity(intent);
    }

    public void transfer(View view) {
        Intent intent = new Intent(this, Transfer.class);
        intent.putExtra("Balance", this.solde);
        startActivity(intent);
    }
}

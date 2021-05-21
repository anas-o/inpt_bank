package com.example.inptbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "db";
    private EditText email, password;
    private Button newAccount, bConfirm, bCallAssistance;
    private String nomBase = "bdINPTbank.sqlite";
    private Integer vsersionBase = 1;
    private Donnees accesBD;
    private SQLiteDatabase bd;
    public static Integer riba7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accesBD = new Donnees(this, nomBase, null, vsersionBase);

        if (testRIb(1234567, 2224) == false) {
            bd = accesBD.getWritableDatabase();
            String req1 = "INSERT INTO profils (rib, prenom, nom, gabPassword, solde)"
                    + "VALUES (1234567, \"Anas\", \"Ouadrhiri\", 2224, 114000);\n";
            String req2 = "INSERT INTO profils(rib, prenom, nom, gabPassword, solde)"
                    + "VALUES (7654321, \"Ayyoub\", \"Seghir\", 1125, 225010);\n";
            String req3 = "INSERT INTO profils(rib, prenom, nom, gabPassword, solde)"
                    + "VALUES (2985634, \"Asaad\", \"Belarbi\", 1111, 509121);\n";
            String req4 = "INSERT INTO profils(rib, prenom, nom, gabPassword, solde)"
                    + "VALUES (1222526, \"Faissal\", \"Ouardi\", 5021, 102);";
            try {
                bd.execSQL(req1);
                bd.execSQL(req2);
                bd.execSQL(req3);
                bd.execSQL(req4);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: ", e);
                Toast.makeText(this, "erreur: " + e, Toast.LENGTH_LONG);
            }
        }

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        bConfirm = (Button)findViewById(R.id.bConfirm);
        bCallAssistance = (Button)findViewById(R.id.bAssistance);
        newAccount = (Button)findViewById(R.id.bAccount);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 1);
        }

    }
    public Boolean testRIb(Integer rib, Integer pass) {
        bd = accesBD.getReadableDatabase();
        String prinom = "";
        String sel = "SELECT prenom FROM profils WHERE rib = " + rib.toString() + " AND gabPassword = " + pass.toString() + ";";
        Cursor query = bd.rawQuery(sel, null);
        if (query.moveToFirst()) {
            prinom = query.getString(0);
        }
        query.close();

        return (!prinom.equals(""));
    }
    public Boolean verify(String mail, String paSS) {
        bd = accesBD.getReadableDatabase();
        Integer ribiJamila = 0;
        String sel = "SELECT rib FROM profils WHERE emailDB = \'" + mail + "\' AND passwordDB = \'" + paSS + "\';";
        Cursor query = bd.rawQuery(sel, null);
        if (query.moveToFirst()) {
            ribiJamila = Integer.parseInt(query.getString(0));
        }
        query.close();
        if(ribiJamila != 0){
            riba7 = ribiJamila;
            return true;
        } return false;
    }
    public void bAccount(View view) {
        Intent intent = new Intent(this, AccountCreation.class);
        startActivity(intent);
    }

    public void bAssistance(View view) {
        String phoneNumber = "tel:0675400334";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));
        startActivity(intent);
    }


    public void bConfirm(View view) {
        if (verify(email.getText().toString(),password.getText().toString() )) {
            Toast.makeText(getBaseContext(), "Connected", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, AccountActivity.class);
            try {
                startActivity(intent);
            }
            catch (ActivityNotFoundException e){
                System.out.println(e);
            }
        }
        else
            Toast.makeText(getBaseContext(), "Identification Error", Toast.LENGTH_LONG).show();
    }
}
package com.example.inptbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class AccountCreation extends AppCompatActivity {
    private EditText ribcreate, emailcreate, passwordcreate, gabpassword;
    private Button bCreate;
    private String nomBase = "bdINPTbank.sqlite";
    private Integer vsersionBase = 1;
    private Donnees accesBD;
    private SQLiteDatabase bd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        ribcreate = (EditText)findViewById(R.id.ribcreate);
        emailcreate = (EditText)findViewById(R.id.emailcreate);
        passwordcreate = (EditText)findViewById(R.id.passwordcreate);
        gabpassword = (EditText)findViewById(R.id.gabpassword);
        bCreate = (Button)findViewById(R.id.bCreate);

        accesBD = new Donnees(this, nomBase, null, vsersionBase);
    }
    public void insertElement(String emayl, String password, Integer riba) {
        bd = accesBD.getWritableDatabase();
        String req = "UPDATE profils"
                + " SET emailDB = \'" + emayl + "\', passwordDB = \'" + password +
                "\' WHERE RIB = " + riba.toString()  + ";";
        bd.execSQL(req);

    }
    public Boolean testRIb(Integer rib, Integer pass) {
        bd = accesBD.getReadableDatabase();
        String prinom = " ";
        String sel = "SELECT prenom FROM profils WHERE rib = " + rib.toString() + " AND gabPassword = " + pass.toString() + ";";
        Cursor query = bd.rawQuery(sel, null);
        if (query.moveToFirst()) {
            prinom = query.getString(0);
        }
        query.close();
        System.out.println("prenom:" + prinom);

        return (!prinom.equals(" "));
    }
    @SuppressLint("ShowToast")
    public void bCreate(View view){
        Date dateCreation = new Date();
        String  emailDB = null, passwordDB = null;
        Integer rib = 0, gabPassword = 0;
        Boolean flag = false;
            try {
                rib = Integer.parseInt(ribcreate.getText().toString());
                emailDB = emailcreate.getText().toString();
                passwordDB = passwordcreate.getText().toString();
                gabPassword = Integer.parseInt(gabpassword.getText().toString());
            } catch (Exception e) {
                System.out.println(e);
                Toast.makeText(getBaseContext(), "Informations not Complete", Toast.LENGTH_SHORT).show();
                rib = 0; gabPassword = 0;
            }
            finally {
                flag = testRIb(rib, gabPassword);
                System.out.println(flag.toString());
                Log.i("tag", "bCreate: brkt");
            }


            /*System.out.println(flag.toString());
            Log.i("tag", "bCreate: brkt");*/

        if (flag) {
            insertElement(emailDB, passwordDB, rib);
            Toast.makeText(getBaseContext(), "Account created", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
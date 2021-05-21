package com.example.inptbank;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConvertActivity extends AppCompatActivity {
    public static BreakIterator data;
    List<String> keysList;
    Spinner toCurrency;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        toCurrency = (Spinner)findViewById(R.id.planets_spinner);
        final EditText edtDirhamValue = (EditText)findViewById(R.id.editText4);
        final Button btnConvert = (Button)findViewById(R.id.button);
        textView =(TextView) findViewById(R.id.textView7);
        try {
            loadConvTypes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtDirhamValue.getText().toString().isEmpty())
                {
                    String toCurr = toCurrency.getSelectedItem().toString();
                    double dirhamValue = Double.valueOf(edtDirhamValue.getText().toString());

                    Toast.makeText(ConvertActivity.this, "Please Wait..", Toast.LENGTH_SHORT).show();
                    try {
                        convertCurrency(toCurr, dirhamValue);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(ConvertActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ConvertActivity.this, "Please Enter a Value to Convert..", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void loadConvTypes() throws IOException {

        String url = "https://v6.exchangerate-api.com/v6/16cf229429830c55588372bc/latest/MAD";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/json")
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                Toast.makeText(ConvertActivity.this, mMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String mMessage = response.body().string();


                ConvertActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(ConvertActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject obj = new JSONObject(mMessage);
                            JSONObject  b = obj.getJSONObject("conversion_rates");

                            Iterator keysToCopyIterator = b.keys();
                            keysList = new ArrayList<String>();

                            while(keysToCopyIterator.hasNext()) {

                                String key = (String) keysToCopyIterator.next();

                                keysList.add(key);

                            }


                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, keysList );
                            toCurrency.setAdapter(spinnerArrayAdapter);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }




        });
    }

    public void convertCurrency(final String toCurr, final double dirhamValue) throws IOException {

        String url = "https://v6.exchangerate-api.com/v6/16cf229429830c55588372bc/latest/MAD";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/json")
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                Toast.makeText(ConvertActivity.this, mMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String mMessage = response.body().string();
                ConvertActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(ConvertActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject obj = new JSONObject(mMessage);
                            JSONObject  b = obj.getJSONObject("conversion_rates");

                            String val = b.getString(toCurr);

                            double output = dirhamValue*Double.valueOf(val);


                            textView.setText(String.valueOf(output));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }





        });
    }
}

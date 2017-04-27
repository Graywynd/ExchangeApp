package com.example.saif.echangeapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by saif on 19/04/2017.
 */
public class ConvertisseurActivity  extends AppCompatActivity {

    private Spinner spinner1, spinner2;
    private Button btnSubmit;
    private EditText text_devise_from,text_devise_to;

    String devise_from = "USD";
    String devise_to = "USD";
    double valeur_from,valeur_to,conversion_rate;


    String result = null;
    String myurl = "http://api.fixer.io/latest?base=USD";
    HashMap<String,Double> rates = new HashMap<String,Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_convertisseur);


        setEditTextViews();
        addListenerOnSpinnerItemSelection1();
        addListenerOnSpinnerItemSelection2();
        addListenerOnButtonCalcul();
    }




    private class Tache extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(ConvertisseurActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Processing : Recupération des taux de conversion ...");
            this.dialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            URL url = null;
            try {


                 url = new URL(myurl);
            }catch (IOException e) {
                e.printStackTrace();
            }

                try {


                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);

                urlConnection.setDoOutput(true);

                urlConnection.connect();

                BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

                char[] buffer = new char[1024];

                String jsonString = new String();

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();

                jsonString = sb.toString();



                    JSONObject jsonObj = new JSONObject(jsonString);

                    // Getting JSON Array node
                    JSONObject c = jsonObj.getJSONObject("rates");


                        Iterator<String> keys = c.keys();

                        while (keys.hasNext()) {
                            String key = keys.next();
                            rates.put(key.toString(),Double.parseDouble(c.get(key).toString()));
                             }



            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {



            this.dialog.dismiss();
        }
    }


    private void addListenerOnSpinnerItemSelection1() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                String abr = arg0.getItemAtPosition(arg2).toString();
                abr = abr.substring(abr.length() - 3);
                devise_from = abr;
                myurl = "http://api.fixer.io/latest?base=" + abr;
                System.out.println("devise from ! " + abr);
                new Tache().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


    }

    private void addListenerOnSpinnerItemSelection2() {
        spinner2 = (Spinner) findViewById(R.id.spinner);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                String abr = arg0.getItemAtPosition(arg2).toString();
                abr = abr.substring(abr.length() - 3);
                devise_to = abr;

                System.out.println("devise to ! " + abr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
    }

    private void addListenerOnButtonCalcul() {
        btnSubmit = (Button) findViewById(R.id.button4);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(devise_to.equals(devise_from)){
                    Toast.makeText(ConvertisseurActivity.this,"Veuillez choisir la devise à convertir !",Toast.LENGTH_SHORT).show();
                }else {


                    valeur_from = Double.parseDouble(text_devise_from.getText().toString());
                    conversion_rate = rates.get(devise_to);
                    valeur_to = valeur_from * conversion_rate;

                    text_devise_to.setText(Double.toString(valeur_to));
                }
            }

        });
    }

    private void setEditTextViews() {
        text_devise_from = (EditText) findViewById(R.id.editText);
        text_devise_to = (EditText) findViewById(R.id.editText2);
    }

}
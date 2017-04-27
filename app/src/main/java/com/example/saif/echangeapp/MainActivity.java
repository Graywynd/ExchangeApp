package com.example.saif.echangeapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button button_marche = null;
    Button button_palmares = null;
    Button button_convertisseur = null;

    String marche_url="http://www.ilboursa.com/marches/aaz.aspx";
    Document page_marche = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_marche = (Button) findViewById(R.id.button);
        button_palmares = (Button) findViewById(R.id.button2);
        button_convertisseur = (Button) findViewById(R.id.button3);

        button_marche.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent activite_marche = new Intent(MainActivity.this, MarcheActivity.class);
                startActivity(activite_marche);

            }
        });
        button_palmares.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent activite_marche = new Intent(MainActivity.this, PalmaresActivity.class);
                startActivity(activite_marche);

            }
        });
        button_convertisseur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent activite_marche = new Intent(MainActivity.this, ConvertisseurActivity.class);
                startActivity(activite_marche);

            }
        });




    }








}

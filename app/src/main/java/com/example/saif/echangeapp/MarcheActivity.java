package com.example.saif.echangeapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class MarcheActivity extends AppCompatActivity {

    String marche_url="http://www.ilboursa.com/marches/aaz.aspx";
    Document page_marche = null;
    ArrayList<Cotation> list_marche = new ArrayList<Cotation>();

    ListView cotation_listview = null;

    TextView textview__nom_cotation1 = null;
    TextView textview__nom_cotation2 = null;
    TextView textview__nom_cotation3 = null;

    TextView textview_ouverture_cotation1 = null;
    TextView textview_ouverture_cotation2 = null;
    TextView textview_ouverture_cotation3 = null;

    TextView textview_variation_cotation1 = null;
    TextView textview_variation_cotation2 = null;
    TextView textview_variation_cotation3 = null;

    TextView textview_volume_cotation1 = null;
    TextView textview_volume_cotation2 = null;
    TextView textview_volume_cotation3 = null;

    Cotation_Adapter eAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_marche);

        cotation_listview  = (ListView) findViewById(R.id.listView_cotation);
        ViewGroup header = (ViewGroup) getLayoutInflater().inflate(R.layout.listview_header, cotation_listview, false);
        cotation_listview.addHeaderView(header);
        cotation_listview.setItemsCanFocus(false);

        setup_textview_cotation_preféré();

        new Tache().execute();




    }



    private class Tache extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                page_marche = Jsoup.connect(marche_url).get();

                Elements table = page_marche.select("table[id=tabQuotes]");
                //System.out.println("chaine +++++++++ "+table.get(0).toString());

                Element tablebody = table.select("tbody").get(0);

                for (Element row : tablebody.select("tr")) {
                        Cotation c = new Cotation();

                    // Identify all the table cell's(td)
                    Elements tds = row.select("td");
                    c.setNom(tds.get(0).text());
                    c.setOuverture(tds.get(1).text());
                    c.setHaut(tds.get(2).text());
                    c.setBas(tds.get(3).text());
                    c.setVolume(tds.get(5).text());
                    c.setDernier(tds.get(6).text());
                    c.setVariation(tds.get(7).text());


                        list_marche.add(c);






                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            inject_cotations_preféré(list_marche);
            eAdapter = new Cotation_Adapter(MarcheActivity.this, R.layout.row_item,
                    list_marche);

            cotation_listview.setAdapter(eAdapter);
            eAdapter.notifyDataSetChanged();


        }
    }



    public class Cotation_Adapter extends ArrayAdapter<Cotation> {
        Activity activity;
        int layoutResourceId;
        Cotation cot;
        ArrayList<Cotation> data;

        public Cotation_Adapter(Activity act, int layoutResourceId,
                               ArrayList<Cotation> data) {
            super(act, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.activity = act;
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            CotationHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new CotationHolder();
                holder.nom = (TextView) row.findViewById(R.id.Nom_cotation);
                holder.ouverture = (TextView) row.findViewById(R.id.Ouveture_Cotation);
                holder.variation = (TextView) row.findViewById(R.id.Variation_Cotaion);
                holder.volume = (TextView) row.findViewById(R.id.Volume_Cotation);

                row.setTag(holder);
            } else {
                holder = (CotationHolder) row.getTag();
            }
            cot = data.get(position);
            holder.nom.setText(cot.getNom());
            holder.ouverture.setText(cot.getOuverture());
            holder.variation.setText(cot.getVariation());
            holder.volume.setText(cot.getVolume());


           /* holder.edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.i("Edit Button Clicked", "**********");

                    Intent update_user = new Intent(activity,
                            Add_Update_User.class);
                    update_user.putExtra("called", "update");
                    update_user.putExtra("USER_ID", v.getTag().toString());
                    activity.startActivity(update_user);

                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub

                    // show a message while loader is loading

                    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete ");
                    final int user_id = Integer.parseInt(v.getTag().toString());
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok",
                            new AlertDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // MyDataObject.remove(positionToRemove);
                                    DataBaseHandler dBHandler = new DataBaseHandler(
                                            activity.getApplicationContext());
                                    dBHandler.Delete_Contact(user_id);
                                    Contact_Main_Screen.this.onResume();

                                }
                            });
                    adb.show();
                }

            });*/
            return row;

        }

        class CotationHolder {
            TextView nom;
            TextView ouverture;
            TextView variation;
            TextView volume;

        }



    }

    private void setup_textview_cotation_preféré() {

         textview__nom_cotation1 = (TextView) findViewById(R.id.textView6);
         textview__nom_cotation2 = (TextView) findViewById(R.id.textView);
         textview__nom_cotation3 = (TextView) findViewById(R.id.textView11);

         textview_ouverture_cotation1 = (TextView) findViewById(R.id.textView8);
         textview_ouverture_cotation2 = (TextView) findViewById(R.id.textView3);
        textview_ouverture_cotation3 = (TextView) findViewById(R.id.textView13);

         textview_variation_cotation1 = (TextView) findViewById(R.id.textView7);
         textview_variation_cotation2 = (TextView) findViewById(R.id.textView2);
         textview_variation_cotation3 = (TextView) findViewById(R.id.textView12);

         textview_volume_cotation1 = (TextView) findViewById(R.id.textView9);
         textview_volume_cotation2 = (TextView) findViewById(R.id.textView4);
         textview_volume_cotation3 = (TextView) findViewById(R.id.textView14);
    }

    private void inject_cotations_preféré(ArrayList<Cotation> list) {
        Cotation c1 = list.get(0);
        Cotation c2 = list.get(1);
        Cotation c3 = list.get(2);

        textview__nom_cotation1.setText(c1.getNom());
        textview_ouverture_cotation1.setText(c1.getOuverture());
        textview_variation_cotation1.setText(c1.getVariation());
        textview_volume_cotation1.setText(c1.getVolume());

        textview__nom_cotation2.setText(c2.getNom());
        textview_ouverture_cotation2.setText(c2.getOuverture());
        textview_variation_cotation2.setText(c2.getVariation());
        textview_volume_cotation2.setText(c2.getVolume());

        textview__nom_cotation3.setText(c3.getNom());
        textview_ouverture_cotation3.setText(c3.getOuverture());
        textview_variation_cotation3.setText(c3.getVariation());
        textview_volume_cotation3.setText(c3.getVolume());



    }




}
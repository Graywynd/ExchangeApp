package com.example.saif.echangeapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHaut.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHaut#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHaut extends Fragment {

    String marche_url="http://www.ilboursa.com/marches/palmares.aspx";
    Document page_marche = null;
    ArrayList<Cotation> list_marche = new ArrayList<Cotation>();
    ListView cotation_listview = null;

    Cotation_Adapter eAdapter=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_haut, container, false);

        cotation_listview  = (ListView) view.findViewById(R.id.listView_gagnant);
        ViewGroup headerview = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.listview_header2, cotation_listview, false);
        cotation_listview.addHeaderView(headerview);
        cotation_listview.setItemsCanFocus(false);



        new Tache().execute();

        return view;

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

                    c.setHaut(tds.get(1).text());
                    c.setBas(tds.get(2).text());
                    c.setDernier(tds.get(3).text());
                    c.setVolume(tds.get(4).text());

                    c.setVariation(tds.get(5).text());




                    list_marche.add(c);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            eAdapter = new Cotation_Adapter(getActivity(), R.layout.row_item2,
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
                holder.nom = (TextView) row.findViewById(R.id.Nom_cotation2);

                holder.variation = (TextView) row.findViewById(R.id.Variation_Cotaion2);
                holder.volume = (TextView) row.findViewById(R.id.Volume_Cotation2);

                row.setTag(holder);
            } else {
                holder = (CotationHolder) row.getTag();
            }
            cot = data.get(position);
            holder.nom.setText(cot.getNom());

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

            TextView variation;
            TextView volume;

        }



    }


}


package com.example.realestate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewrequeststatus extends AppCompatActivity implements JsonResponse,AdapterView.OnItemClickListener {

    ListView l1;
    String search;
    ImageButton c1;
    String[] plot, place, district, state, image,req_id, value,st,plot_id,pas_id,date;
    public static String plotid,sts,pasid,reqid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrequeststatus);
        l1 = (ListView) findViewById(R.id.lv2);






        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewrequeststatus.this;
        String q = "/Viewmyrequeststatus?logid="+Login.logid;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        sts=st[i];
        plotid=plot_id[i];
        pasid=pas_id[i];
        reqid=req_id[i];
        if (sts.equals("accept")) {



        final CharSequence[] items = {"Makepayment","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewrequeststatus.this);
         builder.setTitle("Pay Token Amount 100000");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


//                if (items[item].equals("Map")) {
//
//
//                    String url = "http://www.google.com/maps?q=" + Viewcollage.latitude + "," + Viewcollage.longitude;
//                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(in);
//
//                }

                if (items[item].equals("Makepayment")) {



                    startActivity(new Intent(getApplicationContext(),Makepayment.class));

                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
        }
        if (sts.equals("paid")) {
            final CharSequence[] items = {"View Forworded Files","Send Register Request","Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Viewrequeststatus.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


//                if (items[item].equals("Map")) {
//
//
//                    String url = "http://www.google.com/maps?q=" + Viewcollage.latitude + "," + Viewcollage.longitude;
//                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(in);
//
//                }

                    if (items[item].equals("Send Register Request")) {

                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) Viewrequeststatus.this;
                        String q = "/sendregisterreq?plotid="+plotid+"&reqid="+reqid+"&logid="+Login.logid;
                        q = q.replace(" ", "%20");
                        JR.execute(q);
                        startActivity(new Intent(getApplicationContext(),Viewrequeststatus.class));

                    }else if (items[item].equals("View Forworded Files")) {


                        startActivity(new Intent(getApplicationContext(),ViewForwordedfiles.class));

                    }

                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
        }
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String method = jo.getString("method");
            if (method.equalsIgnoreCase("Viewreq")) {



            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                place = new String[ja1.length()];
                district = new String[ja1.length()];
                state = new String[ja1.length()];
                image = new String[ja1.length()];
                pas_id = new String[ja1.length()];
                st = new String[ja1.length()];
                plot_id = new String[ja1.length()];
                req_id = new String[ja1.length()];
                date = new String[ja1.length()];
                value = new String[ja1.length()];
//                stock = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    place[i] = ja1.getJSONObject(i).getString("place");
                    district[i] = ja1.getJSONObject(i).getString("district");
                    state[i] = ja1.getJSONObject(i).getString("state");
                    req_id[i] = ja1.getJSONObject(i).getString("request_id");
                    image[i] = ja1.getJSONObject(i).getString("plot");
                    st[i] = ja1.getJSONObject(i).getString("status");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    pas_id[i] = ja1.getJSONObject(i).getString("plot_assign_id");
                    plot_id[i] = ja1.getJSONObject(i).getString("plot_id");
//                    stock[i] = ja1.getJSONObject(i).getString("availability");

                    value[i] = "Place: " + place[i] + "\nDistrict: " + district[i] + "\nState: " + state[i] + "\nImage: " + image[i] + "\nStatus: " + st[i] + "\n";

                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                l1.setAdapter(ar);
                Custrequest a = new Custrequest(this, place, district, state, image,st,date);
                l1.setAdapter(a);
            }
            }
            else if (method.equalsIgnoreCase("repeat")) {
                Toast.makeText(getApplicationContext(),"request Already sented Please wait Patiently", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Viewrequeststatus.class));
            }else if (method.equalsIgnoreCase("done")) {
                Toast.makeText(getApplicationContext(),"Request  Sent Successfully wait Patiently For Reply", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Viewrequeststatus.class));
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), Userhome.class);
        startActivity(b);


    }
}
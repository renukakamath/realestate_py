package com.example.realestate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewmyplotrequests extends AppCompatActivity implements JsonResponse,AdapterView.OnItemClickListener {

    ListView l1;
    String search;
    ImageButton c1;
    String[] plot, place, district, state, image, value,st,plot_id,pas_id,date,req_id;
    public static String plotid,sts,pasid ,reqid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmyplotrequests);
        l1 = (ListView) findViewById(R.id.lv2);






        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewmyplotrequests.this;
        String q = "/Viewmyplotrequest?logid="+Login.logid;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        sts=st[i];
        plotid=plot_id[i];
        pasid=pas_id[i];
        reqid=req_id[i];
        if (sts.equals("pending")) {


            final CharSequence[] items = {"Accept", "Reject", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Viewmyplotrequests.this);
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

                    if (items[item].equals("Accept")) {

                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) Viewmyplotrequests.this;
                        String q = "/acceptrequest?reqid=" + reqid + "&logid=" + Login.logid;
                        q = q.replace(" ", "%20");
                        JR.execute(q);
                        startActivity(new Intent(getApplicationContext(),Viewmyplotrequests.class));

                    } else if (items[item].equals("Reject")) {
                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) Viewmyplotrequests.this;
                        String q = "/rejectrequest?reqid=" + reqid + "&logid=" + Login.logid;
                        q = q.replace(" ", "%20");
                        JR.execute(q);
                        startActivity(new Intent(getApplicationContext(),Viewmyplotrequests.class));
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
        }
        if (sts.equals("paid")) {
            final CharSequence[] items = { "Forword certificates", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Viewmyplotrequests.this);
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

                    if (items[item].equals("Forword certificates")) {

                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) Viewmyplotrequests.this;
                        String q = "/forwordcertificate?reqid=" + reqid + "&logid=" + Login.logid;
                        q = q.replace(" ", "%20");
                        JR.execute(q);
                        startActivity(new Intent(getApplicationContext(),Viewmyplotrequests.class));

                    } else if (items[item].equals("Cancel")) {
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
                date = new String[ja1.length()];
                req_id = new String[ja1.length()];
                value = new String[ja1.length()];
//                stock = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    place[i] = ja1.getJSONObject(i).getString("place");
                    district[i] = ja1.getJSONObject(i).getString("district");
                    state[i] = ja1.getJSONObject(i).getString("state");
                    image[i] = ja1.getJSONObject(i).getString("plot");
                    st[i] = ja1.getJSONObject(i).getString("status");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    req_id[i] = ja1.getJSONObject(i).getString("request_id");
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
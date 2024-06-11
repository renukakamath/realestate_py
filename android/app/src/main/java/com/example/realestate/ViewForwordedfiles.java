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

public class ViewForwordedfiles extends AppCompatActivity implements JsonResponse,AdapterView.OnItemClickListener{

    ListView l1;
    String search;
    ImageButton c1;
    String[] plot, place, date, title, image, value,st,plot_id,pas_id;
    public static String plotid,sts,pasid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forwordedfiles);
        l1 = (ListView) findViewById(R.id.lv2);






        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewForwordedfiles.this;
        String q = "/Viewfiles?reqid="+Viewrequeststatus.reqid;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


//        st=stock[i];
//        plotid=plot_id[i];
//        pasid=pas_id[i];

//        final CharSequence[] items = {"Send Request","Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(ViewForwordedfiles.this);
//        // builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//
////                if (items[item].equals("Map")) {
////
////
////                    String url = "http://www.google.com/maps?q=" + Viewcollage.latitude + "," + Viewcollage.longitude;
////                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
////                    startActivity(in);
////
////                }
//
//                if (items[item].equals("Send Request")) {
//
//                    JsonReq JR = new JsonReq();
//                    JR.json_response = (JsonResponse) ViewForwordedfiles.this;
//                    String q = "/sendrequest?plotid="+plotid+"&passignid="+pasid+"&logid="+Login.logid;
//                    q = q.replace(" ", "%20");
//                    JR.execute(q);
//                    startActivity(new Intent(getApplicationContext(),Viewrequeststatus.class));
//
//                }
//
//                else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//
//            }
//
//        });
//        builder.show();
    }

    @Override
    public void response(JSONObject jo) {
        try {


            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                title = new String[ja1.length()];
                image = new String[ja1.length()];
                date = new String[ja1.length()];

                value = new String[ja1.length()];
//                stock = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {

                    title[i] = ja1.getJSONObject(i).getString("title");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    image[i] = ja1.getJSONObject(i).getString("file");

//                    stock[i] = ja1.getJSONObject(i).getString("availability");

                    value[i] = "Title: " + title[i] + "\nDocument: " + image[i] + "\nDate: " + date[i] + "\n";

                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                l1.setAdapter(ar);
                Custimageviewfiles a = new Custimageviewfiles(this,title,image,date);
                l1.setAdapter(a);
            }
            if (status.equalsIgnoreCase("failed")) {
                Toast.makeText(getApplicationContext(),"Request Already sented", Toast.LENGTH_LONG).show();
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
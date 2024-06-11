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

public class Viewregistrationrequeststatus extends AppCompatActivity implements JsonResponse,AdapterView.OnItemClickListener{

    ListView l1;
    String search;
    ImageButton c1;
    String[]  title, Providedadte, date, image,certificate,rrid, value;
    public static String plotid,sts,prid,rridd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewregistrationrequeststatus);
        l1 = (ListView) findViewById(R.id.lv2);






        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewregistrationrequeststatus.this;
        String q = "/Viewmyregisterreqquest?logid="+Login.logid;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    sts=certificate[i];
    rridd=rrid[i];


        if (sts.equals("pending")) {
            final CharSequence[] items = {"Upload Certificates","Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Viewregistrationrequeststatus.this);
            builder.setTitle("Upload Certificates");
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

                    if (items[item].equals("Upload Certificates")) {



                        startActivity(new Intent(getApplicationContext(),Addservice.class));

                    }

                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
        }
        else  {



            final CharSequence[] items = {"View Certificate","Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Viewregistrationrequeststatus.this);
//            builder.setTitle("Pay Token Amount 100000");
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

                    if (items[item].equals("View Certificate")) {



                        startActivity(new Intent(getApplicationContext(),Makepayment.class));

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
                    title = new String[ja1.length()];
                    Providedadte = new String[ja1.length()];
                    date = new String[ja1.length()];
                    image = new String[ja1.length()];
                    rrid = new String[ja1.length()];
                    certificate = new String[ja1.length()];

                    value = new String[ja1.length()];
//                stock = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        title[i] = ja1.getJSONObject(i).getString("title");
                        Providedadte[i] = ja1.getJSONObject(i).getString("provided_date");
                        date[i] = ja1.getJSONObject(i).getString("date");
                        rrid[i] = ja1.getJSONObject(i).getString("plot_register_id");
                        certificate[i] = ja1.getJSONObject(i).getString("certificate");

                        image[i] = ja1.getJSONObject(i).getString("plot");

//                    stock[i] = ja1.getJSONObject(i).getString("availability");

//                        value[i] = "Tile: " + place[i] + "\nDistrict: " + district[i] + "\nState: " + state[i] + "\nImage: " + image[i] + "\nStatus: " + st[i] + "\n";

                    }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                l1.setAdapter(ar);
                    Custrequestregister a = new Custrequestregister(this, title, Providedadte, date, image);
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
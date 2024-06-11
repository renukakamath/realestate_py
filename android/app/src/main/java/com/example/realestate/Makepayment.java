package com.example.realestate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class Makepayment extends AppCompatActivity implements JsonResponse{

    EditText e1,e2,e3,e4,e5;
    Button b1;
    String amount,cardname,cardno,cvv,expdate;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makepayment);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText) findViewById(R.id.amount);
        e2=(EditText) findViewById(R.id.chn);
        e3=(EditText) findViewById(R.id.cno);
        e4=(EditText) findViewById(R.id.cvv);
        e5=(EditText) findViewById(R.id.expdate);

        b1=(Button) findViewById(R.id.paybtn);


        e1.setText("100000");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                cardname=e2.getText().toString();
                cardno=e3.getText().toString();
                cvv=e4.getText().toString();
                expdate=e5.getText().toString();
                amount=e1.getText().toString();

                if(cardname.equalsIgnoreCase("")|| !cardname.matches("[a-zA-Z ]+"))
                {
                    e2.setError("Enter card holdername");
                    e2.setFocusable(true);
                }
                else if(cardno.equalsIgnoreCase("")|| cardno.length()!=16)
                {
                    e3.setError("Enter your card number");
                    e3.setFocusable(true);
                }
                else if(cvv.equalsIgnoreCase("")|| cvv.length()!=3)
                {
                    e4.setError("Enter your Cvv");
                    e4.setFocusable(true);
                }
                 else if(expdate.equalsIgnoreCase(""))
                 {
                     e4.setError("Enter cad exp date");
                     e4.setFocusable(true);
                 }
                 else{

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Makepayment.this;
                    String q = "/makepayment?log_id="+sh.getString("log_id","")+"&plotid="+Viewrequeststatus.plotid+"&amount="+amount+"&reqid="+Viewrequeststatus.reqid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "Payment Completed", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Viewrequeststatus.class));

            }  else {
                startActivity(new Intent(getApplicationContext(), Viewrequeststatus.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}